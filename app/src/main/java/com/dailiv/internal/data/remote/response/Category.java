package com.dailiv.internal.data.remote.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;

/**
 * Created by aldo on 3/11/18.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Category extends BaseResponse {

    @Getter
    public String name;
}
