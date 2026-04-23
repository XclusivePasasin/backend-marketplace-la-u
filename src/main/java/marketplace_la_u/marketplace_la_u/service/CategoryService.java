package marketplace_la_u.marketplace_la_u.service;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import marketplace_la_u.marketplace_la_u.DTO.category.CategoryRequest;
import marketplace_la_u.marketplace_la_u.DTO.category.CategoryResponse;
import marketplace_la_u.marketplace_la_u.model.Category;
import marketplace_la_u.marketplace_la_u.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository repository;

    public List<CategoryResponse> listCategory() {
        return repository.findAll().stream().map(CategoryResponse::new).toList();
    }

    public CategoryResponse registerCategory(CategoryRequest categoryDto) {
        if (repository.existsByName(categoryDto.getName())) {
            throw new RuntimeException("La categoría ya existe");
        }
        Category category = new Category();
        category.setName(categoryDto.getName());
        category.setDescripcion(categoryDto.getDescription());
        Category newCategory = repository.save(category);

        return new CategoryResponse(newCategory);
    }

    public CategoryResponse consultById(Long id) {
        return repository.findById(id).map(CategoryResponse::new).orElseThrow(() -> new RuntimeException("Categoria no encontrada."));
    }

    public void deleteCategory(Long id){
        if (!repository.existsById(id)){
            throw new RuntimeException("Categoria no encontrado");
        }
        repository.deleteById(id);
    }

    public CategoryResponse updateCategory(Long id, Category nuevosDatos){
        return repository.findById(id).map( category ->{
            if(nuevosDatos.getName() != null) category.setName(nuevosDatos.getName());
            if(nuevosDatos.getDescripcion() != null) category.setDescripcion(nuevosDatos.getDescripcion());
            return new CategoryResponse(repository.save(category));
        }).orElseThrow(() -> new RuntimeException("Categoria no encontrada"));
    }

}