package com.dailiv.internal.injector.module;

import android.app.Activity;
import android.content.Context;

import com.dailiv.internal.injector.scope.ActivityContext;

import dagger.Module;
import dagger.Provides;
import lombok.AllArgsConstructor;

/**
 * Created by aldo on 3/1/18.
 */

@Module
@AllArgsConstructor
public class ActivityModule {

    private final Activity activity;

    @Provides
    public Activity provideActivity() {
        return activity;
    }

    @Provides
    @ActivityContext
    public Context provideActivityContext() {
        return activity;
    }


}
