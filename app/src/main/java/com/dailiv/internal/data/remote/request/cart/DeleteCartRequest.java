package com.dailiv.internal.data.remote.request.cart;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by aldo on 4/14/18.
 */

public class DeleteCartRequest {

    @JsonProperty(value = "cart_id")
    public int cartId;

}
