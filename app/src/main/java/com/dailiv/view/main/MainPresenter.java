package com.dailiv.view.main;

import com.annimon.stream.Stream;
import com.dailiv.internal.data.local.pojo.Location;
import com.dailiv.internal.data.local.pojo.SearchResult;
import com.dailiv.internal.data.remote.IApi;
import com.dailiv.internal.data.remote.request.review.SubmitReviewRequest;
import com.dailiv.internal.data.remote.response.cart.CartResponse;
import com.dailiv.internal.data.remote.response.home.SearchResponse;
import com.dailiv.internal.data.remote.response.review.ReviewNeededResponse;
import com.dailiv.util.network.NetworkView;
import com.dailiv.view.base.IPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import rx.functions.Action0;
import rx.functions.Action1;

import static com.annimon.stream.Collectors.summingInt;
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

    private NetworkView<List<CartResponse>> cartListNetworkView;

    private NetworkView<SearchResponse> searchNetworkView;

    private NetworkView<ReviewNeededResponse> reviewNeededNetworkView;

    private NetworkView<Boolean> submitReviewNetworkView;

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

        reviewNeededNetworkView = new NetworkView<>(
                getOnStart(),
                getOnComplete(),
                getOnShowError(),
                getOnReviewNeeded()
        );

        submitReviewNetworkView = new NetworkView<>(
                getOnStart(),
                getOnComplete(),
                getOnShowError(),
                getOnSubmitReview()
        );

        cartListNetworkView = new NetworkView<>(
                getOnStart(),
                getOnComplete(),
                getOnShowError(),
                getCartListResponse()
        );


    }

    private Action1<List<CartResponse>> getCartListResponse() {

        return response -> view.onGetCartCount(Stream.of(response)
                .collect(summingInt(CartResponse::getAmount)));
    }

    public void getCartCount() {

        Location location = getLocation();

        cartListNetworkView.callApi(() -> api.getCart(location.getLocationId()));

    }

    @Override
    public void onDetach() {
        searchNetworkView.safeUnsubscribe();
        reviewNeededNetworkView.safeUnsubscribe();
        submitReviewNetworkView.safeUnsubscribe();
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

    private Action1<ReviewNeededResponse> getOnReviewNeeded() {

        return view::onGetReviewNeeded;
    }

    private Action1<Boolean> getOnSubmitReview() {

        return System.out::println;
    }

    public void checkReviewIsNeeded() {

        reviewNeededNetworkView.callApi(() -> api.reviewNeeded());
    }

    public void submitReview(SubmitReviewRequest submitReviewRequest) {

        submitReviewNetworkView.callApi(() -> api.submitReview(submitReviewRequest));
    }

}
