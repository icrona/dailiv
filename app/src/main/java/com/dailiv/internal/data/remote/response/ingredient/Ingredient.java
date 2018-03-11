package com.dailiv.internal.data.remote.response.ingredient;

import com.dailiv.internal.data.remote.response.BaseResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by aldo on 3/10/18.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Ingredient extends BaseResponse {

    @JsonProperty(value = "ingredient_category_id")
    public int ingredientCategoryId;

    public String name;

    public String unit;

    public int price;

    public int min;

    public String photo;

    public int amount;
}
