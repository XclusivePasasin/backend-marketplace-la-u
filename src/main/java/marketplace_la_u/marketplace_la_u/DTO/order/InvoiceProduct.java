package marketplace_la_u.marketplace_la_u.DTO.order;

import java.math.BigDecimal;

public record InvoiceProduct(
        String name,
        BigDecimal price,
        Integer quantity,
        BigDecimal subtotal
) {
}