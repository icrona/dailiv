package com.dailiv.internal.data.remote.response.ingredient;

import com.dailiv.internal.data.remote.response.BaseResponse;
import com.dailiv.internal.data.remote.response.location.LocationResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by aldo on 4/6/18.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class StoreIngredient extends BaseResponse{

    @JsonProperty(value = "store_id")
    public int storeId;

    @JsonProperty(value = "ingredient_id")
    public int ingredientId;

    public int price;

    public int min;

    public LocationResponse store;

}
