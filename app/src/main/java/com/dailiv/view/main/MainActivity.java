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
import com.dailiv.internal.data.local.pojo.Review;
import com.dailiv.internal.data.local.pojo.SearchResult;
import com.dailiv.internal.data.remote.request.review.SubmitReviewRequest;
import com.dailiv.internal.data.remote.response.review.ReviewNeededResponse;
import com.dailiv.internal.injector.component.DaggerActivityComponent;
import com.dailiv.internal.injector.module.ActivityModule;
import com.dailiv.util.common.Common;
import com.dailiv.util.common.Navigator;
import com.dailiv.view.base.AbstractActivity;
import com.dailiv.view.cart.CartActivity;
import com.dailiv.view.custom.BadgeDrawable;
import com.dailiv.view.custom.ReviewDialog;
import com.dailiv.view.home.HomeFragment;
import com.dailiv.view.location.LocationActivity;
import com.dailiv.view.notification.NotificationFragment;
import com.dailiv.view.profile.ProfileFragment;
import com.dailiv.view.recipe.RecipeFragment;
import com.dailiv.view.search.SearchAdapter;
import com.dailiv.view.shop.ShopFragment;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindArray;
import butterknife.BindView;
import rx.functions.Action1;

import static com.dailiv.util.IConstants.FragmentIndex.HOME;
import static com.dailiv.util.IConstants.FragmentIndex.NOTIFICATION;
import static com.dailiv.util.IConstants.FragmentIndex.PROFILE;
import static com.dailiv.util.IConstants.FragmentIndex.RECIPE;
import static com.dailiv.util.IConstants.FragmentIndex.SHOP;
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

    private int cartCount;

    private LayerDrawable cartIcon;

    private LayerDrawable notifIcon;

    private SearchView searchView;

    private SearchAdapter searchAdapter;

    private ReviewDialog reviewDialog;

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

        setCartCount(this.cartCount);

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

        setReviewDialog();
        presenter.checkReviewIsNeeded();
        setNavigation();

        //todo
        invalidateOptionsMenu();

        setSearchAdapter();

        final Bundle bundle = getIntent().getExtras();

        if(bundle!= null && bundle.getInt("fragmentIndex", -1) != -1) {
            navigateTo(bundle.getInt("fragmentIndex"));
        }

    }

    private void setReviewDialog() {

        reviewDialog = new ReviewDialog(this, getLayoutInflater()) {
            @Override
            public Action1<SubmitReviewRequest> submitAction() {
                return presenter::submitReview;
            }

            @Override
            public String title() {
                return null;
            }
        };

    }

    @Override
    public void onGetReviewNeeded(ReviewNeededResponse reviewNeededResponse) {

        if(reviewNeededResponse.driverAssignPurchase == null) {
            return;
        }
        reviewDialog.show(new Review(reviewNeededResponse));
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
                  setFragment(new HomeFragment(), HOME);
                  return true;
              case R.id.nav_shop:
                  setFragment(new ShopFragment(), SHOP);
                  return true;
              case R.id.nav_recipe:
                  setFragment(new RecipeFragment(), RECIPE);
                  return true;
              case R.id.nav_notification:
                  setFragment(new NotificationFragment(), NOTIFICATION);
                  return true;
              case R.id.nav_account:
                  setFragment(new ProfileFragment(), PROFILE);
                  return true;
          }
          return false;
        });

        setFragment(new HomeFragment(), HOME);
    }

    public void navigateTo(int index) {
        navigationMenu.setCurrentItem(index);
    }

    public void setFragment(Fragment fragment, int index) {
        toolbar.setTitle(menuText[index]);
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
        presenter.getCartCount();
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

    @Override
    public void onGetCartCount(int cartCount) {

        this.cartCount = cartCount;
        invalidateOptionsMenu();

    }

    public void setCartCount(int cartCount) {

        common.setBadgeCount(this, cartIcon, cartCount);
    }
    public void updateCartBadge(int changes) {

        cartCount += changes;
        setCartCount(cartCount);
    }

    private void updateNotifBadge() {

        //todo
//        setBadgeCount(notifIcon, 1);
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

        llSearchResults.setVisibility(View.GONE);
        flMain.setVisibility(View.VISIBLE);

        navigator.openDetails(this, searchResult.getSearchType().getActivityClass(), searchResult.getIdentifier());
    }
}
