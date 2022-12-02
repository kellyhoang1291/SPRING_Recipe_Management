package ca.gbc.yumoid.recipe.controllers;

import ca.gbc.yumoid.recipe.model.Ingredient;
import ca.gbc.yumoid.recipe.model.Recipe;
import ca.gbc.yumoid.recipe.model.User;
import ca.gbc.yumoid.recipe.repositories.UserRepository;
import ca.gbc.yumoid.recipe.services.IngredientService;
import ca.gbc.yumoid.recipe.services.RecipeService;
import ca.gbc.yumoid.recipe.services.SearchService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

@RequestMapping("/registered/ingredients")
@Controller
public class IngredientController {
    private final IngredientService ingredientService;
    private final RecipeService recipeService;

    private final SearchService searchService;
    final private UserRepository userRepository;

    public IngredientController(IngredientService ingredientService, RecipeService recipeService, SearchService searchService, UserRepository userRepository) {
        this.ingredientService = ingredientService;
        this.recipeService = recipeService;
        this.searchService = searchService;
        this.userRepository = userRepository;
    }

    //-------------------------- Create Recipe - Add/Edit/Delete Ingredients
    @RequestMapping("/new/add")
    public String addNewRecipe(Ingredient ingredient, HttpSession session){
        Set<Ingredient> recipeIngredients;

        if (session.getAttribute("recipeIngredients") != null){
            recipeIngredients = (Set<Ingredient>) session.getAttribute("recipeIngredients");
        } else {
            recipeIngredients = new HashSet<>();
        }

        recipeIngredients.add(ingredient);
        session.setAttribute("recipeIngredients", recipeIngredients);

        return "redirect:/registered/recipe/create";
    }

    @RequestMapping("/new/delete")
    public String deleteNewRecipe(@RequestParam("ingredientName") String ingredientName, @RequestParam("quantity") Long quantity, @RequestParam("unit") String unit, HttpSession session){
        Ingredient i = new Ingredient();
        i.setIngredientName(ingredientName);
        i.setQuantity(quantity);
        i.setUnit(unit);
        Set<Ingredient> recipeIngredients = (Set<Ingredient>) session.getAttribute("recipeIngredients");
        Iterator ingredientSet = recipeIngredients.iterator();

        while (ingredientSet.hasNext()){
            Ingredient ri = (Ingredient) ingredientSet.next();
            if (i.equals(ri)){
                recipeIngredients.remove(ri);
                break;
            }
        }
        session.setAttribute("recipeIngredients", recipeIngredients);
        return "redirect:/registered/recipe/create";
    }

    @RequestMapping("/new/edit")
    public String editNewRecipe(@RequestParam("ingredientName") String ingredientName, @RequestParam("quantity") Long quantity, @RequestParam("unit") String unit, Model model){
        Ingredient ingredient = new Ingredient();
        ingredient.setIngredientName(ingredientName);
        ingredient.setQuantity(quantity);
        ingredient.setUnit(unit);
        model.addAttribute("currentIngredient", ingredient);
        return "/registered/ingredient/edit";
    }

    @RequestMapping("/new/save")
    public String saveEditNewRecipe(Ingredient ingredient, @ModelAttribute("currentIngredient") Ingredient currentIngredient , HttpSession session){
        Set<Ingredient> recipeIngredients = (Set<Ingredient>) session.getAttribute("recipeIngredients");
        // delete old one
        Iterator ingredientSet = recipeIngredients.iterator();

        while (ingredientSet.hasNext()){
            Ingredient ri = (Ingredient) ingredientSet.next();
            if (ingredient.equals(ri)){
                recipeIngredients.remove(ri);
                break;
            }
        }
        // add updated one
        recipeIngredients.add(currentIngredient);
        session.setAttribute("recipeIngredients", recipeIngredients);
        return "redirect:/registered/recipe/create";
    }

    @RequestMapping("/save")
    public String save(HttpSession session){
        Recipe recipe = (Recipe) session.getAttribute("recipe");
        Set<Recipe> recipeSet = new HashSet<>();
        recipeSet.add(recipe);
        Set<Ingredient> ingredientSet = (Set<Ingredient>) session.getAttribute("recipeIngredients");

        ingredientService.saveIngredientList(ingredientSet, recipeSet);
        recipeService.save(recipe);

        session.removeAttribute("recipeIngredients");
        session.removeAttribute("recipe");
        return "redirect:/registered/recipe/list";
    }

