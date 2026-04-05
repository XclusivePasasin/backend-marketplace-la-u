package marketplace_la_u.marketplace_la_u.service;

import marketplace_la_u.marketplace_la_u.model.Category;
import marketplace_la_u.marketplace_la_u.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    public List<Category> listar() {
        return repository.findAll();
    }

    public Category guardar(Category category) {
        if (repository.existsByNombre(category.getNombre())) {
            throw new RuntimeException("La categoría ya existe");
        }

        return repository.save(category);
    }

    public Category obtenerPorId(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }

    public Category actualizar(Long id, Category category) {

        Category existente = repository.findById(id).orElse(null);

        if (existente == null) {
            throw new RuntimeException("Categoría no encontrada");
        }

        if (!existente.getNombre().equals(category.getNombre()) &&
                repository.existsByNombre(category.getNombre())) {
            throw new RuntimeException("El nombre ya está en uso");
        }

        existente.setNombre(category.getNombre());
        existente.setDescripcion(category.getDescripcion());

        return repository.save(existente);
    }
}