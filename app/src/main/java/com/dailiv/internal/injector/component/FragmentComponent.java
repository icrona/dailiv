package com.dailiv.internal.injector.component;

import android.content.Context;

import com.dailiv.internal.injector.module.FragmentModule;
import com.dailiv.internal.injector.scope.ActivityContext;
import com.dailiv.internal.injector.scope.FragmentScope;
import com.dailiv.view.profile.ProfileFragment;
import com.dailiv.view.home.HomeFragment;
import com.dailiv.view.notification.NotificationFragment;
import com.dailiv.view.recipe.RecipeFragment;
import com.dailiv.view.shop.ShopFragment;

import dagger.Component;

/**
 * Created by aldo on 3/1/18.
 */

@FragmentScope
@Component(
        dependencies = ApplicationComponent.class,
        modules = FragmentModule.class
)
public interface FragmentComponent {

    @ActivityContext
    Context getContext();

    void inject(HomeFragment homeFragment);

    void inject(ProfileFragment profileFragment);

    void inject(NotificationFragment notificationFragment);

    void inject(RecipeFragment recipeFragment);

    void inject(ShopFragment shopFragment);


}
