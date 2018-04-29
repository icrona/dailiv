package com.dailiv.view.recipe.detail;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.dailiv.App;
import com.dailiv.R;
import com.dailiv.internal.data.remote.response.recipe.RecipeDetailResponse;
import com.dailiv.internal.injector.component.DaggerActivityComponent;
import com.dailiv.internal.injector.module.ActivityModule;
import com.dailiv.view.base.AbstractActivity;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by aldo on 4/29/18.
 */

public class RecipeDetailActivity extends AbstractActivity implements RecipeDetailView{

    @Inject
    RecipeDetailPresenter presenter;

    @BindView(R.id.toolbar_recipe_detail)
    Toolbar toolbar;

    @Override
    public void onDetach() {

        presenter.onDetach();
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
    public void onShowProgressBar() {

    }

    @Override
    public void onHideProgressBar() {

    }

    @Override
    public void showDetail(RecipeDetailResponse response) {

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_recipe_detail;
    }

    @Override
    protected void initComponents(Bundle savedInstanceState) {
        inject();
        onAttach();
        setToolbar();
    }

    private void setToolbar() {
        toolbar.setPadding(0, getStatusBarHeight(), 0, 0);
        toolbar.setNavigationIcon(
                ContextCompat.getDrawable(this, R.drawable.ic_back));
        toolbar.hideOverflowMenu();
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                finish();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
