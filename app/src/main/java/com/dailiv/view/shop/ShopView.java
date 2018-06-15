package com.dailiv.view.shop;

import com.dailiv.internal.data.remote.response.Category;
import com.dailiv.internal.data.remote.response.ingredient.IngredientsResponse;
import com.dailiv.view.base.IBaseView;

import java.util.List;

/**
 * Created by aldo on 4/1/18.
 */

public interface ShopView extends IBaseView {

    void showIngredients(IngredientsResponse response);

    void getCategories(List<Category> categories);

    void onAddToCart(int cartId, int cartedAmount, int ingredientId);
}
