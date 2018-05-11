package com.dailiv.internal.data.remote.response.history;

import com.dailiv.internal.data.remote.response.BaseResponse;
import com.dailiv.internal.data.remote.response.location.LocationResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by aldo on 5/10/18.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderHistoryResponse extends BaseResponse{

    @JsonProperty(value = "user_id")
    public int userId;

    @JsonProperty(value = "payment_method_id")
    public int paymentMethodId;

    @JsonProperty(value = "user_location_id")
    public int userLocationId;

    public int price;

    public int deliveryFee;

    public String status;

    public String note;

    public String date;

    @JsonProperty(value = "user_location")
    public LocationResponse userLocation;
}
