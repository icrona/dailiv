package com.dailiv.util;

/**
 * Created by aldo on 3/1/18.
 */

public interface IConstants {

    int TIMEOUT = 60;
    String FINIHS_ONBOARD = "finish_onboard";
    String ACCESS_TOKEN = "access_token";
    String ACCOUNT_SLUG = "account_slug";

    String LOCATION_ID = "location_id";
    String LOCATION_NAME = "location_name";
    String STORE_ID = "store_id";

    String CHECKOUT = "checkout";

    String EDIT_PROFILE = "edit-profile";

    String PROFILE_RECIPE_LIST = "profile_recipe_list";

    int CHECKOUT_REQUEST_CODE = 1;

    int PROFILE_RECIPE_LIST_REQUEST_CODE = 2;

    int EDIT_PROFILE_REQUEST_CODE = 3;

    interface FragmentIndex {

        int HOME = 0;
        int SHOP = 1;
        int RECIPE = 2;
        int NOTIFICATION = 3;
        int PROFILE = 4;
    }

    int PICK_PHOTO_REQUEST = 4;

    int READ_EXTERNAL_PERMISSION = 5;


}
