package com.dailiv.internal.data.remote;

/**
 * Created by aldo on 3/1/18.
 */


import com.dailiv.internal.data.remote.request.authentication.FacebookAuthRequest;
import com.dailiv.internal.data.remote.request.authentication.GoogleAuthRequest;
import com.dailiv.internal.data.remote.request.authentication.LoginRequest;
import com.dailiv.internal.data.remote.request.authentication.RegisterRequest;
import com.dailiv.internal.data.remote.request.cart.AddToCartRequest;
import com.dailiv.internal.data.remote.request.cart.CheckoutRequest;
import com.dailiv.internal.data.remote.request.cart.DeleteCartRequest;
import com.dailiv.internal.data.remote.request.cart.UpdateCartRequest;
import com.dailiv.internal.data.remote.request.location.AddLocationRequest;
import com.dailiv.internal.data.remote.request.location.ChooseLocationRequest;
import com.dailiv.internal.data.remote.request.profile.EdiHeadlineRequest;
import com.dailiv.internal.data.remote.request.profile.FollowRequest;
import com.dailiv.internal.data.remote.request.recipe.MealPlanningRequest;
import com.dailiv.internal.data.remote.request.recipe.RecipeBaseRequest;
import com.dailiv.internal.data.remote.request.recipe.ThoughtRequest;
import com.dailiv.internal.data.remote.request.review.SubmitReviewRequest;
import com.dailiv.internal.data.remote.response.Category;
import com.dailiv.internal.data.remote.response.authentication.AuthenticationResponse;
import com.dailiv.internal.data.remote.response.cart.CartResponse;
import com.dailiv.internal.data.remote.response.checkout.CouponResponse;
import com.dailiv.internal.data.remote.response.history.OrderHistoryResponse;
import com.dailiv.internal.data.remote.response.home.HomeResponse;
import com.dailiv.internal.data.remote.response.home.SearchResponse;
import com.dailiv.internal.data.remote.response.ingredient.IngredientDetailResponse;
import com.dailiv.internal.data.remote.response.ingredient.IngredientsResponse;
import com.dailiv.internal.data.remote.response.location.LocationResponse;
import com.dailiv.internal.data.remote.response.mealplan.MealPlanResponse;
import com.dailiv.internal.data.remote.response.profile.ProfileResponse;
import com.dailiv.internal.data.remote.response.recipe.AddThoughtResponse;
import com.dailiv.internal.data.remote.response.recipe.Recipe;
import com.dailiv.internal.data.remote.response.recipe.RecipeDetailResponse;
import com.dailiv.internal.data.remote.response.recipe.RecipesResponse;
import com.dailiv.internal.data.remote.response.review.ReviewNeededResponse;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

