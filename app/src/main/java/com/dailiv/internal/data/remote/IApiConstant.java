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

    String COOK = RECIPE + "cook";

    String UNCOOK = RECIPE + "cook";

    String INGREDIENT = "ingredient";

    String INGREDIENT_CATEGORIES = "/category";
}
