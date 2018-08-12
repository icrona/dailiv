package com.dailiv.view.profile;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dailiv.App;
import com.dailiv.R;
import com.dailiv.internal.data.local.pojo.EditProfile;
import com.dailiv.internal.data.local.pojo.Profile;
import com.dailiv.internal.data.local.pojo.ProfileMenu;
import com.dailiv.internal.data.local.pojo.ProfileRecipeList;
import com.dailiv.internal.data.remote.response.profile.ProfileResponse;
import com.dailiv.internal.injector.component.DaggerFragmentComponent;
import com.dailiv.internal.injector.module.FragmentModule;
import com.dailiv.util.common.Navigator;
import com.dailiv.view.base.AbstractFragment;
import com.dailiv.view.login.LoginActivity;
import com.dailiv.view.profile.history.OrderHistoryActivity;
import com.dailiv.view.profile.mealplan.MealPlanActivity;
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
import static com.dailiv.util.common.GlideUtil.glide;
import static com.dailiv.util.common.Preferences.deleteAccessTokenAndSlug;
import static com.dailiv.util.common.Preferences.deleteLocation;

/**
 * Created by aldo on 4/1/18.
 */

public class ProfileFragment extends AbstractFragment implements ProfileView {

    @Inject
    ProfilePresenter presenter;

    @Inject
    Navigator navigator;

    @BindView(R.id.rv_profile_menu)
    RecyclerView rvProfileMenu;

    @BindString(R.string.order_history)
    String sOrderHistory;

    @BindString(R.string.meal_plan)
    String sMealPlan;

    @BindString(R.string.liked_recipes)
    String sLikedRecipes;
    @BindString(R.string.cooked_recipes)
    String sCookedRecipes;
    @BindString(R.string.news_feed)
    String sNewsFeed;
    @BindString(R.string.recipe_by_me)
    String sRecipeByMe;

    private Profile profile;

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

    @BindView(R.id.btn_edit_profile)
    Button btnEdit;

    @Override
    public void inject() {

        DaggerFragmentComponent.builder()
                .applicationComponent(App.getComponent())
                .fragmentModule(new FragmentModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void onAttach() {
        presenter.onAttach(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        presenter.onDetach();
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_profile;
    }

    @Override
    protected void initComponents(Bundle savedInstanceState) {
        inject();
        onAttach();
        setAdapter();
        btnEdit.setVisibility(View.VISIBLE);
    }

    private void setAdapter() {

        ProfileMenuAdapter profileMenuAdapter = new ProfileMenuAdapter(
                getProfileMenu()
        );

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        rvProfileMenu.setLayoutManager(linearLayoutManager);

        rvProfileMenu.setAdapter(profileMenuAdapter);

    }

    private List<ProfileMenu> getProfileMenu() {

        return Arrays.asList(
                new ProfileMenu(
                        sRecipeByMe,
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
                ),
                new ProfileMenu(
                        sMealPlan,
                        R.drawable.ic_meal_plan_red,
                        this::openMealPlan
                ),
//                new ProfileMenu(
//                        sNewsFeed,
//                        R.drawable.ic_news_feed_red,
//                        this::openNewsFeed
//                ),
                new ProfileMenu(
                        sOrderHistory,
                        R.drawable.ic_history_red,
                        this::openOrderHistory
                )
        );

    }

    public void openMealPlan() {
        navigateTo(MealPlanActivity.class);
    }

    public void openOrderHistory() {
        navigateTo(OrderHistoryActivity.class);
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
                sRecipeByMe
        ));
    }

    public void openNewsFeed() {

    }

    @Override
    public void onShowProgressBar() {

    }

    @Override
    public void onHideProgressBar() {

    }

    private void navigateToRecipeList(ProfileRecipeList profileRecipeList) {

        navigator.openProfileRecipeList(getActivity(), profileRecipeList);
    }

    private void navigateTo(Class destination) {

        navigator.openActivity(getActivity(), destination);
    }

    @OnClick(R.id.btn_logout)
    public void logout() {

        deleteAccessTokenAndSlug();

        deleteLocation();

        navigator.openActivityWitClearTask(getActivity(), LoginActivity.class);
    }

    @OnClick(R.id.btn_edit_profile)
    public void editProfile() {

        navigator.openEditProfile(getActivity(), new EditProfile(profile));

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

    }

    @Override
    public void onResume() {
        presenter.getProfile();
        super.onResume();
    }
}
