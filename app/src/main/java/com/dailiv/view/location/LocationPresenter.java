package com.dailiv.view.location;

import com.dailiv.internal.data.remote.IApi;
import com.dailiv.internal.data.remote.request.location.AddLocationRequest;
import com.dailiv.internal.data.remote.request.location.ChooseLocationRequest;
import com.dailiv.internal.data.remote.response.location.LocationResponse;
import com.dailiv.util.network.NetworkView;
import com.dailiv.view.base.IPresenter;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Created by aldo on 3/19/18.
 */

public class LocationPresenter implements IPresenter<LocationView> {

    @Inject
    @Named("common")
    IApi api;

    @Inject
    public LocationPresenter(){}

    private NetworkView<LocationResponse> addLocationNetworkView;

    private NetworkView<LocationResponse> chooseLocationNetworkView;

    private NetworkView<List<LocationResponse>> getLocationListNetworkView;

    private LocationView view;

    @Override
    public void onAttach(LocationView view) {
        this.view = view;
        addLocationNetworkView = new NetworkView<>(
                getOnStart(),
                getOnComplete(),
                getOnShowError(),
                getAddLocation()
        );

        chooseLocationNetworkView = new NetworkView<>(
                getOnStart(),
                getOnComplete(),
                getOnShowError(),
                getChooseLocation()
        );

        getLocationListNetworkView = new NetworkView<>(
                getOnStart(),
                getOnComplete(),
                getOnShowError(),
                getLocationList()
        );
    }

    @Override
    public void onDetach() {
        addLocationNetworkView.safeUnsubscribe();
        chooseLocationNetworkView.safeUnsubscribe();
        getLocationListNetworkView.safeUnsubscribe();
        view = null;
    }

    private Action1<LocationResponse> getAddLocation() {
        return locationResponse -> chooseLocation(locationResponse.id);
    }

    private Action1<LocationResponse> getChooseLocation() {
        return location -> view.onLocationChosen(location);
    }

    private Action1<List<LocationResponse>> getLocationList() {
        return locationList ->
            view.onGetLocation(locationList.isEmpty() ?
                    null :
                    locationList.get(locationList.size() - 1));
    }

    private Action0 getOnStart() {
        return () -> view.onShowProgressBar();
    }

    private Action0 getOnComplete() {
        return () -> view.onHideProgressBar();
    }

    private Action1<String> getOnShowError() {
        return s -> view.onShowError(s);
    }

    public void addLocation(AddLocationRequest locationRequest) {

        addLocationNetworkView.callApi(() -> api.addLocation(locationRequest));
    }

    public void chooseLocation(int locationId) {

        chooseLocationNetworkView.callApi(() -> api.chooseLocation(new ChooseLocationRequest(locationId)));
    }

    public void getLocation() {

        getLocationListNetworkView.callApi(() -> api.getLocationList());
    }


}