    @RequestMapping("/update/add")
    public String addUpdateRecipe(@RequestParam("recipeId") Long recipeId, Ingredient ingredient){
        Set<Ingredient> recipeIngredients = searchService.ingredientSet(recipeId);
        Recipe recipe = recipeService.getRecipeById(recipeId);

        recipeIngredients.add(ingredient);
        recipe.setRecipeIngredients(recipeIngredients);
        ingredientService.save(ingredient);
        recipeService.save(recipe);

        return "redirect:/registered/recipe/update/" + recipeId;
    }

    @RequestMapping("/update/delete")
    public String deleteUpdateRecipe(@RequestParam("recipeId") Long recipeId, @RequestParam("ingredientId") Long ingredientId){
        //remove from shop list if presents

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.getUserByUsername(username);
        Set<Ingredient> userIngredients = searchService.listMyIngredients(username);

        Iterator shopIngredients = userIngredients.iterator();

        while (shopIngredients.hasNext()){
            Ingredient si = (Ingredient) shopIngredients.next();
            if (si.getId() == ingredientId){
                userIngredients.remove(si);
                user.setUserIngredients(userIngredients);
                userRepository.save(user);
                break;
            }
        }

        //remove from recipe
        Recipe recipe = recipeService.getRecipeById(recipeId);

        Set<Ingredient> recipeIngredients = searchService.ingredientSet(recipeId);
        Iterator ingredientSet = recipeIngredients.iterator();

        while (ingredientSet.hasNext()){
            Ingredient ri = (Ingredient) ingredientSet.next();
            if (ri.getId() == ingredientId){
                recipeIngredients.remove(ri);
                recipe.setRecipeIngredients(recipeIngredients);
                recipeService.save(recipe);
                ingredientService.deleteById(ingredientId);
                break;
            }
        }



        return "redirect:/registered/recipe/update/" + recipeId;

    }

    @RequestMapping("/update/edit")
    public String editUpdateRecipe(@RequestParam("recipeId") Long recipeId, @RequestParam("ingredientId") Long ingredientId, Model model, HttpSession session){
        Ingredient ingredient = ingredientService.findById(ingredientId);

        session.setAttribute("recipeId", recipeId);
        model.addAttribute("currentIngredient", ingredient);
        return "/registered/ingredient/edit-update";
    }

    @RequestMapping("/update/save")
    public String saveUpdateRecipe(Ingredient ingredient, HttpSession session){
        ingredientService.save(ingredient);
        Long recipeId = (Long) session.getAttribute("recipeId");
        session.removeAttribute("recipeId");

        return "redirect:/registered/recipe/update/" + recipeId;
    }

    @RequestMapping("/shop/view")
    public String viewShoppingList(Model model){
        Set<Ingredient> userIngredients;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.getUserByUsername(username);
        userIngredients = searchService.listMyIngredients(username);
        model.addAttribute("userIngredients", userIngredients);
        model.addAttribute("user", user);

        return "/registered/shoppinglist/list";
    }

    @RequestMapping("/shop/add")
    public String addToShopping(@RequestParam("recipeId") Long recipeId, @RequestParam("ingredientId") Long ingredientId, Model model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.getUserByUsername(username);
        Ingredient ingredient = ingredientService.findById(ingredientId);
        Set<Ingredient> userIngredients = searchService.listMyIngredients(username);

        userIngredients.add(ingredient);
        user.setUserIngredients(userIngredients);
        userRepository.save(user);

//        model.addAttribute("userIngredients", userIngredients);
//        model.addAttribute("recipe", recipeService.getRecipeById(recipeId));
//        model.addAttribute("ingredients", searchService.ingredientSet(recipeId));
        return "redirect:/registered/recipe/view/" + recipeId;
    }

    @RequestMapping("/shop/delete")
    public String deleteFromShopping(@RequestParam("ingredientId") Long ingredientId, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.getUserByUsername(username);
        Set<Ingredient> userIngredients = searchService.listMyIngredients(username);

        Iterator ingredientSet = userIngredients.iterator();

        while (ingredientSet.hasNext()){
            Ingredient ri = (Ingredient) ingredientSet.next();
            if (ri.getId() == ingredientId){
                userIngredients.remove(ri);
                user.setUserIngredients(userIngredients);
                userRepository.save(user);
                break;
            }
        }
        return "redirect:/registered/ingredients/shop/view";
    }

}
