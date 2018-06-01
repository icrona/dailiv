package com.dailiv.view.profile.recipe;

import com.dailiv.internal.data.remote.response.recipe.Recipe;
import com.dailiv.view.base.IBaseView;

import java.util.List;

/**
 * Created by aldo on 6/1/18.
 */

public interface RecipeListView extends IBaseView {

    void showRecipes(List<Recipe> recipes);

}
