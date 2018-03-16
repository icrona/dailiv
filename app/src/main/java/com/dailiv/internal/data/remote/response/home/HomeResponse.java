package com.dailiv.internal.data.remote.response.home;

import com.dailiv.internal.data.remote.response.recipe.Recipe;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by aldo on 3/14/18.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class HomeResponse {

    @JsonProperty(value = "recipe_of_the_day")
    public Recipe recipeOfTheDay;

    @JsonProperty(value = "popular_recipes")
    public List<List<Recipe>> popularRecipes;
}
