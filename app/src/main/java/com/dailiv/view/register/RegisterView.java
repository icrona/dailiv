package com.dailiv.view.register;

import com.dailiv.internal.data.remote.response.authentication.AuthenticationResponse;
import com.dailiv.view.base.IView;

/**
 * Created by aldo on 3/5/18.
 */

public interface RegisterView extends IView<AuthenticationResponse> {

    void goToLogin();

    void goToTerm();

    void goToPrivacyPolicy();
}
