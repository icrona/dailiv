package com.dailiv.view.profile.recipe;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.dailiv.App;
import com.dailiv.R;
import com.dailiv.internal.data.local.pojo.MealPlanning;
import com.dailiv.internal.data.local.pojo.ProfileRecipeList;
import com.dailiv.internal.data.local.pojo.RecipeIndex;
import com.dailiv.internal.data.remote.response.recipe.Recipe;
import com.dailiv.internal.injector.component.DaggerActivityComponent;
import com.dailiv.internal.injector.module.ActivityModule;
import com.dailiv.util.IConstants;
import com.dailiv.util.common.Navigator;
import com.dailiv.view.base.AbstractActivity;
import com.dailiv.view.custom.MealPlanningDialog;
import com.dailiv.view.custom.RecyclerViewDecorator;
import com.dailiv.view.recipe.RecipeAdapter;
import com.dailiv.view.recipe.detail.RecipeDetailActivity;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import rx.functions.Action1;

import static com.dailiv.util.common.CollectionUtil.mapListToList;

/**
 * Created by aldo on 6/1/18.
 */

public class RecipeListActivity extends AbstractActivity implements RecipeListView{

    @Inject
    RecipeListPresenter presenter;

    @Inject
    Navigator navigator;

    @BindView(R.id.rv_recipe)
    RecyclerView rvRecipe;

    @BindString(R.string.add_to_meal_plan)
    String sAddMealPlan;

    private ProfileRecipeList profileRecipeList;

    private RecipeAdapter recipeAdapter;

    private List<RecipeIndex> recipes = new ArrayList<>();

    private MealPlanningDialog mealPlanningDialog;

    @BindView(R.id.toolbar_recipe_list)
    Toolbar toolbar;

    @Override
    public void onDetach() {

        presenter.onDetach();
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
    protected int getContentView() {
        return R.layout.activity_recipe_list;
    }

    @Override
    protected void initComponents(Bundle savedInstanceState) {
        inject();
        onAttach();
        setAdapter();
        setMealPlanningDialog();
        setRecipeList();
    }

    @Override
    public void showRecipes(List<Recipe> response) {
        List<RecipeIndex> recipeIndices = mapListToList(response, RecipeIndex::new);

        recipes.addAll(recipeIndices);

        recipeAdapter.setRecipes(recipeIndices);
        recipeAdapter.notifyDataSetChanged();
    }

    private void setAdapter() {

        recipeAdapter = new RecipeAdapter(new ArrayList<>(), this::addToMealPlanning, this::navigateToDetail);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        rvRecipe.setLayoutManager(linearLayoutManager);

        rvRecipe.setAdapter(recipeAdapter);

        rvRecipe.addItemDecoration(new RecyclerViewDecorator());

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

    private void setMealPlanningDialog() {

        mealPlanningDialog = new MealPlanningDialog(this, getLayoutInflater()) {
            @Override
            public Action1<MealPlanning> submitAction() {
                return mealPlanning -> {
                    if(mealPlanning.isValid()){
                        presenter.addMealPlanning(mealPlanning);
                    }
                    else{
                        //todo
                    }
                };
            }

            @Override
            public String title() {
                return sAddMealPlan;
            }
        };
    }


    private void addToMealPlanning(int recipeId) {
        mealPlanningDialog.show(recipeId);
    }

    private void navigateToDetail(String identifier) {

        navigator.openDetails(this, RecipeDetailActivity.class, identifier);
    }


    private void setRecipeList() {

        profileRecipeList = Parcels.unwrap(getIntent().getParcelableExtra(IConstants.PROFILE_RECIPE_LIST));

        presenter.getRecipeList(profileRecipeList);
        setToolbar(profileRecipeList.getToolbarTitle());

    }

    private void setToolbar(String title) {
        toolbar.setPadding(0, getStatusBarHeight(), 0, 0);
        toolbar.setNavigationIcon(
                ContextCompat.getDrawable(this, R.drawable.ic_back));
        toolbar.hideOverflowMenu();
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
    }
}
