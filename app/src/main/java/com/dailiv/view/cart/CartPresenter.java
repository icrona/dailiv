package com.dailiv.view.cart;

import com.dailiv.internal.data.local.pojo.Checkout;
import com.dailiv.internal.data.local.pojo.Location;
import com.dailiv.internal.data.remote.IApi;
import com.dailiv.internal.data.remote.request.cart.CheckoutRequest;
import com.dailiv.internal.data.remote.request.cart.DeleteCartRequest;
import com.dailiv.internal.data.remote.request.cart.UpdateCartRequest;
import com.dailiv.internal.data.remote.response.cart.CartResponse;
import com.dailiv.util.network.NetworkView;
import com.dailiv.view.base.IPresenter;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import rx.functions.Action0;
import rx.functions.Action1;

import static com.dailiv.util.common.Preferences.getLocation;

/**
 * Created by aldo on 4/28/18.
 */

public class CartPresenter implements IPresenter<CartView>{

    @Inject
    @Named("common")
    IApi api;

    @Inject
    public CartPresenter() {
    }

    private CartView view;

    private NetworkView<List<CartResponse>> cartListNetworkView;

    private NetworkView<Boolean> updateCartNetworkView;

    private NetworkView<Boolean> deleteCartNetworkView;

    private NetworkView<Boolean> checkoutNetworkView;

    private NetworkView<Integer> deliveryFeeNetworkView;

    @Override
    public void onAttach(CartView view) {

        this.view = view;

        cartListNetworkView = new NetworkView<>(
                getOnStart(),
                getOnComplete(),
                getOnShowError(),
                getCartListResponse()
        );

        deleteCartNetworkView = new NetworkView<>(
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

        checkoutNetworkView = new NetworkView<>(
                getOnStart(),
                getOnComplete(),
                getOnShowError(),
                getOnCheckoutResponse()
        );

        deliveryFeeNetworkView = new NetworkView<>(
                getOnStart(),
                getOnComplete(),
                getOnShowError(),
                getOnDeliveryFeeResponse()
        );
    }

    @Override
    public void onDetach() {

        cartListNetworkView.safeUnsubscribe();
        deleteCartNetworkView.safeUnsubscribe();
        updateCartNetworkView.safeUnsubscribe();
        checkoutNetworkView.safeUnsubscribe();
        deliveryFeeNetworkView.safeUnsubscribe();
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

    private Action1<Boolean> getOnCartResponse() {
        return System.out::println;
    }

    private Action1<List<CartResponse>> getCartListResponse() {
        return view::showCartResponse;
    }

    public void getCartList() {

        Location location = getLocation();

        cartListNetworkView.callApi(() -> api.getCart(location.getLocationId()));

        deliveryFeeNetworkView.callApi(() -> api.getDeliveryFee(location.getStoreId(),location.getLocationId()));
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

    private Action1<Boolean> getOnCheckoutResponse() {
        return System.out::println;
    }

    private Action1<Integer> getOnDeliveryFeeResponse() {
        return view::onGetDeliveryFee;
    }

    public void checkout(Checkout checkout) {

        checkoutNetworkView.callApi(() -> api.checkout(new CheckoutRequest(checkout)));
    }

}
