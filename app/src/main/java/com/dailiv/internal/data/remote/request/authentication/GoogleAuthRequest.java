package com.dailiv.internal.data.remote.request.authentication;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by aldo on 3/25/18.
 */

public class GoogleAuthRequest extends BaseAuthRequest {

    @JsonProperty(value = "token_id")
    private String tokenId;

    public GoogleAuthRequest(String tokenId) {
        this.grantType = "social_login";
        this.tokenId = tokenId;
    }
}
