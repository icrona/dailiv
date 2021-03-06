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

    String COMMENT = RECIPE + "thought";

    String COOK = RECIPE + "cook";

    String UNCOOK = RECIPE + "uncook";

    String LIKE = RECIPE + "like";

    String UNLIKE = RECIPE + "unlike";

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

    String PAYMENT = ACCOUNT + "payment/";

    String ORDER_HISTORY = ACCOUNT + "purchase/history";

    String DELIVERY_FEE = PAYMENT + "delivery_fee/{store_id}/{location_id}";

    String CHECKOUT = PAYMENT + "cash";

    String DISCOUNT_COUPON = "discount-coupon/{code}";

    String REVIEW_NEEDED = ACCOUNT + "purchase/success-transaction-without-rating";

    String SUBMIT_REVIEW = ACCOUNT + "purchase/review";

    String MEAL_PLAN = ACCOUNT + "meal-plan";

    String PROFILE_BY_SLUG = ACCOUNT + "{slug}";

    String PROFILE_BY_ID = ACCOUNT + "{id}";

    String RECIPE_BY_PROFILE_ID = PROFILE_BY_ID + "/{type}";

    String LIKED_RECIPE = "liked-recipe";

    String COOKED_RECIPE = "cooked-recipe";

    String RECIPE_BY_ME = "recipe";

    String EDIT_HEADLINE = ACCOUNT + "edit-headline";

    String CHANGE_PROFILE_PHOTO = ACCOUNT + "change-profile-photo";

    String FOLLOW = ACCOUNT + "follow-user";

    String UNFOLLOW = ACCOUNT + "unfollow-user";

}
