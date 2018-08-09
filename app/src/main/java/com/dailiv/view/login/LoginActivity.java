package com.dailiv.view.login;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.widget.Toast;

import com.dailiv.App;
import com.dailiv.R;
import com.dailiv.databinding.ActivityLoginBinding;
import com.dailiv.internal.data.local.binding.LoginBinding;
import com.dailiv.internal.data.remote.response.authentication.AuthenticationResponse;
import com.dailiv.internal.injector.component.DaggerActivityComponent;
import com.dailiv.internal.injector.module.ActivityModule;
import com.dailiv.util.common.Navigator;
import com.dailiv.util.validator.ValidatorFactory;
import com.dailiv.view.base.AbstractActivity;
import com.dailiv.view.register.RegisterActivity;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import javax.inject.Inject;

import butterknife.BindView;

import static com.dailiv.util.common.Preferences.setAccessTokenAndSlug;

/**
 * Created by aldo on 3/5/18.
 */

public class LoginActivity extends AbstractActivity implements LoginView{

    @Inject
    LoginPresenter presenter;

    @Inject
    Navigator navigator;

    @Inject
    ValidatorFactory validatorFactory;

    @BindView(R.id.fb_login_button)
    LoginButton fbLoginBtn;

    CallbackManager callbackManager;

    GoogleSignInClient googleSignInClient;

    private static final int GOOGLE_AUTH_RC = 1300;

    @Override
    public void onDetach() {
        presenter.onDetach();
    }

    @Override
    public void onShowProgressBar() {

    }

    @Override
    public void inject() {
        DaggerActivityComponent.builder()
                .applicationComponent(App.getComponent())
                .activityModule(new ActivityModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void onAttach() {
        presenter.onAttach(this);
    }

    @Override
    public void onHideProgressBar() {

    }

    @Override
    public void showResponse(AuthenticationResponse response) {

        setAccessTokenAndSlug(response.accessToken, response.user.slug);
        navigator.openMainActivity(this);
    }

    @Override
    protected int getContentView() {
        FacebookSdk.sdkInitialize(this.getApplicationContext(), 1200);

        return R.layout.activity_login;
    }

    @Override
    protected void initComponents(Bundle savedInstanceState) {
        inject();
        onAttach();
        bindingLogin();
        callbackFb();
        setUpGoogleLogin();
    }

    private void bindingLogin() {
        ActivityLoginBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.setLogin(new LoginBinding());
        binding.setPresenter(presenter);
    }

    @Override
    public void goToRegister() {
        navigator.openActivity(this, RegisterActivity.class);
    }

    @Override
    public void doLoginFb() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if(accessToken != null) {
            presenter.doFacebookLogin(accessToken.getToken());
        }
        else{
            fbLoginBtn.performClick();
        }
    }

    private void setUpGoogleLogin() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.google_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);

    }

    @Override
    public void doLoginGoogle() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, GOOGLE_AUTH_RC);
    }

    private void callbackFb() {
        callbackManager = CallbackManager.Factory.create();
        fbLoginBtn.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                System.out.println(loginResult.getAccessToken().getToken());
                presenter.doFacebookLogin(loginResult.getAccessToken().getToken());

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int responseCode,
                                    Intent data) {
        super.onActivityResult(requestCode, responseCode, data);
        if (FacebookSdk.isFacebookRequestCode(requestCode)) {
            callbackManager.onActivityResult(requestCode, responseCode, data);
        }
        else if (requestCode == GOOGLE_AUTH_RC) {

            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            System.out.println(account.getIdToken());

            presenter.doGoogleLogin(account.getIdToken());
        } catch (ApiException e) {
            System.out.println(e.getStatusCode());
        }
    }

    @Override
    public boolean isValidLoginBinding(LoginBinding loginBinding) {

        if(!validatorFactory.getEmailValidator().isValid(loginBinding.getEmail())) {

            Toast.makeText(this, R.string.email_not_valid, Toast.LENGTH_SHORT).show();
            return false;
        }

        if(loginBinding.getPassword() == null || loginBinding.getPassword().equals("")) {

            Toast.makeText(this, R.string.password_empty, Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
