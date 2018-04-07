package com.dailiv.view.splash;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.dailiv.App;
import com.dailiv.internal.injector.component.DaggerActivityComponent;
import com.dailiv.internal.injector.module.ActivityModule;
import com.dailiv.util.common.Common;
import com.dailiv.util.common.Navigator;
import com.dailiv.view.location.LocationActivity;
import com.dailiv.view.register.RegisterActivity;

import javax.inject.Inject;

import static com.dailiv.util.common.Preferences.isAccessTokenAvailable;
import static com.dailiv.util.common.Preferences.isFinishOnboard;

/**
 * Created by aldo on 3/3/18.
 */

public class SplashActivity extends AppCompatActivity implements SplashView {

    @Inject
    SplashPresenter presenter;

    @Inject
    Common common;

    @Inject
    Navigator navigator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inject();
        onAttach();

        if(isFinishOnboard()){
            if(isAccessTokenAvailable()) {
                navigator.openMainActivity(this);
            }
            else{
                navigator.openLoginActivity(this);
            }

        }
        else{
            navigator.openOnboardActivity(this);
        }
    }

    @Override
    protected void onDestroy() {
        onDetach();
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        presenter.onDetach();
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
    public void onShowProgressBar() {

    }

    @Override
    public void onHideProgressBar() {

    }

    @Override
    public void onShowError(String message) {

    }
}
