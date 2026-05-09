package marketplace_la_u.marketplace_la_u.controller;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import marketplace_la_u.marketplace_la_u.DTO.order.OrderRequest;
import marketplace_la_u.marketplace_la_u.DTO.order.OrderResponse;
import marketplace_la_u.marketplace_la_u.model.Users;
import marketplace_la_u.marketplace_la_u.repository.UsersRepository;
import marketplace_la_u.marketplace_la_u.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final UsersRepository usersRepository;

    private Users getMockUser() {
        return usersRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Usuario de prueba no encontrado"));
    }


    @GetMapping
    public ResponseEntity<List<OrderResponse>> listMyOrders(){
        return ResponseEntity.ok(orderService.listMyOrders(getMockUser()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> consultById(@PathVariable Long id){
        return ResponseEntity.ok(orderService.consultById(id, getMockUser()));
    }

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestBody @Valid OrderRequest orderDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(orderDTO, getMockUser()));
    }
}
