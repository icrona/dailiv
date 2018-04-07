package com.dailiv.internal.data.local.pojo;

import com.dailiv.internal.data.remote.response.Category;
import com.dailiv.internal.data.remote.response.recipe.Recipe;

import java.util.Collections;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

import static com.dailiv.util.common.CollectionUtil.mapListToList;
import static java.util.Collections.singletonList;

/**
 * Created by aldo on 4/1/18.
 */

@Data
@AllArgsConstructor
public class RecipeIndex {

    private String slug;

    private String userImage;

    private String recipeName;

    private String recipeImage;

    private String difficulty;

    private List<String> categories;

    private int like;

    private int view;

    public RecipeIndex(Recipe recipe) {
        this(
                recipe.slug,
                recipe.user.photo,
                recipe.name,
                recipe.thumbnailPhoto,
                recipe.difficulty,
                mapListToList(recipe.category, Category::getName),
                recipe.totalLike,
                recipe.totalView
        );
    }

    public RecipeIndex(Recipe recipe, String category) {
        this(
                recipe.slug,
                recipe.user.photo,
                recipe.name,
                recipe.thumbnailPhoto,
                recipe.difficulty,
                singletonList(category),
                recipe.totalLike,
                recipe.totalView
        );
    }
}
