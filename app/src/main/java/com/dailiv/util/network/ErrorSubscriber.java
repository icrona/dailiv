package com.dailiv.util.network;

import lombok.AllArgsConstructor;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Created by aldo on 3/1/18.
 */

@AllArgsConstructor
public class ErrorSubscriber<T> extends Subscriber<T> {


    private Action0 onComplete;

    private Action1<String> onError;

    private Action1<T> onResponse;

    @Override
    public void onCompleted() {

        onComplete.call();

    }

    @Override
    public void onError(Throwable e) {

        String errorMessage = "";

        //TODO check by HttpException, NetworkException etc

        onError.call(errorMessage);

        onComplete.call();

    }

    @Override
    public void onNext(T t) {

        onResponse.call(t);
    }
}
