package com.dailiv.view.account.history;

import com.dailiv.internal.data.remote.IApi;
import com.dailiv.view.base.AbstractSinglePresenter;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by aldo on 5/11/18.
 */

public class OrderHistoryPresenter extends AbstractSinglePresenter<OrderHistoryView>{

    @Inject
    @Named("common")
    IApi api;

    @Inject
    public OrderHistoryPresenter() {}

    public void getOrderHistory() {
        networkView.callApi(() -> api.orderHistory().map(mapResponseToObject()));
    }

}
