package com.dailiv.internal.data.remote.response.location;

import com.dailiv.internal.data.remote.response.BaseResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by aldo on 3/11/18.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class LocationResponse extends BaseResponse {

    @JsonProperty(value = "user_id")
    public int userId;

    public String address;

    @JsonProperty(value = "place_id")
    public int placeId;

    public String latitude;

    public String longitude;

    public String note;

    public String phone;

}
