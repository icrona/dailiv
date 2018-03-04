package com.dailiv.view.base;

/**
 * Created by aldo on 3/1/18.
 */

public interface IPresenter<T extends IBaseView> {

    void onAttach(T view);

    void onDetach();
}
