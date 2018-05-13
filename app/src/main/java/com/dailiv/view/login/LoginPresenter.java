package com.dailiv.view.login;

import com.dailiv.internal.data.local.binding.LoginBinding;
import com.dailiv.internal.data.remote.IApi;
import com.dailiv.internal.data.remote.request.authentication.FacebookAuthRequest;
import com.dailiv.internal.data.remote.request.authentication.GoogleAuthRequest;
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

        if(view.isValidLoginBinding(loginBinding)){
            networkView.callApi(() -> api.login(getLoginRequest(loginBinding)).map(mapResponseToObject()));
        }

    }

    public void doLoginFb() {
        view.doLoginFb();
    }

    public void doLoginGoogle() {
        view.doLoginGoogle();
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

    public void doFacebookLogin(String accessToken) {
        networkView.callApi(() -> api.fbAuth(new FacebookAuthRequest(accessToken)).map(mapResponseToObject()));
    }

    public void doGoogleLogin(String idToken) {
        networkView.callApi(() -> api.googleAuth(new GoogleAuthRequest(idToken)).map(mapResponseToObject()));
    }


}
