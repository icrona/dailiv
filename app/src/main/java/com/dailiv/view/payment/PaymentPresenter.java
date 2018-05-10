package com.dailiv.view.payment;

import com.dailiv.internal.data.local.pojo.Checkout;
import com.dailiv.internal.data.remote.IApi;
import com.dailiv.internal.data.remote.request.cart.CheckoutRequest;
import com.dailiv.util.network.NetworkView;
import com.dailiv.view.base.IPresenter;

import javax.inject.Inject;
import javax.inject.Named;

import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Created by aldo on 5/10/18.
 */

public class PaymentPresenter implements IPresenter<PaymentView>{

    @Inject
    @Named("common")
    IApi api;

    @Inject
    public PaymentPresenter(){}

    private PaymentView view;

    private NetworkView<Boolean> checkoutNetworkView;

    @Override
    public void onAttach(PaymentView view) {

        this.view = view;

        checkoutNetworkView = new NetworkView<>(
                getOnStart(),
                getOnComplete(),
                getOnShowError(),
                getOnCheckoutResponse()
        );
    }

    @Override
    public void onDetach() {

        checkoutNetworkView.safeUnsubscribe();
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

    private Action1<Boolean> getOnCheckoutResponse() {
        return view::onCheckoutResponse;
    }

    public void checkout(Checkout checkout) {

        checkoutNetworkView.callApi(() -> api.checkout(new CheckoutRequest(checkout)));
    }
}
