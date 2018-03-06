package com.dailiv.view.login;

import com.dailiv.internal.data.local.binding.LoginBinding;
import com.dailiv.internal.data.remote.IApi;
import com.dailiv.internal.data.remote.request.authentication.LoginRequest;
import com.dailiv.view.base.AbstractSinglePresenter;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by aldo on 3/5/18.
 */

public class LoginPresenter extends AbstractSinglePresenter<LoginView> {

    @Inject
    @Named("public")
    IApi api;

    @Inject
    public LoginPresenter() {}

    public void doLogin(LoginBinding loginBinding) {

        networkView.callApi(() -> api.login(getLoginRequest(loginBinding)).map(mapResponseToObject()));
    }

    private LoginRequest getLoginRequest(LoginBinding loginBinding) {

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.email = loginBinding.getEmail();
        loginRequest.password = loginBinding.getPassword();
        loginRequest.username = loginBinding.getEmail();

        return loginRequest;
    }


}
