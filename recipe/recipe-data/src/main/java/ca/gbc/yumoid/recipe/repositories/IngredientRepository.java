package ca.gbc.yumoid.recipe.repositories;

import ca.gbc.yumoid.recipe.model.Ingredient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepository extends CrudRepository<Ingredient, Long>{
    Ingredient getIngredientById(Long id);
}
