package com.dailiv.view.profile.mealplan;

import com.dailiv.internal.data.remote.IApi;
import com.dailiv.view.base.AbstractSinglePresenter;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by aldo on 5/27/18.
 */

public class MealPlanPresenter extends AbstractSinglePresenter<MealPlanView>{

    @Inject
    @Named("common")
    IApi api;

    @Inject
    public MealPlanPresenter() {
    }

    public void getMealPlan() {
        networkView.callApi(() -> api.getMealPlan().map(mapResponseToObject()));
    }
}
