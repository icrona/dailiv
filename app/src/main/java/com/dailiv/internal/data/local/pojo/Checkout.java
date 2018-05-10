package com.dailiv.internal.data.local.pojo;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    public int getTotal() {
        return getDeliveryFee() + getSubtotal() - getDiscount();
    }

    public String getTotalString() {
        return stringify(getTotal());
    }

    public String getDeliveryFeeString() {
        return stringify(getDeliveryFee());
    }

    public String getSubtotalString() {
        return stringify(getSubtotal());
    }

    public String getDiscountString() {
        return stringify(getDiscount());
    }

    private String stringify(int value){
        return "Rp " + value;
    }

    public void setDiscountRate(double discountRate) {

        setDiscount((int)(getSubtotal() * discountRate));
    }
}
