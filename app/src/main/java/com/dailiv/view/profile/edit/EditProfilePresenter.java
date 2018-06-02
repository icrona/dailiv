package com.dailiv.view.profile.edit;

import com.dailiv.internal.data.remote.IApi;
import com.dailiv.internal.data.remote.request.profile.EdiHeadlineRequest;
import com.dailiv.util.network.NetworkView;
import com.dailiv.view.base.IPresenter;

import java.io.File;

import javax.inject.Inject;
import javax.inject.Named;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Created by aldo on 6/2/18.
 */

public class EditProfilePresenter implements IPresenter<EditProfileView>{

    @Inject
    @Named("common")
    IApi api;

    private NetworkView<Boolean> editHeadlineNetworkView;

    private NetworkView<Boolean> changeProfilePhotoNetworkView;

    @Inject
    public EditProfilePresenter() {
    }

    private EditProfileView view;

    @Override
    public void onAttach(EditProfileView view) {
        this.view = view;

        editHeadlineNetworkView = new NetworkView<>(
                getOnStart(),
                getOnComplete(),
                getOnShowError(),
                getOnEditHeadline()
        );

        changeProfilePhotoNetworkView = new NetworkView<>(
                getOnStart(),
                getOnComplete(),
                getOnShowError(),
                getOnChangeProfile()
        );
    }

    private Action1<Boolean> getOnEditHeadline() {

        return aBoolean -> view.onEditHeadline();
    }

    private Action1<Boolean> getOnChangeProfile() {

        return aBoolean -> view.onChangeProfilePhoto();
    }

    public void editHeadline(String headline) {

        EdiHeadlineRequest request = new EdiHeadlineRequest();

        request.headline = headline;

        editHeadlineNetworkView.callApi(() -> api.editHeadline(request));

    }

    public void changeProfilePhoto(File file) {

        RequestBody requestFile =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"),
                        file
                );

        MultipartBody.Part body =
                MultipartBody.Part.createFormData("profile_photo", file.getName(),
                        requestFile);

        changeProfilePhotoNetworkView.callApi(() -> api.changeProfilePhoto(body));

    }

    @Override
    public void onDetach() {

        editHeadlineNetworkView.safeUnsubscribe();
        changeProfilePhotoNetworkView.safeUnsubscribe();
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

}
