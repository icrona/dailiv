package com.dailiv.internal.data.local.pojo;

import android.text.TextUtils;

import com.dailiv.internal.data.remote.response.Category;
import com.dailiv.internal.data.remote.response.User;
import com.dailiv.internal.data.remote.response.recipe.Recipe;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

import static com.dailiv.util.common.AssetUtil.getRecipeImageUrl;
import static com.dailiv.util.common.AssetUtil.getUserImageUrl;
import static com.dailiv.util.common.CollectionUtil.mapListToList;
import static java.util.Collections.singletonList;
import static org.apache.commons.lang3.StringUtils.capitalize;

/**
 * Created by aldo on 4/1/18.
 */

@Data
@AllArgsConstructor
public class RecipeIndex {

    private int id;

    private String slug;

    private String userImage;

    private String recipeName;

    private String recipeImage;

    private String difficulty;

    private List<String> categories;

    private int like;

    private int view;

    private String userSlug;

    public void addLike() {
        setLike(getLike() + 1);
    }

    public void subtractLike() {
        setLike(getLike() - 1);
    }

    public RecipeIndex(Recipe recipe) {
        this(
                recipe.id,
                recipe.slug,
                recipe.user.photo,
                recipe.name,
                recipe.thumbnailPhoto,
                recipe.difficulty,
                mapListToList(recipe.category, Category::getName),
                recipe.totalLike,
                recipe.totalView,
                recipe.user.slug
        );
    }

    public RecipeIndex(Recipe recipe, User user) {
        this(
                recipe.id,
                recipe.slug,
                user.photo,
                recipe.name,
                recipe.thumbnailPhoto,
                recipe.difficulty,
                mapListToList(recipe.category, Category::getName),
                recipe.totalLike,
                recipe.totalView,
                user.slug
        );
    }

    public RecipeIndex(Recipe recipe, String category) {
        this(
                recipe.id,
                recipe.slug,
                recipe.user.photo,
                recipe.name,
                recipe.thumbnailPhoto,
                recipe.difficulty,
                singletonList(category),
                recipe.totalLike,
                recipe.totalView,
                recipe.user.slug
        );
    }

    public String getImageUrl() {

        return getRecipeImageUrl(getRecipeImage());
    }

    public String getUserPhotoUrl() {

        return getUserImageUrl(getUserImage());

    }

    public String getCategoriesString() {

        return TextUtils.join(", ", getCategories());
    }

    public String getInfo() {

        String difficulty = capitalize(getDifficulty());
        if(getCategories().isEmpty()) {

            return difficulty;
        }

        return capitalize(getCategories().get(0)) + " | " + difficulty;
    }
}
