package com.dailiv.view.shop.detail;

import com.dailiv.internal.data.remote.response.ingredient.IngredientDetailResponse;
import com.dailiv.view.base.IBaseView;

/**
 * Created by aldo on 4/29/18.
 */

public interface IngredientDetailView extends IBaseView{

    void showDetail(IngredientDetailResponse response);
}
