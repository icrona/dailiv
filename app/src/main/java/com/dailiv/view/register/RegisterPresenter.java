package com.dailiv.view.register;

import com.dailiv.internal.data.local.binding.RegisterBinding;
import com.dailiv.internal.data.remote.IApi;
import com.dailiv.internal.data.remote.request.authentication.RegisterRequest;
import com.dailiv.view.base.AbstractSinglePresenter;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by aldo on 3/5/18.
 */

public class RegisterPresenter extends AbstractSinglePresenter<RegisterView> {

    @Inject
    @Named("public")
    IApi api;

    @Inject
    public RegisterPresenter() {
    }

    public void doRegister(RegisterBinding registerBinding) {

        networkView.callApi(() -> api.register(getRegisterRequest(registerBinding)).map(mapResponseToObject()));
    }

    private RegisterRequest getRegisterRequest(RegisterBinding registerBinding) {

        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.email = registerBinding.getEmail();
        registerRequest.password = registerBinding.getPassword();
        registerRequest.username = registerBinding.getEmail();
        registerRequest.firstname = registerBinding.getFirstName();
        registerRequest.lastname = registerBinding.getLastName();
        registerRequest.phone = registerBinding.getPhone();

        return registerRequest;
    }

}
