package com.dailiv.view.location;

import com.dailiv.internal.data.remote.IApi;
import com.dailiv.view.base.AbstractSinglePresenter;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by aldo on 3/19/18.
 */

public class LocationPresenter extends AbstractSinglePresenter<LocationView>{

    @Inject
    @Named("common")
    IApi api;

    @Inject
    public LocationPresenter(){}
}
