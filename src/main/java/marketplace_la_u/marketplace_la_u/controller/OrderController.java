package marketplace_la_u.marketplace_la_u.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import marketplace_la_u.marketplace_la_u.DTO.order.OrderRequest;
import marketplace_la_u.marketplace_la_u.DTO.order.OrderResponse;
import marketplace_la_u.marketplace_la_u.model.Users;
import marketplace_la_u.marketplace_la_u.repository.UsersRepository;
import marketplace_la_u.marketplace_la_u.service.OrderService;
import marketplace_la_u.marketplace_la_u.service.PdfService;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final UsersRepository usersRepository;
    private final PdfService pdfService;

    // 🔥 AQUÍ ESTÁ LA MAGIA: Extraemos al usuario real desde el Token JWT
    private Users getAuthenticatedUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return usersRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Usuario autenticado no encontrado en la BD"));
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> listMyOrders() {
        return ResponseEntity.ok(orderService.listMyOrders(getAuthenticatedUser()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> consultById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.consultById(id, getAuthenticatedUser()));
    }

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestBody @Valid OrderRequest orderDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                // Usamos el usuario autenticado real para crear el pedido
                .body(orderService.createOrder(orderDTO, getAuthenticatedUser()));
    }

    @GetMapping("/{id}/invoice")
    public ResponseEntity<byte[]> downloadInvoice(@PathVariable Long id) {

        byte[] pdfBytes = pdfService.generatePublicInvoicePdf(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(
                ContentDisposition.attachment()
                        .filename("factura_" + id + ".pdf")
                        .build()
        );

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfBytes);
    }
}