package com.dailiv.internal.data.remote.request.authentication;

import com.dailiv.BuildConfig;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by aldo on 3/24/18.
 */

public class BaseAuthRequest {

    @JsonProperty(value = "grant_type")
    protected String grantType;

    @JsonProperty(value = "client_id")
    protected String clientId = BuildConfig.CLIENT_ID;

    @JsonProperty(value = "client_secret")
    protected String clientSecret = BuildConfig.CLIENT_SECRET;
}
