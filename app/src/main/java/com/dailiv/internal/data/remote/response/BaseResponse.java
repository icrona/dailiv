package com.dailiv.internal.data.remote.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by aldo on 3/10/18.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseResponse {

    public int id;

    @JsonProperty(value = "created_at")
    public String createdAt;

    @JsonProperty(value = "updated_at")
    public String updatedAt;

}
