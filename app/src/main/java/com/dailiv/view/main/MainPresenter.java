package com.dailiv.view.main;

import com.annimon.stream.Stream;
import com.dailiv.internal.data.local.pojo.SearchResult;
import com.dailiv.internal.data.remote.IApi;
import com.dailiv.internal.data.remote.response.home.SearchResponse;
import com.dailiv.util.network.NetworkView;
import com.dailiv.view.base.IPresenter;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import rx.functions.Action0;
import rx.functions.Action1;

import static com.annimon.stream.Collectors.toList;

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

    private NetworkView<List<SearchResponse>> searchNetworkView;

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
        this.view = null;
    }

    public void doSearch(String query) {
        searchNetworkView.callApi(() ->api.search(query));
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

    private Action1<List<SearchResponse>> getSearchResult() {
        return responses -> {

            List<SearchResult> searchResults = Stream.of(responses)
                    .map(SearchResult::new)
                    .collect(toList());
            view.onGetSearchResult(searchResults);
        };
    }
}
