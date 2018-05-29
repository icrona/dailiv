package com.dailiv.internal.data.remote.response.mealplan;

import com.dailiv.internal.data.remote.response.BaseResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by aldo on 5/27/18.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class MealPlanResponse {

    @JsonProperty(value = "breakfast")
    public List<MealPlanRecipeResponse> breakfast;

    @JsonProperty(value = "lunch")
    public List<MealPlanRecipeResponse> lunch;

    @JsonProperty(value = "dinner")
    public List<MealPlanRecipeResponse> dinner;

    @JsonProperty(value = "snack")
    public List<MealPlanRecipeResponse> snack;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class MealPlanRecipeResponse extends BaseResponse{

        @JsonProperty(value = "user_id")
        public int userId;

        @JsonProperty(value = "recipe_id")
        public int recipeId;

        public String date;

        public String category;

        public MealPlanRecipeInfoResponse recipe;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class MealPlanRecipeInfoResponse extends BaseResponse {

        public String name;

        public String slug;
    }

}
