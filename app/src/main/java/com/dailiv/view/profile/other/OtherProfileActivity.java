package com.dailiv.view.profile.other;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dailiv.App;
import com.dailiv.R;
import com.dailiv.internal.data.local.pojo.Profile;
import com.dailiv.internal.data.local.pojo.ProfileMenu;
import com.dailiv.internal.data.local.pojo.ProfileRecipeList;
import com.dailiv.internal.data.remote.response.profile.ProfileResponse;
import com.dailiv.internal.injector.component.DaggerActivityComponent;
import com.dailiv.internal.injector.module.ActivityModule;
import com.dailiv.util.common.Navigator;
import com.dailiv.view.base.AbstractActivity;
import com.dailiv.view.profile.menu.ProfileMenuAdapter;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.dailiv.internal.data.remote.IApiConstant.COOKED_RECIPE;
import static com.dailiv.internal.data.remote.IApiConstant.LIKED_RECIPE;
import static com.dailiv.internal.data.remote.IApiConstant.RECIPE_BY_ME;
import static com.dailiv.util.IConstants.FragmentIndex.PROFILE;
import static com.dailiv.util.common.GlideUtil.glide;
import static com.dailiv.util.common.Preferences.getAccountSlug;

/**
 * Created by aldo on 6/3/18.
 */

public class OtherProfileActivity extends AbstractActivity implements OtherProfileView {

    @Inject
    OtherProfilePresenter presenter;

    @Inject
    Navigator navigator;

    @BindView(R.id.toolbar_profile)
    Toolbar toolbar;

    @BindString(R.string.liked_recipes)
    String sLikedRecipes;
    @BindString(R.string.cooked_recipes)
    String sCookedRecipes;
    @BindString(R.string.recipe_by_user)
    String sRecipeByUser;

    private Profile profile;

    @BindView(R.id.rv_profile_menu)
    RecyclerView rvProfileMenu;

    @BindView(R.id.civ_user_photo)
    CircleImageView civUserPhoto;

    @BindView(R.id.tv_user_name)
    TextView tvName;

    @BindView(R.id.tv_user_headline)
    TextView tvHeadline;

    @BindView(R.id.tv_num_of_recipe)
    TextView tvNumOfRecipe;

    @BindView(R.id.tv_num_followers)
    TextView tvNumOfFollowers;

    @BindView(R.id.tv_num_of_following)
    TextView tvNumOfFollowing;

    @BindView(R.id.ll_follow)
    LinearLayout llFollow;

    @BindView(R.id.btn_follow)
    Button btnFollow;

    @BindView(R.id.btn_unfollow)
    Button btnUnfollow;

    @Override
    public void onDetach() {
        presenter.onDetach();
    }

    @Override
    public void onShowProgressBar() {

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
    public void onHideProgressBar() {

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_other_profile;
    }

    @Override
    protected void initComponents(Bundle savedInstanceState) {
        inject();
        onAttach();
        setToolbar();

        final Bundle bundle = getIntent().getExtras();

        String userSlug = bundle.getString("identifier");
        if(userSlug.equals(getAccountSlug())){

            navigator.openMainActivityFragment(this, PROFILE);
        }
        else{
            presenter.getProfile(userSlug);
            llFollow.setVisibility(View.VISIBLE);
        }
    }

    private void setAdapter() {

        ProfileMenuAdapter profileMenuAdapter = new ProfileMenuAdapter(
                getProfileMenu()
        );

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        rvProfileMenu.setLayoutManager(linearLayoutManager);

        rvProfileMenu.setAdapter(profileMenuAdapter);

    }

    private List<ProfileMenu> getProfileMenu() {

        return Arrays.asList(
                new ProfileMenu(
                        getRecipeByUserString(),
                        R.drawable.ic_recipe_red,
                        this::openRecipeByMe
                ),
                new ProfileMenu(
                        sLikedRecipes,
                        R.drawable.ic_like_red,
                        this::openLikedRecipe
                ),
                new ProfileMenu(
                        sCookedRecipes,
                        R.drawable.ic_cook_red,
                        this::openCookedRecipe
                )
        );

    }

    private String getRecipeByUserString() {

       return String.format(sRecipeByUser, profile.getFirstName());
    }

    public void openLikedRecipe() {
        navigateToRecipeList(new ProfileRecipeList(
                profile.getUserId(),
                LIKED_RECIPE,
                sLikedRecipes
        ));
    }

    public void openCookedRecipe() {
        navigateToRecipeList(new ProfileRecipeList(
                profile.getUserId(),
                COOKED_RECIPE,
                sCookedRecipes
        ));
    }

    public void openRecipeByMe() {
        navigateToRecipeList(new ProfileRecipeList(
                profile.getUserId(),
                RECIPE_BY_ME,
                getRecipeByUserString()
        ));
    }

    private void navigateToRecipeList(ProfileRecipeList profileRecipeList) {

        navigator.openProfileRecipeList(this, profileRecipeList);
    }

    @Override
    public void showResponse(ProfileResponse response) {

        profile = new Profile(response);

        glide(civUserPhoto, profile.getImageUrl());

        tvName.setText(profile.getUserName());

        tvHeadline.setText(profile.getHeadline());

        tvNumOfRecipe.setText(String.valueOf(profile.getNumOfRecipe()));

        tvNumOfFollowers.setText(String.valueOf(profile.getNumOfFollower()));

        tvNumOfFollowing.setText(String.valueOf(profile.getNumOfFollowing()));

        if(profile.isFollowed()){

            btnUnfollow.setVisibility(View.VISIBLE);
        }

        else{
            btnFollow.setVisibility(View.VISIBLE);
        }

        setAdapter();
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

    @OnClick(R.id.btn_follow)
    public void follow() {
        presenter.follow(profile.getUserId());
        btnFollow.setVisibility(View.GONE);
        btnUnfollow.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.btn_unfollow)
    public void unfollow() {
        presenter.unfollow(profile.getUserId());
        btnUnfollow.setVisibility(View.GONE);
        btnFollow.setVisibility(View.VISIBLE);
    }

}
