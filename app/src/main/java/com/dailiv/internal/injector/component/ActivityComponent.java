package com.dailiv.internal.injector.component;

import android.content.Context;

import com.dailiv.internal.injector.module.ActivityModule;
import com.dailiv.internal.injector.scope.ActivityContext;
import com.dailiv.internal.injector.scope.ActivityScope;

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

    //TODO
//    void inject(Activity activity);
}
