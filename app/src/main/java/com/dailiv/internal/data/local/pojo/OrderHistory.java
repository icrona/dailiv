package com.dailiv.internal.data.local.pojo;

import com.dailiv.internal.data.remote.response.history.OrderHistoryResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by aldo on 5/11/18.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderHistory {

    private String address;

    private String date;

    private int amount;

    private String status;

    public OrderHistory(OrderHistoryResponse response) {

        this(
                response.userLocation.address,
                response.date,
                response.price + response.deliveryFee,
                response.status
        );
    }

    public String getAmountString() {
        return String.valueOf(getAmount());
    }
}
