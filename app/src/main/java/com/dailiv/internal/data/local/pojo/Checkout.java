package com.dailiv.internal.data.local.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by aldo on 5/1/18.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Checkout {

    private int storeId;

    private int locationId;

    private int deliveryFee;

    private int subtotal;

    private int total;

    private String addressDetail;

    private String couponCode;

    private String note;

    public int getTotal() {
        return deliveryFee + subtotal;
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

    private String stringify(int value){
        return "Rp " + value;
    }
}
