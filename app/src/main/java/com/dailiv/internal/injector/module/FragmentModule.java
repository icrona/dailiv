package com.dailiv.internal.injector.module;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.dailiv.internal.injector.scope.ActivityContext;

import dagger.Module;
import dagger.Provides;

/**
 * Created by aldo on 3/1/18.
 */

@Module
public class FragmentModule {

    private final Fragment fragment;

    public FragmentModule(Fragment fragment) {
        this.fragment = fragment;
    }

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
