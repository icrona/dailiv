package com.dailiv.view.splash;

import com.dailiv.view.base.IPresenter;

import javax.inject.Inject;

/**
 * Created by aldo on 3/3/18.
 */

public class SplashPresenter implements IPresenter<SplashView> {

    private SplashView view;

    @Inject
    public SplashPresenter() {

    }

    @Override
    public void onAttach(SplashView view) {
        this.view = view;
    }

    @Override
    public void onDetach() {
        this.view = null;
    }
}
