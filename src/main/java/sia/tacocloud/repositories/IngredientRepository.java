package sia.tacocloud.repositories;

import sia.tacocloud.domainEntities.Ingredient;

import java.util.Optional;

public interface IngredientRepository {


    Iterable<Ingredient> findAll();
    Optional<Ingredient> findById(String id);
    Ingredient save(Ingredient ingredient);
}
