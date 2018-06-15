package com.dailiv.view.profile.other;

import com.dailiv.internal.data.remote.IApi;
import com.dailiv.internal.data.remote.request.profile.FollowRequest;
import com.dailiv.internal.data.remote.response.profile.ProfileResponse;
import com.dailiv.util.network.NetworkView;
import com.dailiv.view.base.IPresenter;

import javax.inject.Inject;
import javax.inject.Named;

import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Created by aldo on 6/3/18.
 */

public class OtherProfilePresenter implements IPresenter<OtherProfileView>{

    @Inject
    @Named("common")
    IApi api;

    @Inject
    public OtherProfilePresenter() {
    }

    private NetworkView<Boolean> followNetworkView;

    private NetworkView<ProfileResponse> profileNetworkView;

    private OtherProfileView view;

    @Override
    public void onAttach(OtherProfileView view) {

        this.view = view;

        followNetworkView = new NetworkView<>(
                getOnStart(),
                getOnComplete(),
                getOnShowError(),
                getOnFollow()
        );

        profileNetworkView = new NetworkView<>(
                getOnStart(),
                getOnComplete(),
                getOnShowError(),
                getOnProfile()
        );
    }

    @Override
    public void onDetach() {

        followNetworkView.safeUnsubscribe();
        profileNetworkView.safeUnsubscribe();
        this.view = null;
    }

    private Action0 getOnStart() {
        return () -> view.onShowProgressBar();
    }

    private Action0 getOnComplete() {
        return () -> view.onHideProgressBar();
    }

    private Action1<String> getOnShowError() {
        return view::onShowError;
    }

    private Action1<Boolean> getOnFollow() {

        return aBoolean -> {};
    }

    private Action1<ProfileResponse> getOnProfile() {

        return view::showResponse;
    }

    public void getProfile(String slug) {
        profileNetworkView.callApi(() -> api.getProfileBySlug(slug));
    }

    public void follow(int userId)  {
        FollowRequest followRequest = new FollowRequest();
        followRequest.userId = userId;

        followNetworkView.callApi(() -> api.followUser(followRequest));
    }

    public void unfollow(int userId)  {
        FollowRequest followRequest = new FollowRequest();
        followRequest.userId = userId;

        followNetworkView.callApi(() -> api.unfollowUser(followRequest));
    }

}
