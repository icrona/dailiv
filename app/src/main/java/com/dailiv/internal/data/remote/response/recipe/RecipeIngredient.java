package com.dailiv.internal.data.remote.response.recipe;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by aldo on 4/6/18.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class RecipeIngredient {

    public String name;

    public String unit;

    public int amount;

}
