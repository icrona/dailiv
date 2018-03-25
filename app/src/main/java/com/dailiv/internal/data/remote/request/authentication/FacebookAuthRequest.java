package com.dailiv.internal.data.remote.request.authentication;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by aldo on 3/24/18.
 */

public class FacebookAuthRequest extends BaseAuthRequest{

    @JsonProperty(value = "access_token")
    private String accessToken;

    public FacebookAuthRequest(String accessToken) {
        this.grantType = "social_login";
        this.accessToken = accessToken;
    }
}
