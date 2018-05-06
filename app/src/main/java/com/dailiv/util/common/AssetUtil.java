package com.dailiv.util.common;

import com.dailiv.BuildConfig;

/**
 * Created by aldo on 4/28/18.
 */

public class AssetUtil {

    public static String getIngredientImageUrl(String slug) {

        return BuildConfig.ASSET_PREFIX + slug;
    }

    public static String getRecipeImageUrl(String slug) {

        return BuildConfig.RECIPE_PREFIX + slug;
    }

    public static String getUserImageUrl(String slug) {
        return BuildConfig.USER_PREFIX + slug;
    }
}
