/**********************************************************************************
 * Project: < Yumoid >
 * Assignment: < assignment 2 >
 * Author(s): < Robert Kaczur, Phuong Hoang, Truong Thi Bui>
 * Student Number: < 101014890, 101306676, 101300750>
 * Date: December 4rd 2022
 * Description: This java file is used to set the shopping list (for future extension) entity in H2DB.
 **********************************************************************************/
package ca.gbc.yumoid.recipe.model;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.HashSet;
import java.util.Set;

@Entity
public class ShoppingList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String shoppingListName;

    @ManyToOne
    @JoinTable(name = "user_id")
    private User shoppingListUser;

    @ManyToMany
    @JoinTable(name = "shopping_list_ingredients",
            joinColumns = @JoinColumn(name = "shopping_list_id"), inverseJoinColumns = @JoinColumn(name = "ingredient_id"))
    private Set<Ingredient> shoppingListIngredients;

    public ShoppingList() {
    }

    public ShoppingList(Long id, String shoppingListName) {
        this.id = id;
        this.shoppingListName = shoppingListName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShoppingListName() {
        return shoppingListName;
    }

    public void setShoppingListName(String shoppingListName) {
        this.shoppingListName = shoppingListName;
    }

    public User getShoppingListUser() {
        return shoppingListUser;
    }

    public void setShoppingListUser(User shoppingListUser) {
        this.shoppingListUser = shoppingListUser;
    }

    public Set<Ingredient> getShoppingListIngredients() {
        return shoppingListIngredients;
    }

    public void setShoppingListIngredients(Set<Ingredient> shoppingListIngredients) {
        this.shoppingListIngredients = shoppingListIngredients;
    }
}
