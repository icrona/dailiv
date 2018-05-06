package com.dailiv.view.recipe.detail;

import com.dailiv.internal.data.remote.response.recipe.AddThoughtResponse;
import com.dailiv.internal.data.remote.response.recipe.RecipeDetailResponse;
import com.dailiv.view.base.IBaseView;

/**
 * Created by aldo on 4/29/18.
 */

public interface RecipeDetailView extends IBaseView{

    void showDetail(RecipeDetailResponse response);

    void onThoughtAdded(AddThoughtResponse response);
}
