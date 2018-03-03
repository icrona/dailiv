package com.dailiv.view.main;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;

import com.dailiv.App;
import com.dailiv.R;
import com.dailiv.internal.injector.component.ActivityComponent;
import com.dailiv.internal.injector.component.DaggerActivityComponent;
import com.dailiv.internal.injector.module.ActivityModule;
import com.dailiv.util.common.Common;
import com.dailiv.util.common.Navigator;
import com.dailiv.view.base.AbstractActivity;

import javax.inject.Inject;

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

    private ActivityComponent component;

    private ActionBar toolbar;

    @BindView(R.id.bnv_main)
    BottomNavigationView navigationMenu;

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
        toolbar = getSupportActionBar();
    }

    private void setNavigation() {

        navigationMenu.setOnNavigationItemSelectedListener(item -> {
          switch (item.getItemId()) {
              case R.id.nav_1:
                  toolbar.setTitle("nav 1");
                  return true;
              case R.id.nav_2:
                  toolbar.setTitle("nav 2");
                  return true;
              case R.id.nav_3:
                  toolbar.setTitle("nav 3");
                  return true;
              case R.id.nav_4:
                  toolbar.setTitle("nav 4");
                  return true;
              case R.id.nav_5:
                  toolbar.setTitle("nav 5");
                  return true;
          }
          return false;
        });

    }

    @Override
    protected Navigator getNavigator() {
        return navigator;
    }

    @Override
    protected Common getCommon() {
        return common;
    }

    @Override
    public void inject() {
        component = DaggerActivityComponent.builder()
                .applicationComponent(App.getComponent())
                .activityModule(new ActivityModule(this))
                .build();
        getComponent().inject(this);
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

    public ActivityComponent getComponent() {
        return component;
    }
}
