package com.dailiv.view.account.history;

import android.os.Bundle;

import com.dailiv.App;
import com.dailiv.R;
import com.dailiv.internal.data.remote.response.history.OrderHistoryResponse;
import com.dailiv.internal.injector.component.DaggerActivityComponent;
import com.dailiv.internal.injector.module.ActivityModule;
import com.dailiv.view.base.AbstractActivity;

import javax.inject.Inject;

/**
 * Created by aldo on 5/11/18.
 */

public class OrderHistoryActivity extends AbstractActivity implements OrderHistoryView{

    @Inject
    OrderHistoryPresenter presenter;

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
    public void showResponse(OrderHistoryResponse response) {
        //todo
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_order_history;
    }

    @Override
    protected void initComponents(Bundle savedInstanceState) {
        inject();
        onAttach();
    }
}
