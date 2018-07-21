package com.dailiv.view.delivery;

import com.dailiv.internal.data.remote.IApi;
import com.dailiv.internal.data.remote.response.checkout.CouponResponse;
import com.dailiv.internal.data.remote.response.profile.ProfileResponse;
import com.dailiv.util.network.NetworkView;
import com.dailiv.view.base.IPresenter;

import javax.inject.Inject;
import javax.inject.Named;

import rx.functions.Action0;
import rx.functions.Action1;

import static com.dailiv.util.common.Preferences.getAccountSlug;

/**
 * Created by aldo on 5/10/18.
 */

public class DeliveryPresenter implements IPresenter<DeliveryView>{

    @Inject
    @Named("common")
    IApi api;

    @Inject
    public DeliveryPresenter() {}

    private DeliveryView view;

    private NetworkView<CouponResponse> couponNetworkView;

    private NetworkView<ProfileResponse> profileNetworkView;

    @Override
    public void onAttach(DeliveryView view) {

        this.view = view;

        couponNetworkView = new NetworkView<>(
                getOnStart(),
                getOnComplete(),
                getOnShowError(),
                getOnCouponResponse()
        );

        profileNetworkView = new NetworkView<>(
                getOnStart(),
                getOnComplete(),
                getOnShowError(),
                view::onGetProfile
        );
    }

    @Override
    public void onDetach() {

        couponNetworkView.safeUnsubscribe();
        profileNetworkView.safeUnsubscribe();
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

    private Action1<CouponResponse> getOnCouponResponse() {
        return view::onCheckDiscountCoupon;
    }

    public void checkCoupon(String code) {
        couponNetworkView.callApi(() -> api.discountCoupon(code));
    }

    public void getProfile() {

        profileNetworkView.callApi(() -> api.getProfileBySlug(getAccountSlug()));
    }
}
