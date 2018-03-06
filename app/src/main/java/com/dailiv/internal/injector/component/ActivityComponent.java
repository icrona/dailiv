package com.dailiv.internal.injector.component;

import android.content.Context;

import com.dailiv.internal.injector.module.ActivityModule;
import com.dailiv.internal.injector.scope.ActivityContext;
import com.dailiv.internal.injector.scope.ActivityScope;
import com.dailiv.view.login.LoginActivity;
import com.dailiv.view.main.MainActivity;
import com.dailiv.view.register.RegisterActivity;
import com.dailiv.view.splash.SplashActivity;

import dagger.Component;

/**
 * Created by aldo on 3/1/18.
 */

@ActivityScope
@Component(
        dependencies = ApplicationComponent.class,
        modules = {ActivityModule.class}
)
public interface ActivityComponent {

    @ActivityContext
    Context getContext();

    void inject(SplashActivity splashActivity);

    void inject(MainActivity mainActivity);

    void inject(LoginActivity loginActivity);

    void inject(RegisterActivity registerActivity);
}
