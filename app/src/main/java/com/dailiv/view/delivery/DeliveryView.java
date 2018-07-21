package com.dailiv.view.delivery;

import com.dailiv.internal.data.remote.response.checkout.CouponResponse;
import com.dailiv.internal.data.remote.response.profile.ProfileResponse;
import com.dailiv.view.base.IBaseView;

/**
 * Created by aldo on 5/10/18.
 */

public interface DeliveryView extends IBaseView{

    void onCheckDiscountCoupon(CouponResponse couponResponse);

    void onGetProfile(ProfileResponse profileResponse);

}
