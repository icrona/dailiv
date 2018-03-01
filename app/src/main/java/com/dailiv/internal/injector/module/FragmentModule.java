package com.dailiv.internal.injector.module;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.dailiv.internal.injector.scope.ActivityContext;

import dagger.Module;
import dagger.Provides;
import lombok.AllArgsConstructor;

/**
 * Created by aldo on 3/1/18.
 */

@Module
@AllArgsConstructor
public class FragmentModule {

    private final Fragment fragment;

    @Provides
    public Fragment provideFragment() {
        return fragment;
    }

    @Provides
    @ActivityContext
    public Context provideFragmentContext() {
        return fragment.getContext();
    }
}
