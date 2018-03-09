package com.dailiv.view.register;

import android.util.Pair;

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

        Pair<String, String> namePair = splitName(registerBinding.getFullName());
        registerRequest.firstname = namePair.first;
        registerRequest.lastname = namePair.second;
        registerRequest.phone = registerBinding.getPhone();

        return registerRequest;
    }

    private Pair<String, String> splitName(String name) {

        String lastName = "";
        String firstName= "";
        if(name.split("\\w+").length>1){

            lastName = name.substring(name.lastIndexOf(" ")+1);
            firstName = name.substring(0, name.lastIndexOf(' '));
        }
        else{
            firstName = name;
        }

        return Pair.create(firstName, lastName);
    }

}