import static com.dailiv.internal.data.remote.IApiConstant.ADD_TO_CART;
import static com.dailiv.internal.data.remote.IApiConstant.CHANGE_PROFILE_PHOTO;
import static com.dailiv.internal.data.remote.IApiConstant.CHECKOUT;
import static com.dailiv.internal.data.remote.IApiConstant.CHOOSE_LOCATION;
import static com.dailiv.internal.data.remote.IApiConstant.COMMENT;
import static com.dailiv.internal.data.remote.IApiConstant.COOK;
import static com.dailiv.internal.data.remote.IApiConstant.DELETE_CART;
import static com.dailiv.internal.data.remote.IApiConstant.DELIVERY_FEE;
import static com.dailiv.internal.data.remote.IApiConstant.DISCOUNT_COUPON;
import static com.dailiv.internal.data.remote.IApiConstant.EDIT_HEADLINE;
import static com.dailiv.internal.data.remote.IApiConstant.FB_AUTH;
import static com.dailiv.internal.data.remote.IApiConstant.FOLLOW;
import static com.dailiv.internal.data.remote.IApiConstant.GET_CART;
import static com.dailiv.internal.data.remote.IApiConstant.GOOGLE_AUTH;
import static com.dailiv.internal.data.remote.IApiConstant.HOME;
import static com.dailiv.internal.data.remote.IApiConstant.INGREDIENTS;
import static com.dailiv.internal.data.remote.IApiConstant.INGREDIENT_CATEGORIES;
import static com.dailiv.internal.data.remote.IApiConstant.INGREDIENT_DETAIL;
import static com.dailiv.internal.data.remote.IApiConstant.LIKE;
import static com.dailiv.internal.data.remote.IApiConstant.LOCATION;
import static com.dailiv.internal.data.remote.IApiConstant.LOGIN;
import static com.dailiv.internal.data.remote.IApiConstant.MEAL_PLAN;
import static com.dailiv.internal.data.remote.IApiConstant.MEAL_PLANNING;
import static com.dailiv.internal.data.remote.IApiConstant.ORDER_HISTORY;
import static com.dailiv.internal.data.remote.IApiConstant.PROFILE_BY_SLUG;
import static com.dailiv.internal.data.remote.IApiConstant.RECIPES;
import static com.dailiv.internal.data.remote.IApiConstant.RECIPE_BY_PROFILE_ID;
import static com.dailiv.internal.data.remote.IApiConstant.RECIPE_CATEGORY;
import static com.dailiv.internal.data.remote.IApiConstant.RECIPE_DETAIL;
import static com.dailiv.internal.data.remote.IApiConstant.REGISTER;
import static com.dailiv.internal.data.remote.IApiConstant.REVIEW_NEEDED;
import static com.dailiv.internal.data.remote.IApiConstant.SEARCH;
import static com.dailiv.internal.data.remote.IApiConstant.SUBMIT_REVIEW;
import static com.dailiv.internal.data.remote.IApiConstant.UNCOOK;
import static com.dailiv.internal.data.remote.IApiConstant.UNFOLLOW;
import static com.dailiv.internal.data.remote.IApiConstant.UNLIKE;
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

    @POST(LIKE)
    Observable<Response<Boolean>> like(@Body RecipeBaseRequest recipeBaseRequest);

    @POST(UNLIKE)
    Observable<Response<Boolean>> unlike(@Body RecipeBaseRequest recipeBaseRequest);

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

    @POST(MEAL_PLANNING)
    Observable<Response<Boolean>> mealPlanning(@Body MealPlanningRequest mealPlanningRequest);

    @GET(RECIPE_DETAIL)
    Observable<Response<RecipeDetailResponse>> getRecipeDetail(@Path("identifier") String identifier);

    @GET(INGREDIENT_DETAIL)
    Observable<Response<IngredientDetailResponse>> getIngredientDetail(@Path("identifier") String identifier, @Path("store_id") int storeId);

    @GET(DELIVERY_FEE)
    Observable<Response<Integer>> getDeliveryFee(@Path("store_id") int storeId, @Path("location_id") int locationId);

    @POST(CHECKOUT)
    Observable<Response<Boolean>> checkout(@Body CheckoutRequest checkoutRequest);

    @POST(COMMENT)
    Observable<Response<AddThoughtResponse>> comment(@Body ThoughtRequest thoughtRequest);

    @GET(DISCOUNT_COUPON)
    Observable<Response<CouponResponse>> discountCoupon(@Path("code") String code);

    @GET(ORDER_HISTORY)
    Observable<Response<List<OrderHistoryResponse>>> orderHistory();

    @GET(REVIEW_NEEDED)
    Observable<Response<ReviewNeededResponse>> reviewNeeded();

    @POST(SUBMIT_REVIEW)
    Observable<Response<Boolean>> submitReview(@Body SubmitReviewRequest submitReviewRequest);

    @GET(MEAL_PLAN)
    Observable<Response<Map<String, MealPlanResponse>>> getMealPlan();

    @GET(PROFILE_BY_SLUG)
    Observable<Response<ProfileResponse>> getProfileBySlug(@Path("slug") String slug);

    @GET(RECIPE_BY_PROFILE_ID)
    Observable<Response<List<Recipe>>> getRecipeByProfileId(@Path("id") int id, @Path("type") String type);

    @Multipart
    @POST(CHANGE_PROFILE_PHOTO)
    Observable<Response<Boolean>> changeProfilePhoto(@Part() MultipartBody.Part file);

    @POST(EDIT_HEADLINE)
    Observable<Response<Boolean>> editHeadline(@Body EdiHeadlineRequest request);

    @POST(FOLLOW)
    Observable<Response<Boolean>> followUser(@Body FollowRequest request);

    @POST(UNFOLLOW)
    Observable<Response<Boolean>> unfollowUser(@Body FollowRequest request);

}
