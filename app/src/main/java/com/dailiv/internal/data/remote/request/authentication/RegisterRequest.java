package com.dailiv.internal.data.remote.request.authentication;

import com.dailiv.BuildConfig;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by aldo on 3/5/18.
 */

public class RegisterRequest {

    public String email;

    public String username;

    public String password;

    public String firstname;

    public String lastname;

    public String phone;

    @JsonProperty(value = "grant_type")
    private String grantType = "password";

    @JsonProperty(value = "client_id")
    private String clientId = BuildConfig.CLIENT_ID;

    @JsonProperty(value = "client_secret")
    private String clientSecret = BuildConfig.CLIENT_SECRET;
}