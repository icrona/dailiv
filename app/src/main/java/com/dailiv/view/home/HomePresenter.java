package com.dailiv.view.home;

import com.annimon.stream.Stream;
import com.dailiv.internal.data.local.pojo.IngredientIndex;
import com.dailiv.internal.data.local.pojo.RecipeIndex;
import com.dailiv.internal.data.local.pojo.RecipeOfTheDay;
import com.dailiv.internal.data.remote.IApi;
import com.dailiv.internal.data.remote.request.cart.AddToCartRequest;
import com.dailiv.internal.data.remote.request.cart.DeleteCartRequest;
import com.dailiv.internal.data.remote.request.cart.UpdateCartRequest;
import com.dailiv.internal.data.remote.response.cart.CartResponse;
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

    private NetworkView<CartResponse> addToCartNetworkView;

    private NetworkView<Boolean> updateCartNetworkView;

    private NetworkView<Boolean> deleteCartNetworkView;

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

        addToCartNetworkView = new NetworkView<>(
                getOnStart(),
                getOnComplete(),
                getOnShowError(),
                getAddToCartResponse()
        );

        updateCartNetworkView = new NetworkView<>(
                getOnStart(),
                getOnComplete(),
                getOnShowError(),
                getOnCartResponse()
        );

        deleteCartNetworkView = new NetworkView<>(
                getOnStart(),
                getOnComplete(),
                getOnShowError(),
                getOnCartResponse()
        );

    }

    @Override
    public void onDetach() {
        homeNetworkView.safeUnsubscribe();
        ingredientsNetworkView.safeUnsubscribe();
        addToCartNetworkView.safeUnsubscribe();
        updateCartNetworkView.safeUnsubscribe();
        deleteCartNetworkView.safeUnsubscribe();
        this.view = null;

    }

    public void getHome() {

        homeNetworkView.callApi(() -> api.home());
    }

    public void getIngredient() {

        ingredientsNetworkView.callApi(() -> api.ingredients(
                getLocation().getStoreId(),
                2,
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

            view.onShowHome(recipeOfTheDay, recipeIndices);
        };
    }

    private Action1<IngredientsResponse> getOnIngredientResponse() {
        return ingredientsResponse -> {

            List<IngredientIndex> ingredientIndices = mapListToList(ingredientsResponse.data, IngredientIndex::new);

            view.onShowIngredients(ingredientIndices);
        };
    }

    private Action1<Boolean> getOnCartResponse() {
        return System.out::println;
    }

    private Action1<CartResponse> getAddToCartResponse() {
        return cartResponse -> view.onAddToCart(cartResponse.id, cartResponse.amount, cartResponse.ingredient.id);
    }

    public void addToCart(int ingredientAmount, int storeIngredientId) {

        AddToCartRequest addToCartRequest = new AddToCartRequest();

        addToCartRequest.ingredientAmount = ingredientAmount;
        addToCartRequest.storeIngredientId = storeIngredientId;

        addToCartNetworkView.callApi(() -> api.addToCart(addToCartRequest));
    }

    public void deleteCart(int cartId) {

        DeleteCartRequest deleteCartRequest = new DeleteCartRequest();

        deleteCartRequest.cartId = cartId;

        deleteCartNetworkView.callApi(() -> api.deleteCart(deleteCartRequest));
    }

    public void updateCart(int cartId, int quantity) {

        UpdateCartRequest updateCartRequest = new UpdateCartRequest();

        updateCartRequest.cartId = cartId;
        updateCartRequest.amount = quantity;

        updateCartNetworkView.callApi(() -> api.updateCart(updateCartRequest));

    }

}
