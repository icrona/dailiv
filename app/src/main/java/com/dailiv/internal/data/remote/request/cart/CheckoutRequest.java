package com.dailiv.internal.data.remote.request.cart;

import com.dailiv.internal.data.local.pojo.Checkout;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by aldo on 4/30/18.
 */

public class CheckoutRequest {

    @JsonProperty(value = "gross_amount")
    public int grossAmount;

    @JsonProperty(value = "location_id")
    public int locationId;

    @JsonProperty(value = "delivery_fee")
    public int deliveryFee;

    public int distance = 20;

    public String note;

    @JsonProperty(value = "store_id")
    public int storeId;

    @JsonProperty(value = "discount_coupon")
    public String discountCoupon;

    @JsonProperty(value = "phone_number")
    public String phoneNumber;

    public CheckoutRequest(Checkout checkout) {
        this.grossAmount = checkout.getSubtotal();
        this.locationId = checkout.getLocationId();
        this.deliveryFee = checkout.getDeliveryFee();
        this.note = checkout.getNote();
        this.storeId = checkout.getStoreId();
        this.discountCoupon = checkout.getCouponCode();
        this.phoneNumber = checkout.getPhoneNumber();
    }
}
