package marketplace_la_u.marketplace_la_u.service;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import marketplace_la_u.marketplace_la_u.DTO.order.OrderProductRequest;
import marketplace_la_u.marketplace_la_u.DTO.order.OrderRequest;
import marketplace_la_u.marketplace_la_u.DTO.order.OrderResponse;
import marketplace_la_u.marketplace_la_u.model.Order_Products;
import marketplace_la_u.marketplace_la_u.model.Orders;
import marketplace_la_u.marketplace_la_u.model.Product;
import marketplace_la_u.marketplace_la_u.model.Users;
import marketplace_la_u.marketplace_la_u.orders.OrderStatus;
import marketplace_la_u.marketplace_la_u.repository.OrdersRepository;
import marketplace_la_u.marketplace_la_u.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrdersRepository orderRepository;
    private final ProductRepository productRepository;

    public List<OrderResponse> listMyOrders(Users user){
        return orderRepository.findByUserId(user.getId()).stream().map(OrderResponse::new).toList();
    }

    public OrderResponse consultById(Long id, Users user){
        Orders order = orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Orden no encontrada."));
        if (!order.getUser().getId().equals(user.getId())){
            throw new IllegalStateException("No tienes permiso para esta orden.");
        }
        return new OrderResponse(order);
    }

    public OrderResponse createOrder(OrderRequest orderDTO, Users user) {

        Orders order = new Orders();
        order.setUser(user);
        order.setStatus(OrderStatus.PENDIENTE);

        List<Order_Products> items = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (OrderProductRequest productDto : orderDTO.getProduct()) {

            Product product = productRepository.findById(productDto.getProductId()).orElseThrow(() -> new EntityNotFoundException("Producto no encontrado."));

            if (product.getStock() < productDto.getQuantity()) {throw new IllegalStateException("Stock insuficiente.");}
            product.setStock(product.getStock() - productDto.getQuantity());

            Order_Products item = new Order_Products();
            item.setOrder(order);
            item.setProduct(product);
            item.setQuantity(productDto.getQuantity());
            item.setPricePurchase(product.getPrice());

            items.add(item);

            total = total.add(product.getPrice().multiply(BigDecimal.valueOf(productDto.getQuantity())));
        }

        order.setProducts(items);
        order.setTotalPrice(total);

        return new OrderResponse(orderRepository.save(order));
    }
}
