package com.dailiv.view.notification;

import android.os.Bundle;

import com.dailiv.App;
import com.dailiv.R;
import com.dailiv.internal.injector.component.DaggerFragmentComponent;
import com.dailiv.internal.injector.module.FragmentModule;
import com.dailiv.view.base.AbstractFragment;

import javax.inject.Inject;

/**
 * Created by aldo on 4/1/18.
 */

public class NotificationFragment extends AbstractFragment implements NotificationView{

    @Inject
    NotificationPresenter presenter;

    @Override
    public void inject() {
        DaggerFragmentComponent.builder()
                .applicationComponent(App.getComponent())
                .fragmentModule(new FragmentModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void onAttach() {
        presenter.onAttach(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        presenter.onDetach();
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_notification;
    }

    @Override
    protected void initComponents(Bundle savedInstanceState) {
        inject();
        onAttach();
    }

    @Override
    public void onShowProgressBar() {

    }

    @Override
    public void onHideProgressBar() {

    }

    @Override
    protected String getScreenName() {
        return "Notification";
    }
}
