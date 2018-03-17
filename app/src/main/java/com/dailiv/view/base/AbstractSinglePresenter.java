package com.dailiv.view.base;

import com.dailiv.util.network.NetworkView;

import retrofit2.Response;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by jurnal on 4/18/17.
 */

public abstract class AbstractSinglePresenter<T extends IView> implements IPresenter<T> {

    protected NetworkView<Object> networkView;
    protected T view;

    @Override
    public void onAttach(T view) {
        this.view = view;
        networkView = new NetworkView<>(
                getOnStart(),
                getOnComplete(),
                getOnShowError(),
                getResponse()
        );
    }

    @Override
    public void onDetach() {
        networkView.safeUnsubscribe();
        view = null;
    }

    protected Func1<Response<?>, Response<Object>> mapResponseToObject() {

        return response -> (Response)response;
    }


    public Action1<Object> getResponse() {
        return o -> view.showResponse(o);
    }

    protected Action0 getOnStart() {
        return () -> view.onShowProgressBar();
    }

    protected Action0 getOnComplete() {
        return () -> view.onHideProgressBar();
    }

    protected Action1<String> getOnShowError() {
        return s -> view.onShowError(s);
    }
}
