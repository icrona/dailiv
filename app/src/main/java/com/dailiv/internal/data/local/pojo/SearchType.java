package com.dailiv.internal.data.local.pojo;

import com.dailiv.view.recipe.detail.RecipeDetailActivity;
import com.dailiv.view.shop.detail.IngredientDetailActivity;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by aldo on 3/31/18.
 */

@Getter
@AllArgsConstructor
public enum SearchType {

    RECIPE("", RecipeDetailActivity.class),
    INGREDIENT("", IngredientDetailActivity.class);

    //todo
    private String icon;

    private Class activityClass;
}
