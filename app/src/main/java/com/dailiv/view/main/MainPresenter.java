package com.dailiv.view.main;

import com.dailiv.internal.data.remote.IApi;
import com.dailiv.view.base.IPresenter;

import javax.inject.Inject;
import javax.inject.Named;

import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Created by aldo on 3/3/18.
 */

public class MainPresenter implements IPresenter<MainView> {

    @Inject
    @Named("common")
    IApi api;

    @Inject
    public MainPresenter() {

    }

    private MainView view;


    @Override
    public void onAttach(MainView view) {
        this.view = view;
    }

    @Override
    public void onDetach() {
        this.view = null;
    }


    private Action0 getOnStart() {
        return () -> view.onShowProgressBar();
    }

    private Action0 getOnComplete() {
        return () -> view.onHideProgressBar();
    }

    private Action1<String> getOnShowError() {
        return view::onShowError;
    }

}
