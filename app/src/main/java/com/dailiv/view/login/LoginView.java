package com.dailiv.view.login;

import com.dailiv.internal.data.local.binding.LoginBinding;
import com.dailiv.internal.data.remote.response.authentication.AuthenticationResponse;
import com.dailiv.view.base.IView;

/**
 * Created by aldo on 3/5/18.
 */

public interface LoginView extends IView<AuthenticationResponse>{

    void goToRegister();

    void doLoginFb();

    void doLoginGoogle();

    boolean isValidLoginBinding(LoginBinding loginBinding);
}
