package com.dailiv.view.profile.mealplan;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

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

import org.w3c.dom.Text;

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

    @BindView(R.id.tv_meal_plan_date)
    TextView tvDate;

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
        tvDate.setText(getCurrentWeek());
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
