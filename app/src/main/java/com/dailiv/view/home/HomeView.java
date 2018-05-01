package com.dailiv.view.home;

import com.dailiv.internal.data.local.pojo.RecipeIndex;
import com.dailiv.internal.data.local.pojo.RecipeOfTheDay;
import com.dailiv.view.base.IBaseView;

import java.util.List;

/**
 * Created by aldo on 4/1/18.
 */

public interface HomeView extends IBaseView{

    void onShowHome(RecipeOfTheDay recipeOfTheDay, List<RecipeIndex> recipeList);
}
