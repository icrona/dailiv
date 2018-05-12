package com.dailiv.view.profile;

import com.dailiv.internal.data.remote.IApi;
import com.dailiv.view.base.IPresenter;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by aldo on 4/1/18.
 */

public class ProfilePresenter implements IPresenter<ProfileView>{

    @Inject
    @Named("common")
    IApi api;

    @Inject
    public ProfilePresenter(){}

    private ProfileView view;

    @Override
    public void onAttach(ProfileView view) {
        this.view = view;
    }

    @Override
    public void onDetach() {
        this.view = null;
    }
}
