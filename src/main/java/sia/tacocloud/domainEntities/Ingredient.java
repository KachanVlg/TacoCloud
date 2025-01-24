package sia.tacocloud.domainEntities;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;
import sia.tacocloud.repositories.IngredientRepository;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor(access= AccessLevel.PROTECTED, force = true)
public class Ingredient{

    @Id
    private String id;
    private String name;
    private Type type;

    public enum Type{
        WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
    }
}
