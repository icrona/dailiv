package com.dailiv.view.recipe.detail;

import com.dailiv.internal.data.local.pojo.MealPlanning;
import com.dailiv.internal.data.remote.IApi;
import com.dailiv.internal.data.remote.request.recipe.MealPlanningRequest;
import com.dailiv.internal.data.remote.request.recipe.RecipeBaseRequest;
import com.dailiv.internal.data.remote.request.recipe.ThoughtRequest;
import com.dailiv.internal.data.remote.response.recipe.AddThoughtResponse;
import com.dailiv.internal.data.remote.response.recipe.RecipeDetailResponse;
import com.dailiv.util.network.NetworkView;
import com.dailiv.view.base.IPresenter;

import javax.inject.Inject;
import javax.inject.Named;

import retrofit2.Response;
import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func0;

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

    private NetworkView<Boolean> recipeActionNetworkView;

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

        recipeActionNetworkView = new NetworkView<>(
                getOnStart(),
                getOnComplete(),
                getOnShowError(),
                getOnRecipeAction()
        );

    }

    @Override
    public void onDetach() {

        recipeDetailNetworkView.safeUnsubscribe();
        addThoughtNetworkView.safeUnsubscribe();
        recipeActionNetworkView.safeUnsubscribe();
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

    private Action1<Boolean> getOnRecipeAction() {

        return System.out::println;
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

    public void cook(int recipeId) {

        recipeAction(() -> api.cook(getRecipeBaseRequest(recipeId)));
    }

    public void unCook(int recipeId) {

        recipeAction(() -> api.uncook(getRecipeBaseRequest(recipeId)));
    }

    public void like(int recipeId) {

        recipeAction(() -> api.like(getRecipeBaseRequest(recipeId)));
    }

    public void unLike(int recipeId) {

        recipeAction(() -> api.unlike(getRecipeBaseRequest(recipeId)));
    }

    public void addMealPlanning(MealPlanning mealPlanning) {

        MealPlanningRequest mealPlanningRequest = new MealPlanningRequest();

        mealPlanningRequest.recipeId = mealPlanning.getRecipeId();
        mealPlanningRequest.planningCategory = mealPlanning.getPlanningCategory().getKey();
        mealPlanningRequest.planningDate = mealPlanning.getPlanningDateString();

        recipeAction(() -> api.mealPlanning(mealPlanningRequest));

    }

    private RecipeBaseRequest getRecipeBaseRequest(int recipeId) {

        RecipeBaseRequest recipeBaseRequest = new RecipeBaseRequest();
        recipeBaseRequest.recipeId = recipeId;

        return recipeBaseRequest;
    }


    private void recipeAction(Func0<Observable<Response<Boolean>>> func) {
        recipeActionNetworkView.callApi(func);
    }
}
