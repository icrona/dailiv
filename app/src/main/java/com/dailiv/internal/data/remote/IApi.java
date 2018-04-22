package com.dailiv.internal.data.remote;

/**
 * Created by aldo on 3/1/18.
 */


import com.dailiv.internal.data.remote.request.authentication.FacebookAuthRequest;
import com.dailiv.internal.data.remote.request.authentication.GoogleAuthRequest;
import com.dailiv.internal.data.remote.request.authentication.LoginRequest;
import com.dailiv.internal.data.remote.request.authentication.RegisterRequest;
import com.dailiv.internal.data.remote.request.cart.AddToCartRequest;
import com.dailiv.internal.data.remote.request.cart.DeleteCartRequest;
import com.dailiv.internal.data.remote.request.cart.UpdateCartRequest;
import com.dailiv.internal.data.remote.request.location.AddLocationRequest;
import com.dailiv.internal.data.remote.request.location.ChooseLocationRequest;
import com.dailiv.internal.data.remote.request.recipe.RecipeBaseRequest;
import com.dailiv.internal.data.remote.response.Category;
import com.dailiv.internal.data.remote.response.authentication.AuthenticationResponse;
import com.dailiv.internal.data.remote.response.cart.CartResponse;
import com.dailiv.internal.data.remote.response.home.HomeResponse;
import com.dailiv.internal.data.remote.response.home.SearchResponse;
import com.dailiv.internal.data.remote.response.ingredient.IngredientsResponse;
import com.dailiv.internal.data.remote.response.location.LocationResponse;
import com.dailiv.internal.data.remote.response.recipe.RecipesResponse;

import java.util.List;

import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

import static com.dailiv.internal.data.remote.IApiConstant.ADD_TO_CART;
import static com.dailiv.internal.data.remote.IApiConstant.CART;
import static com.dailiv.internal.data.remote.IApiConstant.CHOOSE_LOCATION;
import static com.dailiv.internal.data.remote.IApiConstant.COOK;
import static com.dailiv.internal.data.remote.IApiConstant.DELETE_CART;
import static com.dailiv.internal.data.remote.IApiConstant.FB_AUTH;
import static com.dailiv.internal.data.remote.IApiConstant.GET_CART;
import static com.dailiv.internal.data.remote.IApiConstant.GOOGLE_AUTH;
import static com.dailiv.internal.data.remote.IApiConstant.HOME;
import static com.dailiv.internal.data.remote.IApiConstant.INGREDIENTS;
import static com.dailiv.internal.data.remote.IApiConstant.INGREDIENT_CATEGORIES;
import static com.dailiv.internal.data.remote.IApiConstant.LOCATION;
import static com.dailiv.internal.data.remote.IApiConstant.LOGIN;
import static com.dailiv.internal.data.remote.IApiConstant.RECIPES;
import static com.dailiv.internal.data.remote.IApiConstant.RECIPE_CATEGORY;
import static com.dailiv.internal.data.remote.IApiConstant.REGISTER;
import static com.dailiv.internal.data.remote.IApiConstant.SEARCH;
import static com.dailiv.internal.data.remote.IApiConstant.UNCOOK;
import static com.dailiv.internal.data.remote.IApiConstant.UPDATE_CART;

public interface IApi {

    @POST(REGISTER)
    Observable<Response<AuthenticationResponse>> register (@Body RegisterRequest registerRequest);

    @POST(LOGIN)
    Observable<Response<AuthenticationResponse>> login (@Body LoginRequest loginRequest);

    @POST(FB_AUTH)
    Observable<Response<AuthenticationResponse>> fbAuth(@Body FacebookAuthRequest facebookAuthRequest);

    @POST(GOOGLE_AUTH)
    Observable<Response<AuthenticationResponse>> googleAuth(@Body GoogleAuthRequest googleAuthRequest);

    @GET(HOME)
    Observable<Response<HomeResponse>> home();

    @POST(LOCATION)
    Observable<Response<LocationResponse>> addLocation(@Body AddLocationRequest addLocationRequest);

    @POST(CHOOSE_LOCATION)
    Observable<Response<LocationResponse>> chooseLocation(@Body ChooseLocationRequest chooseLocationRequest);

    @GET(LOCATION)
    Observable<Response<List<LocationResponse>>> getLocationList();

    @GET(SEARCH)
    Observable<Response<SearchResponse>> search(@Query("search") String search, @Query("store_id") int storeId);

    @GET(INGREDIENTS)
    Observable<Response<IngredientsResponse>> ingredients(
            @Query("store_id") int storeId,
            @Query("limit") int limit,
            @Query("category[]") List<String> category,
            @Query("from_price") Integer fromPrice,
            @Query("to_price") Integer toPrice,
            @Query("page") Integer page);

    @GET(INGREDIENT_CATEGORIES)
    Observable<Response<List<Category>>> ingredientCategory();

    @POST(COOK)
    Observable<Response<Boolean>> cook(@Body RecipeBaseRequest recipeBaseRequest);

    @POST(UNCOOK)
    Observable<Response<Boolean>> uncook(@Body RecipeBaseRequest recipeBaseRequest);

    @GET(GET_CART)
    Observable<Response<List<CartResponse>>> getCart(@Path("location_id") int locationId);

    @POST(UPDATE_CART)
    Observable<Response<Boolean>> updateCart(@Body UpdateCartRequest updateCartRequest);

    @POST(DELETE_CART)
    Observable<Response<Boolean>> deleteCart(@Body DeleteCartRequest deleteCartRequest);

    @POST(ADD_TO_CART)
    Observable<Response<CartResponse>> addToCart(@Body AddToCartRequest addToCartRequest);

    @GET(RECIPES)
    Observable<Response<RecipesResponse>> recipes(
            @Query("limit") int limit,
            @Query("category[]") List<String> category,
            @Query("from_duration") Integer fromDuration,
            @Query("to_duration") Integer toDuration,
            @Query("sort_by") String sortBy,
            @Query("difficulty") String difficulty,
            @Query("page") Integer page);

    @GET(RECIPE_CATEGORY)
    Observable<Response<List<Category>>> recipeCategory();

}
