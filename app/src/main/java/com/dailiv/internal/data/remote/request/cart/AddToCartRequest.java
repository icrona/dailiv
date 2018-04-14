package com.dailiv.internal.data.remote.request.cart;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by aldo on 4/14/18.
 */

public class AddToCartRequest {

    @JsonProperty(value = "ingredient_amount")
    public int ingredientAmount;

    @JsonProperty(value = "store_ingredient_id")
    public int storeIngredientId;
}
