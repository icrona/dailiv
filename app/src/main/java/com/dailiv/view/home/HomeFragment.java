package com.dailiv.view.home;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.dailiv.App;
import com.dailiv.R;
import com.dailiv.internal.data.local.pojo.RecipeIndex;
import com.dailiv.internal.data.local.pojo.RecipeOfTheDay;
import com.dailiv.internal.injector.component.DaggerFragmentComponent;
import com.dailiv.internal.injector.module.FragmentModule;
import com.dailiv.util.common.Navigator;
import com.dailiv.view.base.AbstractFragment;
import com.dailiv.view.custom.RecyclerViewDecorator;
import com.dailiv.view.location.LocationActivity;
import com.dailiv.view.recipe.RecipeAdapter;
import com.dailiv.view.recipe.detail.RecipeDetailActivity;
import com.dailiv.view.shop.detail.IngredientDetailActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.dailiv.util.common.CollectionUtil.mapListToList;
import static com.dailiv.util.common.Preferences.getLocation;

/**
 * Created by aldo on 4/1/18.
 */

public class HomeFragment extends AbstractFragment implements HomeView{


    @Inject
    Navigator navigator;

    @Inject
    HomePresenter presenter;

    @BindView(R.id.tv_current_location)
    TextView tvLocation;

    @BindView(R.id.rv_recipe)
    RecyclerView rvRecipe;

    private RecipeAdapter recipeAdapter;

    private List<RecipeIndex> recipes = new ArrayList<>();

    @Override
    public void inject() {

        DaggerFragmentComponent.builder()
                .applicationComponent(App.getComponent())
                .fragmentModule(new FragmentModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void onAttach() {
        presenter.onAttach(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initComponents(Bundle savedInstanceState) {
        inject();
        onAttach();
        setLocation();
        presenter.getHome();
        presenter.getIngredient();
        setAdapter();
    }

    private void setAdapter() {

        recipeAdapter = new RecipeAdapter(new ArrayList<>(), null, this::navigateToRecipeDetail);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        rvRecipe.setLayoutManager(linearLayoutManager);

        rvRecipe.setAdapter(recipeAdapter);

        rvRecipe.addItemDecoration(new RecyclerViewDecorator());
    }

    private void navigateToRecipeDetail(String identifier) {

        navigator.openDetails(getActivity(), RecipeDetailActivity.class, identifier);
    }

    private void navigateToIngredientDetail(String identifier) {

        navigator.openDetails(getActivity(), IngredientDetailActivity.class, identifier);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        presenter.onDetach();
    }

    @Override
    public void onShowHome(RecipeOfTheDay recipeOfTheDay, List<RecipeIndex> recipeList) {

        recipes.addAll(recipeList);
        recipeAdapter.setRecipes(recipeList);
        recipeAdapter.notifyDataSetChanged();
    }

    @Override
    public void onShowProgressBar() {

    }

    @Override
    public void onHideProgressBar() {

    }

    public void setLocation() {
        tvLocation.setText(getLocation().getLocationName());
    }

    @OnClick(R.id.btn_change_location)
    public void changeLocation() {
        navigator.openActivity(getActivity(), LocationActivity.class);
    }

    @Override
    public void onResume() {
        setLocation();
        super.onResume();
    }
}
