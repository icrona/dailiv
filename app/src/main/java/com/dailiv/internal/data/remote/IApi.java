package com.dailiv.internal.data.remote;

/**
 * Created by aldo on 3/1/18.
 */


import com.dailiv.internal.data.remote.request.authentication.FacebookAuthRequest;
import com.dailiv.internal.data.remote.request.authentication.GoogleAuthRequest;
import com.dailiv.internal.data.remote.request.authentication.LoginRequest;
import com.dailiv.internal.data.remote.request.authentication.RegisterRequest;
import com.dailiv.internal.data.remote.request.recipe.RecipeBaseRequest;
import com.dailiv.internal.data.remote.response.authentication.AuthenticationResponse;
import com.dailiv.internal.data.remote.response.home.HomeResponse;
import com.dailiv.internal.data.remote.response.home.SearchResponse;

import java.util.List;

import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

import static com.dailiv.internal.data.remote.IApiConstant.*;

public interface IApi {

    @POST(REGISTER)
    Observable<Response<AuthenticationResponse>> register (@Body RegisterRequest registerRequest);

    @POST(LOGIN)
    Observable<Response<AuthenticationResponse>> login (@Body LoginRequest loginRequest);

    @POST(FB_AUTH)
    Observable<Response<AuthenticationResponse>> fbAuth(@Body FacebookAuthRequest facebookAuthRequest);

    @POST(GOOGLE_AUTh)
    Observable<Response<AuthenticationResponse>> googleAuth(@Body GoogleAuthRequest googleAuthRequest);

    @GET(HOME)
    Observable<Response<HomeResponse>> home();

    @GET(SEARCH)
    Observable<Response<List<SearchResponse>>> search(@Query("search") String search);

    @POST(COOK)
    Observable<Response<Boolean>> cook(@Body RecipeBaseRequest recipeBaseRequest);

    @POST(UNCOOK)
    Observable<Response<Boolean>> uncook(@Body RecipeBaseRequest recipeBaseRequest);


}
