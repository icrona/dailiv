package com.dailiv.view.recipe;

import com.dailiv.internal.data.local.pojo.RecipeFilter;
import com.dailiv.internal.data.remote.IApi;
import com.dailiv.internal.data.remote.response.Category;
import com.dailiv.internal.data.remote.response.recipe.RecipesResponse;
import com.dailiv.util.network.NetworkView;
import com.dailiv.view.base.IPresenter;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Created by aldo on 4/1/18.
 */

public class RecipePresenter implements IPresenter<RecipeView>{

    @Inject
    @Named("common")
    IApi api;

    @Inject
    public RecipePresenter(){}

    private RecipeView view;

    private NetworkView<RecipesResponse> recipesResponseNetworkView;

    private NetworkView<List<Category>> categoryNetworkView;

    private static final int LIMIT = 10;

    @Override
    public void onAttach(RecipeView view) {
        this.view = view;
        recipesResponseNetworkView = new NetworkView<>(
                getOnStart(),
                getOnComplete(),
                getOnShowError(),
                getOnRecipesResponse()
        );

        categoryNetworkView = new NetworkView<>(
                getOnStart(),
                getOnComplete(),
                getOnShowError(),
                getCategoryResponse()
        );
    }

    @Override
    public void onDetach() {
        recipesResponseNetworkView.safeUnsubscribe();
        categoryNetworkView.safeUnsubscribe();
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

    private Action1<RecipesResponse> getOnRecipesResponse() {
        return response -> view.showRecipes(response);
    }

    private Action1<List<Category>> getCategoryResponse() {

        return view::getCategories;
    }

    public void getRecipes(RecipeFilter filter) {

        recipesResponseNetworkView.callApi(() -> api.recipes(
                LIMIT,
                filter.getCategory(),
                filter.getFromDuration(),
                filter.getToDuration(),
                filter.getSortBy().getKey(),
                filter.getDifficulty() == null ? null : filter.getDifficulty().getKey(),
                filter.getPage()
        ));
    }

    public void getCategories() {

        categoryNetworkView.callApi(() -> api.recipeCategory());
    }


}
