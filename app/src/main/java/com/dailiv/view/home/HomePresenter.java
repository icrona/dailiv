package com.dailiv.view.home;

import com.annimon.stream.Stream;
import com.dailiv.internal.data.local.pojo.IngredientIndex;
import com.dailiv.internal.data.local.pojo.RecipeIndex;
import com.dailiv.internal.data.local.pojo.RecipeOfTheDay;
import com.dailiv.internal.data.remote.IApi;
import com.dailiv.internal.data.remote.response.home.HomeResponse;
import com.dailiv.internal.data.remote.response.ingredient.IngredientsResponse;
import com.dailiv.util.network.NetworkView;
import com.dailiv.view.base.IPresenter;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.inject.Inject;
import javax.inject.Named;

import rx.functions.Action0;
import rx.functions.Action1;

import static com.annimon.stream.Collectors.toList;
import static com.dailiv.util.common.CollectionUtil.mapListToList;
import static com.dailiv.util.common.Preferences.getLocation;

/**
 * Created by aldo on 4/1/18.
 */

public class HomePresenter implements IPresenter<HomeView> {

    @Inject
    @Named("common")
    IApi api;

    @Inject
    public HomePresenter() {}

    private HomeView view;

    private NetworkView<HomeResponse> homeNetworkView;

    private NetworkView<IngredientsResponse> ingredientsNetworkView;

    @Override
    public void onAttach(HomeView view) {
        this.view = view;
        homeNetworkView = new NetworkView<>(
                getOnStart(),
                getOnComplete(),
                getOnShowError(),
                getOnHomeResponse()
        );
        ingredientsNetworkView = new NetworkView<>(
                getOnStart(),
                getOnComplete(),
                getOnShowError(),
                getOnIngredientResponse()
        );
    }

    @Override
    public void onDetach() {
        homeNetworkView.safeUnsubscribe();
        ingredientsNetworkView.safeUnsubscribe();
        this.view = null;

    }

    public void getHome() {

        homeNetworkView.callApi(() -> api.home());
    }

    public void getIngredient() {

        ingredientsNetworkView.callApi(() -> api.ingredients(
                getLocation().getStoreId(),
                3,
                null,
                null,
                null,
                null
        ));
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

    private Action1<HomeResponse> getOnHomeResponse() {

        String[] categories = new String[] {"Breakfast", "Lunch", "Dinner"};

        AtomicInteger index = new AtomicInteger();

        return homeResponse -> {

            List<RecipeIndex> recipeIndices = Stream.of(homeResponse.popularRecipes)
                    .map(l -> l.get(0))
                    .map(recipe -> new RecipeIndex(recipe, categories[index.getAndIncrement() ]))
                    .collect(toList());

            RecipeOfTheDay recipeOfTheDay = new RecipeOfTheDay(homeResponse.recipeOfTheDay);

            System.out.println(homeResponse);

        };
    }

    private Action1<IngredientsResponse> getOnIngredientResponse() {
        return ingredientsResponse -> {

            List<IngredientIndex> ingredientIndices = mapListToList(ingredientsResponse.data, IngredientIndex::new);

            System.out.println(ingredientsResponse);
        };
    }
}
