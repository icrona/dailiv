package com.dailiv.internal.data.local.pojo;

import com.dailiv.internal.data.remote.response.home.SearchRecipe;
import com.dailiv.internal.data.remote.response.ingredient.Ingredient;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by aldo on 3/31/18.
 */

@Data
@AllArgsConstructor
public class SearchResult {

    private SearchType searchType;

    private String name;

    private String identifier;

    public SearchResult(SearchRecipe recipe) {
        this(
                SearchType.RECIPE,
                recipe.source.name,
                recipe.source.slug
        );
    }

    public SearchResult(Ingredient ingredient) {
        this(
                SearchType.INGREDIENT,
                ingredient.name,
                String.valueOf(ingredient.id)
        );
    }
}
