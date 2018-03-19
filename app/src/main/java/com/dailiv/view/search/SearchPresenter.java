package com.dailiv.view.search;

import com.dailiv.internal.data.remote.IApi;
import com.dailiv.view.base.AbstractSinglePresenter;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by aldo on 3/17/18.
 */

public class SearchPresenter extends AbstractSinglePresenter<SearchView>{

    @Inject
    @Named("common")
    IApi api;

    @Inject
    public SearchPresenter() {}


}
