package com.dailiv.view.notification;

import com.dailiv.internal.data.remote.IApi;
import com.dailiv.view.base.IPresenter;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by aldo on 4/1/18.
 */

public class NotificationPresenter implements IPresenter<NotificationView>{

    @Inject
    @Named("common")
    IApi api;

    @Inject
    public NotificationPresenter() {}

    private NotificationView view;

    @Override
    public void onAttach(NotificationView view) {
        this.view = view;
    }

    @Override
    public void onDetach() {
        this.view = null;
    }
}
