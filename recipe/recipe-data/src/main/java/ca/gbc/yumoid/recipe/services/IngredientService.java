package ca.gbc.yumoid.recipe.services;
import ca.gbc.yumoid.recipe.model.Ingredient;
import ca.gbc.yumoid.recipe.model.Recipe;
import ca.gbc.yumoid.recipe.repositories.IngredientRepository;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class IngredientService {
    final private IngredientRepository ingredientRepository;

    public IngredientService(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    public Set<Ingredient> findAll() {
        return null;
    }

    public Ingredient findById(Long id){
        return ingredientRepository.getIngredientById(id);
    }


    public void save(Ingredient ingredient){
        ingredientRepository.save(ingredient);
    }


    public void deleteById(Long id){
        ingredientRepository.deleteById(id);
    }

    public void delete(Ingredient ingredient){
        ingredientRepository.delete(ingredient);
    }

    public void saveIngredientList(Set<Ingredient> ingredientList, Set<Recipe> recipeList){
        Iterator ingredientIterator = ingredientList.iterator();

        while (ingredientIterator.hasNext()){
            Ingredient i = (Ingredient) ingredientIterator.next();
            i.setIngredientRecipes(recipeList);
            ingredientRepository.save(i);
        }
    }

}
