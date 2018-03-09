package com.dailiv.view.login;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.dailiv.App;
import com.dailiv.R;
import com.dailiv.databinding.ActivityLoginBinding;
import com.dailiv.internal.data.local.binding.LoginBinding;
import com.dailiv.internal.injector.component.DaggerActivityComponent;
import com.dailiv.internal.injector.module.ActivityModule;
import com.dailiv.util.common.Navigator;
import com.dailiv.view.base.AbstractActivity;
import com.dailiv.view.onboard.OnboardActivty;

import javax.inject.Inject;

/**
 * Created by aldo on 3/5/18.
 */

public class LoginActivity extends AbstractActivity implements LoginView{

    @Inject
    LoginPresenter presenter;

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
    public void showResponse(Object response) {
        System.out.println(response);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected void initComponents(Bundle savedInstanceState) {
        inject();
        onAttach();
        bindingLogin();

//        navigator.openActivity(this, OnboardActivty.class);
    }

    private void bindingLogin() {
        ActivityLoginBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.setLogin(new LoginBinding());
        binding.setPresenter(presenter);
    }
}
