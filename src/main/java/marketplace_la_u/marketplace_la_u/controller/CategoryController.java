package marketplace_la_u.marketplace_la_u.controller;
import jakarta.validation.Valid;
import marketplace_la_u.marketplace_la_u.DTO.category.CategoryRequest;
import marketplace_la_u.marketplace_la_u.DTO.category.CategoryResponse;
import marketplace_la_u.marketplace_la_u.model.Category;
import marketplace_la_u.marketplace_la_u.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService service;


    @PostMapping
    public ResponseEntity<?> registerCategory(@Valid @RequestBody CategoryRequest category) {
        try {
            return ResponseEntity.ok(service.registerCategory(category));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping
    public List<CategoryResponse> listCategory() {
        return service.listCategory();
    }


    @PutMapping("/{id}")
    public CategoryResponse updateCategory(@Valid@PathVariable Long id, @RequestBody Category category) {
        return service.updateCategory(id, category);
    }


    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id) {
        service.deleteCategory(id);
    }
}

