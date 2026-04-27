package marketplace_la_u.marketplace_la_u.controller;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import marketplace_la_u.marketplace_la_u.DTO.category.CategoryRequest;
import marketplace_la_u.marketplace_la_u.DTO.category.CategoryResponse;
import marketplace_la_u.marketplace_la_u.DTO.category.CategoryUpdateRequest;
import marketplace_la_u.marketplace_la_u.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {


    private final CategoryService service;


    @PostMapping
    public ResponseEntity<CategoryResponse> registerCategory(@Valid @RequestBody CategoryRequest categoryDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registerCategory(categoryDto));
    }


    @GetMapping
    public ResponseEntity<List<CategoryResponse>> listCategory() {
        return ResponseEntity.ok(service.listCategory());
    }


    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> updateCategory(@PathVariable Long id, @RequestBody @Valid CategoryUpdateRequest categoryDto) {
        return ResponseEntity.ok(service.updateCategory(id, categoryDto));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        service.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> consultById(@PathVariable Long id) {
        return ResponseEntity.ok(service.consultById(id));
    }
}

