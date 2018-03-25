package com.dailiv.internal.data.remote.request.authentication;

/**
 * Created by aldo on 3/5/18.
 */

public class RegisterRequest extends BaseAuthRequest{

    public String email;

    public String username;

    public String password;

    public String firstname;

    public String lastname;

    public String phone;

    public RegisterRequest() {
        this.grantType = "password";
    }
}