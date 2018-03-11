package com.dailiv.internal.data.remote.request.recipe;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by aldo on 3/11/18.
 */

public class RecipeBaseRequest {

    @JsonProperty(value = "recipe_id")
    public int recipeId;
}
