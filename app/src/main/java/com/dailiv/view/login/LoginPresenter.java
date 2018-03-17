package com.dailiv.view.login;

import com.dailiv.internal.data.local.binding.LoginBinding;
import com.dailiv.internal.data.remote.IApi;
import com.dailiv.internal.data.remote.request.authentication.LoginRequest;
import com.dailiv.internal.data.remote.request.recipe.RecipeBaseRequest;
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

////        networkView.callApi(() -> api.cook(recipeBaseRequest()).map(mapResponseToObject()));
//
////        networkView.callApi(() -> api.search(loginBinding.getEmail()).map(mapResponseToObject()));
//
//        networkView.callApi(() -> api.home().map(mapResponseToObject()));
    }

    public void goToRegister() {
        view.goToRegister();
    }

    private RecipeBaseRequest recipeBaseRequest() {
        RecipeBaseRequest recipeBaseRequest = new RecipeBaseRequest();
        recipeBaseRequest.recipeId = 1;
        return recipeBaseRequest;
    }

    private LoginRequest getLoginRequest(LoginBinding loginBinding) {

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.email = loginBinding.getEmail();
        loginRequest.password = loginBinding.getPassword();
        loginRequest.username = loginBinding.getEmail();

        return loginRequest;
    }


}
