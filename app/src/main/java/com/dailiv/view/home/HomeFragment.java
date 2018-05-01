package com.dailiv.view.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.annimon.stream.Stream;
import com.dailiv.App;
import com.dailiv.R;
import com.dailiv.internal.data.local.pojo.IngredientIndex;
import com.dailiv.internal.data.local.pojo.RecipeIndex;
import com.dailiv.internal.data.local.pojo.RecipeOfTheDay;
import com.dailiv.internal.injector.component.DaggerFragmentComponent;
import com.dailiv.internal.injector.module.FragmentModule;
import com.dailiv.util.common.Navigator;
import com.dailiv.view.base.AbstractFragment;
import com.dailiv.view.custom.RecyclerViewDecorator;
import com.dailiv.view.location.LocationActivity;
import com.dailiv.view.main.MainActivity;
import com.dailiv.view.recipe.RecipeAdapter;
import com.dailiv.view.recipe.RecipeFragment;
import com.dailiv.view.recipe.detail.RecipeDetailActivity;
import com.dailiv.view.shop.ShopAdapter;
import com.dailiv.view.shop.ShopFragment;
import com.dailiv.view.shop.detail.IngredientDetailActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.annimon.stream.Collectors.toList;
import static com.dailiv.util.IConstants.FragmentIndex.RECIPE;
import static com.dailiv.util.IConstants.FragmentIndex.SHOP;
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

    @BindView(R.id.rv_shop)
    RecyclerView rvShop;

    private RecipeAdapter recipeAdapter;

    private ShopAdapter shopAdapter;

    private List<IngredientIndex> ingredients = new ArrayList<>();

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

        shopAdapter = new ShopAdapter(
                new ArrayList<>(),
                this::addToCart,
                this::deleteCart,
                this::updateCart,
                this::navigateToIngredientDetail
        );

        final GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        rvShop.setLayoutManager(gridLayoutManager);

        rvShop.setAdapter(shopAdapter);

        rvShop.addItemDecoration(new RecyclerViewDecorator());

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

        recipeAdapter.setRecipes(recipeList);
        recipeAdapter.notifyDataSetChanged();
    }

    @Override
    public void onShowIngredients(List<IngredientIndex> ingredientIndices) {

        ingredients.addAll(ingredientIndices);

        shopAdapter.setIngredients(ingredientIndices);
        shopAdapter.notifyDataSetChanged();
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

    @OnClick(R.id.tv_looking_for_ingredients)
    public void goToIngredients() {
        switchToFragment(SHOP);
    }

    @OnClick(R.id.tv_recipes)
    public void goToRecipes() {
        switchToFragment(RECIPE);
    }

    private void switchToFragment(int index) {

        ((MainActivity)getActivity()).navigateTo(index);
    }

    @Override
    public void onResume() {
        setLocation();
        super.onResume();
    }

    public void onAddToCart(int cartId, int cartedAmount, int ingredientId) {

        List<IngredientIndex> list = Stream.of(ingredients)
                .map(i -> {
                    if(i.getId() == ingredientId){
                        i.setCartId(cartId);
                    }
                    return i;
                })
                .collect(toList());

        shopAdapter.updateIngredients(list);
        shopAdapter.notifyDataSetChanged();
    }

    //todo
    public void addToCart(int storeIngredientId) {

        presenter.addToCart(1, storeIngredientId);
        System.out.println("adding to cart with store ingredient id " + storeIngredientId);
    }


    public void deleteCart(int cartId) {

        presenter.deleteCart(cartId);

        System.out.println("delete cart with cart id " + cartId);
    }

    public void updateCart(int cartId, int quantity) {

        presenter.updateCart(cartId, quantity);

        System.out.println("update cart with cart id " + cartId + " and quantity " + quantity);

    }
}
