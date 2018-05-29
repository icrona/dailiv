package com.dailiv.view.profile.mealplan;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.dailiv.App;
import com.dailiv.R;
import com.dailiv.internal.data.local.pojo.MealPlan;
import com.dailiv.internal.data.remote.response.mealplan.MealPlanResponse;
import com.dailiv.internal.injector.component.DaggerActivityComponent;
import com.dailiv.internal.injector.module.ActivityModule;
import com.dailiv.util.common.Navigator;
import com.dailiv.view.base.AbstractActivity;
import com.dailiv.view.custom.RecyclerViewDecorator;
import com.dailiv.view.recipe.detail.RecipeDetailActivity;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;

import static com.dailiv.util.common.CollectionUtil.mapToList;

/**
 * Created by aldo on 5/27/18.
 */

public class MealPlanActivity extends AbstractActivity implements MealPlanView{

    @Inject
    MealPlanPresenter presenter;

    @Inject
    Navigator navigator;

    @BindView(R.id.toolbar_meal_plan)
    Toolbar toolbar;

    @BindView(R.id.rv_meal_plan)
    RecyclerView rvMealPlan;

    private MealPlanAdapter mealPlanAdapter;

    @Override
    public void onDetach() {
        presenter.onDetach();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_meal_plan;
    }

    @Override
    protected void initComponents(Bundle savedInstanceState) {
        inject();
        onAttach();
        setToolbar();
        setAdapter();
        presenter.getMealPlan();
    }

    private void setToolbar() {
        toolbar.setPadding(0, getStatusBarHeight(), 0, 0);
        toolbar.setNavigationIcon(
                ContextCompat.getDrawable(this, R.drawable.ic_back));
        toolbar.hideOverflowMenu();
        setSupportActionBar(toolbar);
    }

    private void setAdapter() {
        mealPlanAdapter = new MealPlanAdapter(this::navigateToRecipeDetail);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        rvMealPlan.setLayoutManager(linearLayoutManager);

        rvMealPlan.setAdapter(mealPlanAdapter);

        rvMealPlan.addItemDecoration(new RecyclerViewDecorator());

    }

    private void navigateToRecipeDetail(String identifier) {

        navigator.openDetails(this, RecipeDetailActivity.class, identifier);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                finish();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onShowProgressBar() {

    }

    @Override
    public void inject() {
        DaggerActivityComponent.builder()
                .applicationComponent(App.getComponent())
                .activityModule(new ActivityModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void onAttach() {
        presenter.onAttach(this);
    }

    @Override
    public void onHideProgressBar() {

    }

    @Override
    public void showResponse(Map<String, MealPlanResponse> response) {

        //todo remove response sample

        MealPlanResponse test = response.get("tuesday");

        MealPlanResponse.MealPlanRecipeResponse recipe1 = new MealPlanResponse.MealPlanRecipeResponse();

        recipe1.id = 28;
        recipe1.userId = 34;
        recipe1.recipeId = 14;
        recipe1.date = "2018-05-29";
        recipe1.category = "Lunch";

        MealPlanResponse.MealPlanRecipeInfoResponse recipeInfo1 = new MealPlanResponse.MealPlanRecipeInfoResponse();
        recipeInfo1.id = 14;
        recipeInfo1.name = "Prof. Madge Mitchell Jr.";
        recipeInfo1.slug = "1522685237-Prof.-Madge-Mitchell-Jr.";

        recipe1.recipe = recipeInfo1;

        MealPlanResponse.MealPlanRecipeResponse recipe2 = new MealPlanResponse.MealPlanRecipeResponse();

        recipe2.id = 31;
        recipe2.userId = 34;
        recipe2.recipeId = 13;
        recipe2.date = "2018-05-29";
        recipe2.category = "Lunch";

        MealPlanResponse.MealPlanRecipeInfoResponse recipeInfo2 = new MealPlanResponse.MealPlanRecipeInfoResponse();
        recipeInfo2.id = 13;
        recipeInfo2.name = "Susie Mitchell";
        recipeInfo2.slug = "1522685237-Susie-Mitchell";

        recipe2.recipe = recipeInfo2;

        test.lunch = Arrays.asList(recipe1, recipe2);

        response.remove("tuesday");

        response.put("tuesday", test);

        //todo remove till here later

        List<MealPlan> mealPlans = mapToList(response, MealPlan::new);

        mealPlanAdapter.setMealPlans(mealPlans);

        mealPlanAdapter.notifyDataSetChanged();

    }

    public String getCurrentWeek() {

        Calendar cal = GregorianCalendar.getInstance(Locale.FRANCE);

        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        Date startWeek = cal.getTime();

        cal.add(Calendar.DATE, 6);

        Date endWeek = cal.getTime();

        return "Date: " + dateFormat.format(startWeek) + " - " + dateFormat.format(endWeek);
    }

}
