package com.dailiv.view.main;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.dailiv.App;
import com.dailiv.R;
import com.dailiv.internal.data.local.pojo.Location;
import com.dailiv.internal.data.local.pojo.SearchResult;
import com.dailiv.internal.injector.component.DaggerActivityComponent;
import com.dailiv.internal.injector.module.ActivityModule;
import com.dailiv.util.common.Common;
import com.dailiv.util.common.Navigator;
import com.dailiv.view.account.AccountFragment;
import com.dailiv.view.base.AbstractActivity;
import com.dailiv.view.cart.CartActivity;
import com.dailiv.view.custom.BadgeDrawable;
import com.dailiv.view.home.HomeFragment;
import com.dailiv.view.location.LocationActivity;
import com.dailiv.view.notification.NotificationFragment;
import com.dailiv.view.recipe.RecipeFragment;
import com.dailiv.view.search.SearchAdapter;
import com.dailiv.view.shop.ShopFragment;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindArray;
import butterknife.BindView;

import static com.dailiv.util.common.Preferences.getLocation;
import static java.util.Collections.emptyList;

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

    @BindView(R.id.fl_main)
    FrameLayout flMain;

    @BindView(R.id.ll_search_results)
    LinearLayout llSearchResults;

    @BindView(R.id.rv_search_results)
    RecyclerView rvSearchResults;

    private LayerDrawable cartIcon;

    private LayerDrawable notifIcon;

    private SearchView searchView;

    private SearchAdapter searchAdapter;

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);

        MenuItem cart = menu.findItem(R.id.cart);
        MenuItem actionSearch = menu.findItem(R.id.search);

        searchView = (SearchView) actionSearch.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                presenter.doSearch(query);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });



        searchView.findViewById(R.id.search_close_btn).setOnClickListener(v -> {

            //todo
            EditText et = findViewById(R.id.search_src_text);

            et.setText("");

            searchView.setQuery("", false);
            searchView.onActionViewCollapsed();
            searchView.setFocusable(true);
            searchView.setIconified(false);
            searchView.requestFocusFromTouch();

            emptySearchResult();
        });

        actionSearch.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                cart.setVisible(false);
                flMain.setVisibility(View.GONE);
                llSearchResults.setVisibility(View.VISIBLE);
                emptySearchResult();
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                cart.setVisible(true);
                flMain.setVisibility(View.VISIBLE);
                llSearchResults.setVisibility(View.GONE);
                emptySearchResult();
                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        MenuItem cart = menu.findItem(R.id.cart);

        cartIcon = (LayerDrawable) cart.getIcon();

        updateCartBadge();

        super.onPrepareOptionsMenu(menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.cart:{
                navigator.openActivity(this, CartActivity.class);
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initComponents(Bundle savedInstanceState) {

        inject();
        onAttach();
        setToolbar();

        setNavigation();

        //todo
        invalidateOptionsMenu();

        setSearchAdapter();

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
                  setFragment(new HomeFragment());
                  return true;
              case R.id.nav_shop:
                  toolbar.setTitle(menuText[1]);
                  setFragment(new ShopFragment());
                  return true;
              case R.id.nav_recipe:
                  toolbar.setTitle(menuText[2]);
                  setFragment(new RecipeFragment());
                  return true;
              case R.id.nav_notification:
                  toolbar.setTitle(menuText[3]);
                  setFragment(new NotificationFragment());
                  return true;
              case R.id.nav_account:
                  toolbar.setTitle(menuText[4]);
                  setFragment(new AccountFragment());
                  return true;
          }
          return false;
        });

        setFragment(new HomeFragment());
    }

    private void setFragment(Fragment fragment) {
        super.onPostResume();
        final String tag = fragment.getClass().getSimpleName();

        if(getSupportFragmentManager().findFragmentByTag(tag) == null) {

            final FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fl_main, fragment, tag);
            fragmentTransaction.commit();

        }
    }

    @Override
    protected void onResume() {
        invalidateOptionsMenu();
        updateNotifBadge();
        getChosenLocation();
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

    private void getChosenLocation() {

        Location location = getLocation();
        if(location.isAvailable()) {
            //todo
            System.out.println(location.getLocationName());
        }
        else {
            navigator.openActivity(this, LocationActivity.class);
        }
    }

    @Override
    public void onGetSearchResult(List<SearchResult> results) {

        searchAdapter.setSearchResults(results);
        searchAdapter.notifyDataSetChanged();

    }

    private void emptySearchResult() {
        onGetSearchResult(emptyList());
    }

    private void setSearchAdapter() {
        searchAdapter = new SearchAdapter(new ArrayList<>(), this::navigateTo);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvSearchResults.setLayoutManager(linearLayoutManager);
        rvSearchResults.setAdapter(searchAdapter);
    }

    private void navigateTo(SearchResult searchResult) {

        navigator.openDetails(this, searchResult.getSearchType().getActivityClass(), searchResult.getIdentifier());
    }
}
