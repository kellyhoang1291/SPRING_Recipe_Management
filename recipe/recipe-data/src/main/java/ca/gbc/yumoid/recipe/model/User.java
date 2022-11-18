/**********************************************************************************
 * Project: < Yumoid >
 * Assignment: < assignment 1 >
 * Author(s): < Robert Kaczur, Phuong Hoang, Truong Thi Bui>
 * Student Number: < 101014890, 101306676, 101300750>
 * Date: October 23rd 2022
 * Description: This java file is used to set the user entity in H2DB.
 **********************************************************************************/
package ca.gbc.yumoid.recipe.model;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.*;

import javax.persistence.*;
import java.util.*;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String address;
    private String postalCode;
    @Column(unique = true)
    private String username;
    private String password;
    @NotNull
    private String recoveryPIN;
    private boolean enabled;

    @ManyToMany(mappedBy = "likedByUsers")
    private Set<Recipe> likedRecipes = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "userRecipe")
    private Set<Recipe> recipes;

    @OneToMany(mappedBy = "userMeal")
    private Set<Meal> meals;

    @OneToMany(mappedBy = "userEvent")
    private Set<Event> events;

    public User() {
    }

    public User(Long id, String firstName, String lastName, String address, String postalCode, String username, String password,  String recoveryPIN) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.postalCode = postalCode;
        this.username = username;
        this.password = password;
        this.recoveryPIN = recoveryPIN;
    }

    public User(Long id, String firstName, String lastName, String address, String postalCode, String username,
                String password, String recoveryPIN, boolean enabled, Set<Recipe> likedRecipes, Set<Role> roles,
                Set<Recipe> recipes, Set<Meal> meals, Set<Event> events) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.postalCode = postalCode;
        this.username = username;
        this.password = password;
        this.recoveryPIN = recoveryPIN;
        this.enabled = enabled;
        this.likedRecipes = likedRecipes;
        this.roles = roles;
        this.recipes = recipes;
        this.meals = meals;
        this.events = events;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Recipe> getLikedRecipes() {
        return likedRecipes;
    }

    public void setLikedRecipes(Set<Recipe> likedRecipes) {
        this.likedRecipes = likedRecipes;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(Set<Recipe> recipes) {
        this.recipes = recipes;
    }

    public Set<Meal> getMeals() {
        return meals;
    }

    public void setMeals(Set<Meal> meals) {
        this.meals = meals;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getFullName(){ return this.firstName + " " + this.lastName; }


    public String getRecoveryPIN() {
        return recoveryPIN;
    }

    public void setRecoveryPIN(String recoveryPIN) {
        this.recoveryPIN = recoveryPIN;
    }

    public Set<Event> getEvents() {
        return events;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", enabled=" + enabled +
                ", likedRecipes=" + likedRecipes +
                ", roles=" + roles +
                ", recipes=" + recipes +
                ", meals=" + meals +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
