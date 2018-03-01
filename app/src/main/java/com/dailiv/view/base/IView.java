package com.dailiv.view.base;

/**
 * Created by aldo on 3/1/18.
 */

public interface IView<T> extends IDetachView {

    void inject();

    void onAttach();

    void onShowProgressBar();

    void onHideProgressBar();

    void onShowError(final String message);

    void showResponse(T response);
}
