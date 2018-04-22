package com.dailiv.internal.data.remote.request.recipe;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by aldo on 4/22/18.
 */

public class MealPlanningRequest {

    @JsonProperty("planning_category")
    public String planningCategory;

    @JsonProperty("planning_date")
    public String planningDate;

    @JsonProperty("recipe_id")
    public int recipeId;

}
