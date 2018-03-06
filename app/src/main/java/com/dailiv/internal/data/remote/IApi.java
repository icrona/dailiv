package com.dailiv.internal.data.remote;

/**
 * Created by aldo on 3/1/18.
 */


import com.dailiv.internal.data.remote.request.authentication.LoginRequest;
import com.dailiv.internal.data.remote.request.authentication.RegisterRequest;
import com.dailiv.internal.data.remote.response.authentication.AuthenticationResponse;

import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

import static com.dailiv.internal.data.remote.IApiConstant.*;

public interface IApi {

    @POST(REGISTER)
    Observable<Response<AuthenticationResponse>> register (@Body RegisterRequest registerRequest);

    @POST(LOGIN)
    Observable<Response<AuthenticationResponse>> login (@Body LoginRequest loginRequest);
}
