package ca.gbc.yumoid.recipe.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ingredientName;

    private String quantity;

    @ManyToMany
    @JoinTable(name = "ingredient_recipe",
            joinColumns = @JoinColumn(name = "ingredient_id"),
            inverseJoinColumns = @JoinColumn(name = "recipe_id"))
    private Set<Recipe> ingredientRecipe = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "ingredient_shopping",
            joinColumns = @JoinColumn(name = "ingredient_id"),
            inverseJoinColumns = @JoinColumn(name = "order_id"))
    private Set<Recipe> ingredientShoppingList = new HashSet<>();

    public Ingredient() {
    }

    public Ingredient(Long id, String ingredientName, String quantity, Set<Recipe> ingredientRecipe, Set<Recipe> ingredientShoppingList) {
        this.id = id;
        this.ingredientName = ingredientName;
        this.quantity = quantity;
        this.ingredientRecipe = ingredientRecipe;
        this.ingredientShoppingList = ingredientShoppingList;
    }

    public Ingredient(String ingredientName, String quantity, Set<Recipe> ingredientRecipe, Set<Recipe> ingredientShoppingList) {
        this.ingredientName = ingredientName;
        this.quantity = quantity;
        this.ingredientRecipe = ingredientRecipe;
        this.ingredientShoppingList = ingredientShoppingList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public Set<Recipe> getIngredientRecipe() {
        return ingredientRecipe;
    }

    public void setIngredientRecipe(Set<Recipe> ingredientRecipe) {
        this.ingredientRecipe = ingredientRecipe;
    }

    public Set<Recipe> getIngredientShoppingList() {
        return ingredientShoppingList;
    }

    public void setIngredientShoppingList(Set<Recipe> ingredientShoppingList) {
        this.ingredientShoppingList = ingredientShoppingList;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "id=" + id +
                ", ingredientName='" + ingredientName + '\'' +
                ", quantity='" + quantity + '\'' +
                ", ingredientRecipe=" + ingredientRecipe +
                ", ingredientShoppingList=" + ingredientShoppingList +
                '}';
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ingredient ingredient = (Ingredient) o;
        return Objects.equals(id, ingredient.id);
    }
}
