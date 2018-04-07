package com.dailiv.view.register;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.dailiv.App;
import com.dailiv.R;
import com.dailiv.databinding.ActivityRegisterBinding;
import com.dailiv.internal.data.local.binding.RegisterBinding;
import com.dailiv.internal.data.remote.response.authentication.AuthenticationResponse;
import com.dailiv.internal.injector.component.DaggerActivityComponent;
import com.dailiv.internal.injector.module.ActivityModule;
import com.dailiv.util.common.Navigator;
import com.dailiv.view.base.AbstractActivity;
import com.dailiv.view.main.MainActivity;

import javax.inject.Inject;

import static com.dailiv.util.common.Preferences.setAccessToken;

/**
 * Created by aldo on 3/5/18.
 */

public class RegisterActivity extends AbstractActivity implements RegisterView{

    @Inject
    RegisterPresenter presenter;

    @Inject
    Navigator navigator;

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
    public void showResponse(AuthenticationResponse authResponse) {
        setAccessToken(authResponse.accessToken);
        navigator.openMainActivity(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_register;
    }

    @Override
    protected void initComponents(Bundle savedInstanceState) {
        inject();
        onAttach();
        bindingRegister();
    }

    private void bindingRegister() {
        ActivityRegisterBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        binding.setRegister(new RegisterBinding());
        binding.setPresenter(presenter);
    }
}
