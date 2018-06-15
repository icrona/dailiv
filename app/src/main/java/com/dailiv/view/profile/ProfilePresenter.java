package com.dailiv.view.profile;

import com.dailiv.internal.data.remote.IApi;
import com.dailiv.view.base.AbstractSinglePresenter;

import javax.inject.Inject;
import javax.inject.Named;

import static com.dailiv.util.common.Preferences.getAccountSlug;

/**
 * Created by aldo on 4/1/18.
 */

public class ProfilePresenter extends AbstractSinglePresenter<ProfileView> {

    @Inject
    @Named("common")
    IApi api;

    @Inject
    public ProfilePresenter(){}

    public void getProfile() {
        networkView.callApi(() -> api.getProfileBySlug(getAccountSlug()).map(mapResponseToObject()));
    }

}
