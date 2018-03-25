package com.dailiv.internal.data.remote.request.location;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;

/**
 * Created by aldo on 3/25/18.
 */

@AllArgsConstructor
public class ChooseLocationRequest {

    @JsonProperty(value = "location_id")
    public int locationId;
}
