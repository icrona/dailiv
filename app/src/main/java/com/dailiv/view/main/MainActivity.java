package com.dailiv.view.main;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.dailiv.App;
import com.dailiv.R;
import com.dailiv.internal.injector.component.DaggerActivityComponent;
import com.dailiv.internal.injector.module.ActivityModule;
import com.dailiv.util.common.Common;
import com.dailiv.util.common.Navigator;
import com.dailiv.view.base.AbstractActivity;
import com.dailiv.view.custom.BadgeDrawable;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import javax.inject.Inject;

import butterknife.BindArray;
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

    private LayerDrawable cartIcon;

    private LayerDrawable notifIcon;

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        MenuItem search = menu.findItem(R.id.search);
        MenuItem cart = menu.findItem(R.id.cart);

        cartIcon = (LayerDrawable) cart.getIcon();

        updateCartBadge();

        //todo
        search.setVisible(true);
        cart.setVisible(true);
        super.onPrepareOptionsMenu(menu);

        return true;
    }



    @Override
    protected void initComponents(Bundle savedInstanceState) {

        inject();
        onAttach();
        setToolbar();

        setNavigation();

        //todo
        invalidateOptionsMenu();

    }
    private void setToolbar() {
        toolbar.setPadding(0, getStatusBarHeight(), 0, 0);
        toolbar.setTitle(menuText[0]);
        toolbar.hideOverflowMenu();
        setSupportActionBar(toolbar);
    }
    private void setNavigation() {
        Menu menu = navigationMenu.getMenu();
        notifIcon = (LayerDrawable) menu.findItem(R.id.nav_notification).getIcon();

        updateNotifBadge();

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
    protected void onResume() {
        invalidateOptionsMenu();
        updateNotifBadge();
        super.onResume();
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

    private void setBadgeCount(LayerDrawable icon, int count) {

        BadgeDrawable badge;

        // Reuse drawable if possible
        Drawable reuse = icon.findDrawableByLayerId(R.id.ic_badge);
        if (reuse != null && reuse instanceof BadgeDrawable) {
            badge = (BadgeDrawable) reuse;
        } else {
            badge = new BadgeDrawable(this);
        }

        badge.setCount(count);
        icon.mutate();
        icon.setDrawableByLayerId(R.id.ic_badge, badge);
    }

    private void updateCartBadge() {

        //todo
        setBadgeCount(cartIcon, 1);
    }

    private void updateNotifBadge() {

        //todo
        setBadgeCount(notifIcon, 1);
    }

}
