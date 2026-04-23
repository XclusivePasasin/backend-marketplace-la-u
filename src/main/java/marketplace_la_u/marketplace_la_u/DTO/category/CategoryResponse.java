package marketplace_la_u.marketplace_la_u.DTO.category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import marketplace_la_u.marketplace_la_u.model.Category;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponse {
    private Long id;
    private String name;
    private String description;

    public CategoryResponse(Category category){
        this.id = category.getId();
        this.name = category.getName();
        this.description = category.getDescripcion();
    }
}
