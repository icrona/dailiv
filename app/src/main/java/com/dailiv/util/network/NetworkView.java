package com.dailiv.util.network;

import lombok.AllArgsConstructor;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static com.dailiv.util.network.NetworkHelper.checkConnection;

/**
 * Created by aldo on 3/1/18.
 */

@AllArgsConstructor
public class NetworkView<T> {

    private Action0 onStart;

    private Action0 onComplete;

    private Action1<String> onError;

    private Action1<T> onResponse;

    private Subscription subscription;

    public void callApi(final Func0<Observable<Response<T>>> result) {

        onStart.call();

        safeUnsubscribe();

        subscription = checkConnection()
                .flatMapObservable(aVoid -> result.call())
                .flatMap(mapResponse())
                .observeOn(Schedulers.io())
                .subscribe(getSubscriber());

    }

    public void safeUnsubscribe() {

        if (null != subscription && subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    private Subscriber<T> getSubscriber() {

        return new ErrorSubscriber<>(onComplete, onError, onResponse);
    }

    private Func1<Response<T>, Observable<T>> mapResponse() {
        return new NetworkResponseMapper<T>();
    }

}
