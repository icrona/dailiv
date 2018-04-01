package com.dailiv.view.account;

import com.dailiv.internal.data.remote.IApi;
import com.dailiv.view.base.IPresenter;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by aldo on 4/1/18.
 */

public class AccountPresenter implements IPresenter<AccountView>{

    @Inject
    @Named("common")
    IApi api;

    @Inject
    public AccountPresenter(){}

    private AccountView view;

    @Override
    public void onAttach(AccountView view) {
        this.view = view;
    }

    @Override
    public void onDetach() {
        this.view = null;
    }
}
