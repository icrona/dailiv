package com.dailiv.view.base;

/**
 * Created by aldo on 3/3/18.
 */

public interface IBaseView extends IDetachView{

    void inject();

    void onAttach();

    void onShowProgressBar();

    void onHideProgressBar();

    void onShowError(final String message);
}
