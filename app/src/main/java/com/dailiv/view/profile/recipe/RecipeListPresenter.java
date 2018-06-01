package com.dailiv.view.profile.recipe;

import com.dailiv.internal.data.local.pojo.MealPlanning;
import com.dailiv.internal.data.local.pojo.ProfileRecipeList;
import com.dailiv.internal.data.remote.IApi;
import com.dailiv.internal.data.remote.request.recipe.MealPlanningRequest;
import com.dailiv.internal.data.remote.response.recipe.Recipe;
import com.dailiv.util.network.NetworkView;
import com.dailiv.view.base.IPresenter;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Created by aldo on 6/1/18.
 */

public class RecipeListPresenter implements IPresenter<RecipeListView>{

    @Inject
    @Named("common")
    IApi api;

    @Inject
    public RecipeListPresenter() {}

    private RecipeListView view;

    private NetworkView<List<Recipe>> recipesNetworkView;

    private NetworkView<Boolean> mealPlanningNetworkView;

    public void getRecipeList(ProfileRecipeList profileRecipeList) {

        recipesNetworkView.callApi(() -> api.getRecipeByProfileId(
                profileRecipeList.getUserId(),
                profileRecipeList.getRecipeType()
        ));
    }

    @Override
    public void onAttach(RecipeListView view) {
        this.view = view;

        recipesNetworkView = new NetworkView<>(
                getOnStart(),
                getOnComplete(),
                getOnShowError(),
                getOnRecipesResponse()
        );

        mealPlanningNetworkView = new NetworkView<>(
                getOnStart(),
                getOnComplete(),
                getOnShowError(),
                getOnAddMealPlanning()
        );
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

    private Action1<List<Recipe>> getOnRecipesResponse() {
        return response -> view.showRecipes(response);
    }

    private Action1<Boolean> getOnAddMealPlanning() {
        return aBoolean -> {

        };
    }

    public void addMealPlanning(MealPlanning mealPlanning) {

        MealPlanningRequest mealPlanningRequest = new MealPlanningRequest();

        mealPlanningRequest.recipeId = mealPlanning.getRecipeId();
        mealPlanningRequest.planningCategory = mealPlanning.getPlanningCategory().getKey();
        mealPlanningRequest.planningDate = mealPlanning.getPlanningDateString();

        mealPlanningNetworkView.callApi(() -> api.mealPlanning(mealPlanningRequest));

    }

    @Override
    public void onDetach() {

        recipesNetworkView.safeUnsubscribe();
        mealPlanningNetworkView.safeUnsubscribe();
        this.view = null;
    }
}
