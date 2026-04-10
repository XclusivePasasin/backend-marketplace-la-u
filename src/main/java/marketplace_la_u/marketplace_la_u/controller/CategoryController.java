package marketplace_la_u.marketplace_la_u.controller;

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

    // ✅ CREAR
    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody Category category) {
        try {
            return ResponseEntity.ok(service.guardar(category));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ✅ LISTAR
    @GetMapping
    public List<Category> listar() {
        return service.listar();
    }

    // ✅ ACTUALIZAR
    @PutMapping("/{id}")
    public Category actualizar(@PathVariable Long id, @RequestBody Category category) {
        return service.actualizar(id, category);
    }

    // ✅ ELIMINAR
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}

