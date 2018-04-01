package com.dailiv.view.home;

import com.dailiv.internal.data.remote.IApi;
import com.dailiv.internal.data.remote.response.home.HomeResponse;
import com.dailiv.util.network.NetworkView;
import com.dailiv.view.base.IPresenter;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by aldo on 4/1/18.
 */

public class HomePresenter implements IPresenter<HomeView> {

    @Inject
    @Named("common")
    IApi api;

    @Inject
    public HomePresenter() {}

    private HomeView view;

    private NetworkView<HomeResponse> homeNetworkView;

    @Override
    public void onAttach(HomeView view) {
        this.view = view;
    }

    @Override
    public void onDetach() {
        this.view = null;
    }
}
