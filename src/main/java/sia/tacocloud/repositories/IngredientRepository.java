package sia.tacocloud.repositories;

import org.springframework.data.repository.CrudRepository;
import sia.tacocloud.domainEntities.Ingredient;

import java.util.Optional;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {}
