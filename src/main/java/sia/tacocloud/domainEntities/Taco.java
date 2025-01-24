package sia.tacocloud.domainEntities;



import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Date;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor(access= AccessLevel.PUBLIC, force = true)
public class Taco {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private  Date createdAt = new Date();

    @NotNull
    @Size(min = 5, message="Name must be at least 5 characters long")
    private  String name;

    @NotNull
    @Size(min=1, message = "You must choose at least 1 ingredient")
    @ManyToMany
    private List<Ingredient> ingredients;

    public void addIngredient(Ingredient ingredient) {
        this.ingredients.add(ingredient);
    }
}
