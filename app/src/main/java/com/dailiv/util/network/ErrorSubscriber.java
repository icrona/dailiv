package com.dailiv.util.network;

import android.content.Intent;

import com.dailiv.view.login.LoginActivity;

import java.io.IOException;

import lombok.AllArgsConstructor;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;

import static com.dailiv.App.getContext;

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

        String errorMessage = e.getMessage();

        //TODO check by HttpException, NetworkException etc

        if(e instanceof HttpException) {

            HttpException exception = (HttpException) e;

            if(exception.code() == 401) {

                redirectToLogin();

            }

            try{
                errorMessage = exception.response().errorBody().string();
            }
            catch (IOException ex) {

                errorMessage = exception.getMessage();
            }

        }

        onError.call(errorMessage);

        onComplete.call();

    }

    public void redirectToLogin() {
        final Intent intent = new Intent(getContext(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        getContext().startActivity(intent);
    }

    @Override
    public void onNext(T t) {

        onResponse.call(t);
    }
}
