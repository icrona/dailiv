package com.dailiv.internal.data.remote;

/**
 * Created by aldo on 3/5/18.
 */

public interface IApiConstant {

    String FB_AUTH = "auth/facebook/callback";

    String GOOGLE_AUTH = "auth/google/callback";

    String LOGIN = "login";

    String REGISTER = "register";

    String HOME = "home";

    String SEARCH = HOME + "/search";

    String ACCOUNT = "account/";

    String LOCATION = ACCOUNT + "location/";

    String CHOOSE_LOCATION = LOCATION + "choose";

    String RECIPE = "recipe/";

    String RECIPE_DETAIL = RECIPE + "{identifier}";

    String COOK = RECIPE + "cook";

    String UNCOOK = RECIPE + "cook";

    String INGREDIENTS = "ingredients";

    String INGREDIENT = "ingredient";

    String INGREDIENT_CATEGORIES = INGREDIENT + "/category";

    String INGREDIENT_DETAIL = INGREDIENT + "/detail/{identifier}/{store_id}";

    String CART = ACCOUNT + "cart/";

    String GET_CART = CART + "{location_id}";

    String UPDATE_CART = CART + "update";

    String DELETE_CART = CART + "delete";

    String ADD_TO_CART = ACCOUNT + "add-to-cart";

    String RECIPES = "recipes";

    String RECIPE_CATEGORY = RECIPE + "category";

    String MEAL_PLANNING = ACCOUNT + "meal-planning";
}
