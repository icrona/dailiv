package com.dailiv.view.profile;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.dailiv.App;
import com.dailiv.R;
import com.dailiv.internal.data.local.pojo.OrderHistory;
import com.dailiv.internal.data.local.pojo.ProfileMenu;
import com.dailiv.internal.injector.component.DaggerFragmentComponent;
import com.dailiv.internal.injector.module.FragmentModule;
import com.dailiv.util.common.Navigator;
import com.dailiv.view.base.AbstractFragment;
import com.dailiv.view.login.LoginActivity;
import com.dailiv.view.profile.history.OrderHistoryActivity;
import com.dailiv.view.profile.mealplan.MealPlanActivity;
import com.dailiv.view.profile.menu.ProfileMenuAdapter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

import static com.dailiv.util.common.Preferences.deleteAccessToken;
import static java.util.Collections.singletonList;

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
    }

    private void setAdapter() {

        ProfileMenuAdapter profileMenuAdapter = new ProfileMenuAdapter(
                getProfileMenu(),
                this::navigateTo
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
                        null
                ),
                new ProfileMenu(
                        sLikedRecipes,
                        R.drawable.ic_like_red,
                        null
                ),
                new ProfileMenu(
                        sCookedRecipes,
                        R.drawable.ic_cook_red,
                        null
                ),
                new ProfileMenu(
                        sMealPlan,
                        R.drawable.ic_meal_plan_red,
                        MealPlanActivity.class
                ),
                new ProfileMenu(
                        sNewsFeed,
                        R.drawable.ic_news_feed_red,
                        null
                ),
                new ProfileMenu(
                        sOrderHistory,
                        R.drawable.ic_history_red,
                        OrderHistoryActivity.class
                )
        );

    }

    @Override
    public void onShowProgressBar() {

    }

    @Override
    public void onHideProgressBar() {

    }

    private void navigateTo(Class destination) {

        navigator.openActivity(getActivity(), destination);
    }

    @OnClick(R.id.btn_logout)
    public void logout() {

        deleteAccessToken();

        navigator.openActivityWitClearTask(getActivity(), LoginActivity.class);
    }
}
