package com.dailiv.view.shop.detail;

import com.dailiv.internal.data.remote.IApi;
import com.dailiv.internal.data.remote.response.ingredient.IngredientDetailResponse;
import com.dailiv.util.network.NetworkView;
import com.dailiv.view.base.IPresenter;

import javax.inject.Inject;
import javax.inject.Named;

import rx.functions.Action0;
import rx.functions.Action1;

import static com.dailiv.util.common.Preferences.getLocation;

/**
 * Created by aldo on 4/29/18.
 */

public class IngredientDetailPresenter implements IPresenter<IngredientDetailView>{

    @Inject
    @Named("common")
    IApi api;


    @Inject
    public IngredientDetailPresenter() {

    }

    private IngredientDetailView view;

    private NetworkView<IngredientDetailResponse> ingredientDetailResponseNetworkView;



    @Override
    public void onAttach(IngredientDetailView view) {

        this.view = view;

        ingredientDetailResponseNetworkView = new NetworkView<>(
                getOnStart(),
                getOnComplete(),
                getOnShowError(),
                getOnIngredientDetail()
        );
    }

    @Override
    public void onDetach() {

        ingredientDetailResponseNetworkView.safeUnsubscribe();
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

    private Action1<IngredientDetailResponse> getOnIngredientDetail() {
        return view::showDetail;
    }

    public void getIngredientDetail(String identifer) {

        ingredientDetailResponseNetworkView.callApi(() -> api.getIngredientDetail(identifer, getLocation().getStoreId()));
    }
}
