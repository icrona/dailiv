package com.dailiv.view.shop;

import com.dailiv.internal.data.remote.IApi;
import com.dailiv.view.base.IPresenter;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by aldo on 4/1/18.
 */

public class ShopPresenter implements IPresenter<ShopView>{

    @Inject
    @Named("common")
    IApi api;

    @Inject
    public ShopPresenter() {
    }

    private ShopView view;

    @Override
    public void onAttach(ShopView view) {
        this.view = view;
    }

    @Override
    public void onDetach() {
        this.view = null;
    }
}
