package com.dailiv.internal.injector.component;

import android.content.Context;

import com.dailiv.internal.injector.module.FragmentModule;
import com.dailiv.internal.injector.scope.ActivityContext;
import com.dailiv.internal.injector.scope.FragmentScope;

import dagger.Component;

/**
 * Created by aldo on 3/1/18.
 */

@FragmentScope
@Component(
        dependencies = ApplicationComponent.class,
        modules = FragmentModule.class
)
public interface FragmentComponent {

    @ActivityContext
    Context getContext();

    //TODO
}
