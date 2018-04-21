package com.dailiv.internal.data.remote.response.ingredient;

import com.dailiv.internal.data.remote.response.BaseResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by aldo on 3/10/18.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Ingredient extends BaseResponse {

    @JsonProperty(value = "ingredient_category_id")
    public int ingredientCategoryId;

    public String name;

    @JsonProperty(value = "en_name")
    public String enName;

    public String unit;

    public String photo;

    @JsonProperty(value = "store_ingredient")
    public List<StoreIngredient> storeIngredient;

    public Cart cart;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Cart {

        public int id;

        public int amount;
    }

}
