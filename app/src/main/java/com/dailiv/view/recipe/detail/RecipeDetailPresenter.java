package com.dailiv.view.recipe.detail;

import com.dailiv.internal.data.remote.IApi;
import com.dailiv.internal.data.remote.request.recipe.ThoughtRequest;
import com.dailiv.internal.data.remote.response.recipe.AddThoughtResponse;
import com.dailiv.internal.data.remote.response.recipe.RecipeDetailResponse;
import com.dailiv.util.network.NetworkView;
import com.dailiv.view.base.IPresenter;

import javax.inject.Inject;
import javax.inject.Named;

import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Created by aldo on 4/29/18.
 */

public class RecipeDetailPresenter implements IPresenter<RecipeDetailView>{

    @Inject
    @Named("common")
    IApi api;

    @Inject
    public RecipeDetailPresenter() {

    }

    private RecipeDetailView view;

    private NetworkView<RecipeDetailResponse> recipeDetailNetworkView;

    private NetworkView<AddThoughtResponse> addThoughtNetworkView;

    @Override
    public void onAttach(RecipeDetailView view) {

        this.view = view;

        recipeDetailNetworkView = new NetworkView<>(
                getOnStart(),
                getOnComplete(),
                getOnShowError(),
                getOnRecipeDetail()
        );

        addThoughtNetworkView = new NetworkView<>(
                getOnStart(),
                getOnComplete(),
                getOnShowError(),
                getOnAddThought()
        );

    }

    @Override
    public void onDetach() {

        recipeDetailNetworkView.safeUnsubscribe();
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

    private Action1<RecipeDetailResponse> getOnRecipeDetail() {
        return view::showDetail;
    }

    private Action1<AddThoughtResponse> getOnAddThought() {

        return view::onThoughtAdded;
    }

    public void getRecipeDetail(String identifier) {

        recipeDetailNetworkView.callApi(() -> api.getRecipeDetail(identifier));
    }

    public void addComment(int recipeId, String thought) {

        ThoughtRequest thoughtRequest = new ThoughtRequest();
        thoughtRequest.recipeId = recipeId;
        thoughtRequest.thought = thought;


        addThoughtNetworkView.callApi(() -> api.comment(thoughtRequest));
    }
}
