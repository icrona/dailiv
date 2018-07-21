package com.dailiv.internal.data.local.pojo;

import android.text.SpannableString;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.dailiv.util.common.MoneyUtil.getMoneyString;

/**
 * Created by aldo on 5/1/18.
 */

@Data
@AllArgsConstructor
@Parcel(Parcel.Serialization.BEAN)
@NoArgsConstructor(onConstructor = @__(@ParcelConstructor))
public class Checkout {

    private int storeId;

    private int locationId;

    private int discount;

    private int deliveryFee;

    private int subtotal;

    private int total;

    private String addressDetail;

    private String couponCode;

    private String note;

    private String phoneNumber;

    public int getTotal() {
        return getDeliveryFee() + getSubtotal() - getDiscount();
    }

    public SpannableString getTotalString() {
        return stringify(getTotal());
    }

    public SpannableString getDeliveryFeeString() {
        return stringify(getDeliveryFee());
    }

    public SpannableString getSubtotalString() {
        return stringify(getSubtotal());
    }

    public SpannableString getDiscountString() {
        return stringify(getDiscount());
    }

    private SpannableString stringify(int value){
        return getMoneyString(value);
    }

    public void setDiscountRate(double discountRate) {

        setDiscount((int)(getSubtotal() * discountRate));
    }
}
