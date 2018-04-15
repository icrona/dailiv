package com.dailiv.view.shop;

import com.dailiv.internal.data.local.pojo.IngredientFilter;
import com.dailiv.internal.data.remote.IApi;
import com.dailiv.internal.data.remote.request.cart.AddToCartRequest;
import com.dailiv.internal.data.remote.request.cart.DeleteCartRequest;
import com.dailiv.internal.data.remote.request.cart.UpdateCartRequest;
import com.dailiv.internal.data.remote.response.Category;
import com.dailiv.internal.data.remote.response.ingredient.IngredientsResponse;
import com.dailiv.util.network.NetworkView;
import com.dailiv.view.base.AbstractSinglePresenter;
import com.dailiv.view.base.IPresenter;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import rx.functions.Action0;
import rx.functions.Action1;

import static com.dailiv.util.common.Preferences.getLocation;

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

    private NetworkView<IngredientsResponse> ingredientsResponseNetworkView;

    private NetworkView<Boolean> addToCartNetworkView;

    private NetworkView<Boolean> updateCartNetworkView;

    private NetworkView<Boolean> deleteCartNetworkView;

    private NetworkView<List<Category>> categoryNetworkView;

    private static final int LIMIT = 15;

    @Override
    public void onAttach(ShopView view) {
        this.view = view;
        ingredientsResponseNetworkView = new NetworkView<>(
                getOnStart(),
                getOnComplete(),
                getOnShowError(),
                getOnIngredientsResponse()
        );

        addToCartNetworkView = new NetworkView<>(
                getOnStart(),
                getOnComplete(),
                getOnShowError(),
                getOnCartResponse()
        );

        updateCartNetworkView = new NetworkView<>(
                getOnStart(),
                getOnComplete(),
                getOnShowError(),
                getOnCartResponse()
        );

        deleteCartNetworkView = new NetworkView<>(
                getOnStart(),
                getOnComplete(),
                getOnShowError(),
                getOnCartResponse()
        );

        categoryNetworkView = new NetworkView<>(
                getOnStart(),
                getOnComplete(),
                getOnShowError(),
                getCategoryResponse()
        );
    }

    @Override
    public void onDetach() {

        ingredientsResponseNetworkView.safeUnsubscribe();
        addToCartNetworkView.safeUnsubscribe();
        updateCartNetworkView.safeUnsubscribe();
        deleteCartNetworkView.safeUnsubscribe();
        categoryNetworkView.safeUnsubscribe();

        this.view = null;
    }

    private Action0 getOnStart() {
        return () -> view.onShowProgressBar();
    }

    private Action0 getOnComplete() {
        return () -> view.onHideProgressBar();
    }

    private Action1<String> getOnShowError() {
        return view::onShowError;
    }

    private Action1<IngredientsResponse> getOnIngredientsResponse() {
        return response -> view.showIngredients(response);
    }

    private Action1<Boolean> getOnCartResponse() {
        return System.out::println;
    }

    private Action1<List<Category>> getCategoryResponse() {

        return view::getCategories;
    }

    public void getIngredients(IngredientFilter filter) {

        ingredientsResponseNetworkView.callApi(() -> api.ingredients(
                getLocation().getStoreId(),
                LIMIT,
                filter.getCategory(),
                filter.getFromPrice(),
                filter.getToPrice(),
                filter.getPage()
        ));
    }

    public void getCategories() {

        categoryNetworkView.callApi(() -> api.ingredientCategory());
    }

    public void addToCart() {

    }

}
