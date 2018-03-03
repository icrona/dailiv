package com.dailiv.view.main;

import com.dailiv.view.base.IPresenter;

import javax.inject.Inject;

/**
 * Created by aldo on 3/3/18.
 */

public class MainPresenter implements IPresenter<MainView> {

    private MainView view;

    @Inject
    public MainPresenter() {

    }

    @Override
    public void onAttach(MainView view) {
        this.view = view;
    }

    @Override
    public void onDetach() {
        this.view = null;
    }
}
