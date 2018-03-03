package com.dailiv.internal.injector.module;

import com.dailiv.util.common.Common;
import com.dailiv.util.common.Navigator;
import com.dailiv.util.validator.ValidatorFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by aldo on 3/1/18.
 */

@Module
public class UtilityModule {

    @Provides
    @Singleton
    public ValidatorFactory provideValidator() {
        return new ValidatorFactory();
    }

    @Provides
    @Singleton
    public Common provideCommon() {
        return new Common();
    }

    @Provides
    @Singleton
    public Navigator provideNavigator() {
        return new Navigator();
    }
}
