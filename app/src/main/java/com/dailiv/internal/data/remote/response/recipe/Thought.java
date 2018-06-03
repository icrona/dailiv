package com.dailiv.internal.data.remote.response.recipe;

import com.dailiv.internal.data.local.pojo.RecipeDetail;
import com.dailiv.internal.data.remote.response.BaseResponse;
import com.dailiv.internal.data.remote.response.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by aldo on 3/11/18.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Thought extends BaseResponse {

    @JsonProperty(value = "recipe_id")
    public int recipeId;

    @JsonProperty(value = "user_id")
    public int userId;

    public String body;

    public int helpful;

    public boolean clicked;

    public User user;

    public String diffForHuman;

    public RecipeDetail.Comment toComment() {

        return new RecipeDetail.Comment(
                id,
                userId,
                user.getFullname(),
                body,
                user.photo,
                diffForHuman,
                user.slug
        );
    }
}
