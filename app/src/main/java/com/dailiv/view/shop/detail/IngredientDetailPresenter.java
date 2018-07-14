package com.dailiv.view.shop.detail;

import com.annimon.stream.Stream;
import com.dailiv.internal.data.local.pojo.Location;
import com.dailiv.internal.data.remote.IApi;
import com.dailiv.internal.data.remote.request.cart.AddToCartRequest;
import com.dailiv.internal.data.remote.request.cart.DeleteCartRequest;
import com.dailiv.internal.data.remote.request.cart.UpdateCartRequest;
import com.dailiv.internal.data.remote.response.cart.CartResponse;
import com.dailiv.internal.data.remote.response.ingredient.IngredientDetailResponse;
import com.dailiv.util.network.NetworkView;
import com.dailiv.view.base.IPresenter;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import rx.functions.Action0;
import rx.functions.Action1;

import static com.annimon.stream.Collectors.summingInt;
import static com.dailiv.util.common.Preferences.getLocation;

/**
 * Created by aldo on 4/29/18.
 */

public class IngredientDetailPresenter implements IPresenter<IngredientDetailView>{

    @Inject
    @Named("common")
    IApi api;


    @Inject
    public IngredientDetailPresenter() {

    }

    private IngredientDetailView view;

    private NetworkView<IngredientDetailResponse> ingredientDetailResponseNetworkView;

    private NetworkView<CartResponse> addToCartNetworkView;

    private NetworkView<Boolean> updateCartNetworkView;

    private NetworkView<Boolean> deleteCartNetworkView;

    private NetworkView<List<CartResponse>> cartListNetworkView;


    @Override
    public void onAttach(IngredientDetailView view) {

        this.view = view;

        ingredientDetailResponseNetworkView = new NetworkView<>(
                getOnStart(),
                getOnComplete(),
                getOnShowError(),
                getOnIngredientDetail()
        );

        addToCartNetworkView = new NetworkView<>(
                getOnStart(),
                getOnComplete(),
                getOnShowError(),
                getAddToCartResponse()
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

        cartListNetworkView = new NetworkView<>(
                getOnStart(),
                getOnComplete(),
                getOnShowError(),
                getCartListResponse()
        );

    }

    private Action1<List<CartResponse>> getCartListResponse() {

        return response -> view.onGetCartCount(Stream.of(response)
                .collect(summingInt(CartResponse::getAmount)));
    }

    public void getCartCount() {

        Location location = getLocation();

        cartListNetworkView.callApi(() -> api.getCart(location.getLocationId()));

    }


    @Override
    public void onDetach() {

        ingredientDetailResponseNetworkView.safeUnsubscribe();
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

    private Action1<IngredientDetailResponse> getOnIngredientDetail() {
        return view::showDetail;
    }

    public void getIngredientDetail(String identifer) {

        ingredientDetailResponseNetworkView.callApi(() -> api.getIngredientDetail(identifer, getLocation().getStoreId()));
    }

    private Action1<Boolean> getOnCartResponse() {
        return System.out::println;
    }

    private Action1<CartResponse> getAddToCartResponse() {
        return cartResponse -> view.onAddToCart(cartResponse.id, cartResponse.amount, cartResponse.ingredient.id);
    }

    public void addToCart(int ingredientAmount, int storeIngredientId) {

        AddToCartRequest addToCartRequest = new AddToCartRequest();

        addToCartRequest.ingredientAmount = ingredientAmount;
        addToCartRequest.storeIngredientId = storeIngredientId;

        addToCartNetworkView.callApi(() -> api.addToCart(addToCartRequest));
    }

    public void deleteCart(int cartId) {

        DeleteCartRequest deleteCartRequest = new DeleteCartRequest();

        deleteCartRequest.cartId = cartId;

        deleteCartNetworkView.callApi(() -> api.deleteCart(deleteCartRequest));
    }

    public void updateCart(int cartId, int quantity) {

        UpdateCartRequest updateCartRequest = new UpdateCartRequest();

        updateCartRequest.cartId = cartId;
        updateCartRequest.amount = quantity;

        updateCartNetworkView.callApi(() -> api.updateCart(updateCartRequest));

    }
}
