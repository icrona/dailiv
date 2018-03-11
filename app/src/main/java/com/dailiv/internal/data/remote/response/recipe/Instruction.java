package com.dailiv.internal.data.remote.response.recipe;

import com.dailiv.internal.data.remote.response.BaseResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by aldo on 3/10/18.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Instruction extends BaseResponse {

    @JsonProperty(value = "recipe_id")
    public int recipeId;

    public String body;
}
