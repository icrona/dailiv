package com.dailiv.view.location;

import android.os.Bundle;

import com.dailiv.App;
import com.dailiv.R;
import com.dailiv.internal.injector.component.DaggerActivityComponent;
import com.dailiv.internal.injector.module.ActivityModule;
import com.dailiv.view.base.AbstractActivity;

import javax.inject.Inject;

/**
 * Created by aldo on 3/19/18.
 */

public class LocationActivity extends AbstractActivity implements LocationView{

    @Inject
    LocationPresenter presenter;

    @Override
    public void onDetach() {
        presenter.onDetach();
    }

    @Override
    public void onShowProgressBar() {

    }

    @Override
    public void inject() {
        DaggerActivityComponent.builder()
                .applicationComponent(App.getComponent())
                .activityModule(new ActivityModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void onAttach() {
        presenter.onAttach(this);
    }

    @Override
    public void onHideProgressBar() {

    }

    @Override
    public void showResponse(Object response) {

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_location;
    }

    @Override
    protected void initComponents(Bundle savedInstanceState) {
        inject();
        onAttach();
    }
}
