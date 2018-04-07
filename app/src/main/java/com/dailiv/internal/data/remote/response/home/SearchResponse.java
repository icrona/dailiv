package com.dailiv.internal.data.remote.response.home;

import com.dailiv.internal.data.remote.response.ingredient.Ingredient;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by aldo on 4/6/18.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchResponse {

    public List<SearchRecipe> recipe;

    public List<Ingredient> ingredient;
}
