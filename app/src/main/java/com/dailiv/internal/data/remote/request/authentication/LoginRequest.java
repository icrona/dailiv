package com.dailiv.internal.data.remote.request.authentication;

/**
 * Created by aldo on 3/5/18.
 */

public class LoginRequest extends BaseAuthRequest{

    public String email;

    public String username;

    public String password;

    public LoginRequest() {
        this.grantType = "password";
    }
}