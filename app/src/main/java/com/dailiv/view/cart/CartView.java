package com.dailiv.view.cart;

import com.dailiv.internal.data.remote.response.cart.CartResponse;
import com.dailiv.view.base.IBaseView;

import java.util.List;

/**
 * Created by aldo on 4/28/18.
 */

public interface CartView extends IBaseView {

    void showCartResponse(List<CartResponse> cartResponses);

    void onGetDeliveryFee(int deliveryFee);
}
