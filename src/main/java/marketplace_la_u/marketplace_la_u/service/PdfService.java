package marketplace_la_u.marketplace_la_u.service;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import lombok.RequiredArgsConstructor;
import marketplace_la_u.marketplace_la_u.DTO.order.InvoiceProduct;
import marketplace_la_u.marketplace_la_u.model.Order_Products;
import marketplace_la_u.marketplace_la_u.model.Orders;
import marketplace_la_u.marketplace_la_u.model.Users;
import marketplace_la_u.marketplace_la_u.repository.OrdersRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PdfService {

    private final OrdersRepository ordersRepository;
    private final TemplateEngine templateEngine;

    @Transactional(readOnly = true)
    public byte[] generatePublicInvoicePdf(Long orderId) {

        Orders order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada"));

        try {
            List<InvoiceProduct> products = order.getProducts()
                    .stream()
                    .map(this::mapToInvoiceProduct)
                    .toList();

            Context context = new Context();

            context.setVariable("orderId", order.getId());
            context.setVariable("clientName", order.getUser().getName());
            context.setVariable("clientEmail", order.getUser().getEmail());

            context.setVariable("date", order.getCreatedAt()
                    .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));

            context.setVariable("products", products);
            context.setVariable("total", order.getTotalPrice());

            String htmlContent = templateEngine.process("invoice", context);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.useFastMode();
            builder.withHtmlContent(htmlContent, null);
            builder.toStream(outputStream);
            builder.run();

            return outputStream.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Error al generar PDF desde HTML", e);
        }
    }

    private InvoiceProduct mapToInvoiceProduct(Order_Products item) {
        BigDecimal subtotal = item.getPricePurchase()
                .multiply(BigDecimal.valueOf(item.getQuantity()));

        return new InvoiceProduct(
                item.getProduct().getName(),
                item.getPricePurchase(),
                item.getQuantity(),
                subtotal
        );
    }
}