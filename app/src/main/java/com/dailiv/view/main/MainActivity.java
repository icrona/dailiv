package com.dailiv.view.main;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.dailiv.App;
import com.dailiv.R;
import com.dailiv.internal.injector.component.DaggerActivityComponent;
import com.dailiv.internal.injector.module.ActivityModule;
import com.dailiv.util.common.Common;
import com.dailiv.util.common.Navigator;
import com.dailiv.view.base.AbstractActivity;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import javax.inject.Inject;

import butterknife.BindArray;
import butterknife.BindString;
import butterknife.BindView;

/**
 * Created by aldo on 3/3/18.
 */

public class MainActivity extends AbstractActivity implements MainView{

    @Inject
    MainPresenter presenter;

    @Inject
    Common common;

    @Inject
    Navigator navigator;

    @BindView(R.id.toolbar_home)
    Toolbar toolbar;

    @BindArray(R.array.menu)
    String[] menuText;

    @BindView(R.id.bnv_main)
    BottomNavigationViewEx navigationMenu;

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initComponents(Bundle savedInstanceState) {

        inject();
        onAttach();
        setToolbar();
        setNavigation();
    }
    private void setToolbar() {
        toolbar.setPadding(0, getStatusBarHeight(), 0, 0);
        toolbar.setTitle(menuText[0]);
        toolbar.hideOverflowMenu();
        setSupportActionBar(toolbar);
    }
    private void setNavigation() {

        navigationMenu.enableAnimation(false);
        navigationMenu.enableShiftingMode(false);
        navigationMenu.enableItemShiftingMode(false);
        navigationMenu.setTextVisibility(false);

        navigationMenu.setOnNavigationItemSelectedListener(item -> {
          switch (item.getItemId()) {
              case R.id.nav_home:
                  toolbar.setTitle(menuText[0]);
                  return true;
              case R.id.nav_shop:
                  toolbar.setTitle(menuText[1]);
                  return true;
              case R.id.nav_recipe:
                  toolbar.setTitle(menuText[2]);
                  return true;
              case R.id.nav_notification:
                  toolbar.setTitle(menuText[3]);
                  return true;
              case R.id.nav_account:
                  toolbar.setTitle(menuText[4]);
                  return true;
          }
          return false;
        });

    }

    @Override
    public void inject() {
        DaggerActivityComponent.builder()
                .applicationComponent(App.getComponent())
                .activityModule(new ActivityModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void onAttach() {
        presenter.onAttach(this);
    }

    @Override
    public void onDetach() {
        presenter.onDetach();
    }

    @Override
    public void onShowProgressBar() {

    }

    @Override
    public void onHideProgressBar() {

    }

    @Override
    public void showResponse(Object response) {

    }

}
