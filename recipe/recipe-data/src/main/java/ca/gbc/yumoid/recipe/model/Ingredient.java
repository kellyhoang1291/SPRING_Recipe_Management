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

    private Long quantity;

    private String unit;

    @ManyToMany(mappedBy = "recipeIngredients")
    private Set<Recipe> ingredientRecipes = new HashSet<>();

    @ManyToMany(mappedBy = "userIngredients")
    private Set<User> ingredientUsers = new HashSet<>();

    @ManyToMany(mappedBy ="shoppingListIngredients")
    private Set<ShoppingList> ingredientShoppingList = new HashSet<>();

    public Ingredient() {
    }

    public Ingredient(Long id, String ingredientName, Long quantity, String unit) {
        this.id = id;
        this.ingredientName = ingredientName;
        this.quantity = quantity;
        this.unit = unit;
    }

    public Ingredient(String ingredientName, Long quantity, String unit) {
        this.ingredientName = ingredientName;
        this.quantity = quantity;
        this.unit = unit;
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

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Set<Recipe> getIngredientRecipes() {
        return ingredientRecipes;
    }

    public void setIngredientRecipes(Set<Recipe> ingredientRecipes) {
        this.ingredientRecipes = ingredientRecipes;
    }

    public Set<ShoppingList> getIngredientShoppingList() {
        return ingredientShoppingList;
    }

    public void setIngredientShoppingList(Set<ShoppingList> ingredientShoppingList) {
        this.ingredientShoppingList = ingredientShoppingList;
    }

    public Set<User> getIngredientUsers() {
        return ingredientUsers;
    }

    public void setIngredientUsers(Set<User> ingredientUsers) {
        this.ingredientUsers = ingredientUsers;
    }

    @Override
    public String toString() {
        return "IngredientService{" +
                "id=" + id +
                ", ingredientName='" + ingredientName + '\'' +
                ", quantity='" + quantity + '\'' +

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
