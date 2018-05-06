package com.dailiv.internal.data.remote.response.recipe;

import com.dailiv.internal.data.local.pojo.RecipeDetail;
import com.dailiv.internal.data.remote.response.BaseResponse;
import com.dailiv.internal.data.remote.response.Category;
import com.dailiv.internal.data.remote.response.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by aldo on 3/10/18.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Recipe extends BaseResponse {

    @JsonProperty(value = "user_id")
    public int userId;

    public String name;

    public int portion;

    public int duration;

    public String description;

    public String difficulty;

    public String slug;

    @JsonProperty(value = "total_like")
    public int totalLike;

    @JsonProperty(value = "total_view")
    public int totalView;

    @JsonProperty(value = "thumbnail_photo")
    public String thumbnailPhoto;

    @JsonProperty(value = "full_photo")
    public String fullPhoto;

    public User user;

    public List<Category> category;

    public RecipeDetail.RelatedRecipe toRelatedRecipe() {

        return new RecipeDetail.RelatedRecipe(
                id,
                slug,
                name,
                fullPhoto
        );
    }

}
