package com.dailiv.internal.data.remote.response.authentication;

import com.dailiv.internal.data.remote.response.UserResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by aldo on 3/5/18.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthenticationResponse {

    @JsonProperty(value = "access_token")
    public String accessToken;

    @JsonProperty(value = "token_type")
    public String tokenType;

    @JsonProperty(value = "expires_in")
    public Long expiresIn;

    public UserResponse user;

}
