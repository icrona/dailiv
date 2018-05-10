package com.dailiv.internal.data.remote.response.checkout;

import com.dailiv.internal.data.remote.response.BaseResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by aldo on 5/10/18.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class CouponResponse extends BaseResponse{

    public String code;

    public String discount;

    @JsonProperty(value = "usage_limit")
    public int usageLimit;

    @JsonProperty(value = "expiry_date")
    public String expiryDate;

}
