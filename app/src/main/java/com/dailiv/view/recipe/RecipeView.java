package com.dailiv.view.recipe;

import com.dailiv.internal.data.remote.response.Category;
import com.dailiv.internal.data.remote.response.recipe.RecipesResponse;
import com.dailiv.view.base.IBaseView;

import java.util.List;

/**
 * Created by aldo on 4/1/18.
 */

public interface RecipeView extends IBaseView{

    void showRecipes(RecipesResponse response);

    void getCategories(List<Category> categories);

}
