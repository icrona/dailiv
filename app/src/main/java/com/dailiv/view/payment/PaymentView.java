package com.dailiv.view.payment;

import com.dailiv.view.base.IBaseView;

/**
 * Created by aldo on 5/10/18.
 */

public interface PaymentView extends IBaseView{

    void onCheckoutResponse(boolean response);
}
