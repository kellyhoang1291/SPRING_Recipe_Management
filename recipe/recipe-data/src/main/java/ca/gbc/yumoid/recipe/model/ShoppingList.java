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

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_shopping_list", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "order_id"))
    private User user;

    @ManyToMany(mappedBy = "ingredientShoppingList")
    private Set<Ingredient> shopIngredients = new HashSet<>();
}
