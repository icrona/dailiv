package com.dailiv.internal.data.remote.response.recipe;

import com.dailiv.internal.data.remote.response.Category;
import com.dailiv.internal.data.remote.response.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by aldo on 3/10/18.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class RecipeResponse {

    public Recipe recipe;

    public List<Instruction> instructions;

    public List<RecipeIngredient> ingridients;

    public User user;

    public List<Category> categories;

    public boolean liked;

    public boolean cooked;

    public List<Thought> thoughts;

    @JsonProperty(value = "related_recipes")
    public List<Recipe> relatedRecipes;


}
