package com.dailiv.internal.data.remote.request.location;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by aldo on 3/11/18.
 */

public class AddLocationRequest {

    @JsonProperty(value = "formatted_address")
    public String formattedAddress;

    @JsonProperty(value = "place_id")
    public String placeId;

    public double latitude;

    public double longitude;
}
