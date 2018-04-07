package com.dailiv.view.main;

import com.dailiv.internal.data.local.pojo.SearchResult;
import com.dailiv.internal.data.remote.IApi;
import com.dailiv.internal.data.remote.response.home.SearchResponse;
import com.dailiv.util.network.NetworkView;
import com.dailiv.view.base.IPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import rx.functions.Action0;
import rx.functions.Action1;

import static com.dailiv.util.common.CollectionUtil.mapListToList;
import static com.dailiv.util.common.Preferences.getLocation;

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

    private NetworkView<SearchResponse> searchNetworkView;

    private MainView view;


    @Override
    public void onAttach(MainView view) {
        this.view = view;
        searchNetworkView = new NetworkView<>(
                getOnStart(),
                getOnComplete(),
                getOnShowError(),
                getSearchResult()
        );
    }

    @Override
    public void onDetach() {
        searchNetworkView.safeUnsubscribe();
        this.view = null;
    }

    public void doSearch(String query) {
        searchNetworkView.callApi(() ->api.search(query, getLocation().getStoreId()));
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

    private Action1<SearchResponse> getSearchResult() {
        return responses -> {

            List<SearchResult> searchResults = new ArrayList<>();
            searchResults.addAll(mapListToList(responses.recipe, SearchResult::new));
            searchResults.addAll(mapListToList(responses.ingredient, SearchResult::new));

            view.onGetSearchResult(searchResults);

        };
    }


}
