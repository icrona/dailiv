package com.dailiv.view.recipe;

import com.dailiv.internal.data.remote.IApi;
import com.dailiv.view.base.IPresenter;

import javax.inject.Inject;
import javax.inject.Named;

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

    @Override
    public void onAttach(RecipeView view) {
        this.view = view;
    }

    @Override
    public void onDetach() {
        this.view = null;
    }
}
