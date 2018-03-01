package com.dailiv.view.base;

/**
 * Created by aldo on 3/1/18.
 */

public interface IPresenter<T extends IView> {

    void onAttach(T view);

    void onDetach();
}
