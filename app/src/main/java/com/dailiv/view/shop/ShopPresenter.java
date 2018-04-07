package com.dailiv.view.shop;

import com.dailiv.internal.data.local.pojo.IngredientFilter;
import com.dailiv.internal.data.remote.IApi;
import com.dailiv.view.base.AbstractSinglePresenter;
import com.dailiv.view.base.IPresenter;

import javax.inject.Inject;
import javax.inject.Named;

import static com.dailiv.util.common.Preferences.getLocation;

/**
 * Created by aldo on 4/1/18.
 */

public class ShopPresenter extends AbstractSinglePresenter<ShopView>{

    @Inject
    @Named("common")
    IApi api;

    @Inject
    public ShopPresenter() {
    }

    private static final int LIMIT = 15;

    public void getIngredients(IngredientFilter filter) {

        networkView.callApi(() -> api.ingredients(
                getLocation().getStoreId(),
                LIMIT,
                filter.getCategory(),
                filter.getFromPrice(),
                filter.getToPrice(),
                filter.getPage()
        ).map(mapResponseToObject()));
    }
}
