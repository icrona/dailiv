package com.dailiv.internal.data.remote.response.ingredient;

import com.dailiv.internal.data.remote.response.BaseResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by aldo on 4/29/18.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class IngredientDetailResponse {

    public Ingredient ingredient;

    @JsonProperty(value = "similar_ingredient")
    public List<Ingredient> similarIngredient;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Ingredient extends BaseResponse{

        public String name;

        public int price;

        public int min;

        public String unit;

        public String photo;

        public String category;
    }
}
