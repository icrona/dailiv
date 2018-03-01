package com.dailiv.util.network;

import java.util.Arrays;
import java.util.List;

import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by aldo on 3/1/18.
 */

public class NetworkResponseMapper<T> implements Func1<Response<T>, Observable<T>> {

    private List<Integer> SUCCESS_LIST = Arrays.asList(200, 201, 204);

    @Override
    public Observable<T> call(Response<T> response) {

        try {

            if(SUCCESS_LIST.contains(response.code())) {
                return Observable.just(response.body());
            }

            //TODO

            return Observable.error(new HttpException(response));

        }
        catch (Exception e) {
            return Observable.error(e);
        }
    }
}
