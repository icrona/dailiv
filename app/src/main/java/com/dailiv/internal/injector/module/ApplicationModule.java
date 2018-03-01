package com.dailiv.internal.injector.module;

import android.app.Application;
import android.content.Context;

import com.dailiv.internal.injector.scope.ApplicationContext;

import dagger.Module;
import dagger.Provides;
import lombok.AllArgsConstructor;

/**
 * Created by aldo on 3/1/18.
 */

@Module
@AllArgsConstructor
public class ApplicationModule {

    private final Application application;

    @Provides
    public Application provideApplication() {
        return application;
    }

    @Provides
    @ApplicationContext
    public Context provideApplicationContext() {
        return application;
    }
}
