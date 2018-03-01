package com.dailiv.internal.injector.component;

import android.app.Application;
import android.content.Context;

import com.dailiv.internal.data.remote.IApi;
import com.dailiv.internal.injector.module.ApplicationModule;
import com.dailiv.internal.injector.module.NetworkModule;
import com.dailiv.internal.injector.module.UtilityModule;
import com.dailiv.internal.injector.scope.ApplicationContext;
import com.dailiv.util.validator.ValidatorFactory;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by aldo on 3/1/18.
 */

@Singleton
@Component(modules = {
        ApplicationModule.class,
        NetworkModule.class,
        UtilityModule.class
})
public interface ApplicationComponent {

    @ApplicationContext
    Context getContext();

    void inject(Application application);

    //TODO
    IApi getApi();

    ValidatorFactory getValidator();


}
