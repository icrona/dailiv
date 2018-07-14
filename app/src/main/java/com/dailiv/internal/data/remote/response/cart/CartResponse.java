package com.dailiv.internal.data.remote.response.cart;

import com.dailiv.internal.data.remote.response.BaseResponse;
import com.dailiv.internal.data.remote.response.ingredient.Ingredient;
import com.dailiv.internal.data.remote.response.ingredient.StoreIngredient;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

/**
 * Created by aldo on 4/14/18.
 */


@JsonIgnoreProperties(ignoreUnknown = true)
public class CartResponse extends BaseResponse{

    @JsonProperty(value = "store_ingredient_id")
    public int storeIngredientId;

    @JsonProperty(value = "user_id")
    public int userId;

    @Getter
    public int amount;

    public String status;

    public Ingredient ingredient;

    @JsonProperty(value = "store_ingredient")
    public StoreIngredient storeIngredient;

}
