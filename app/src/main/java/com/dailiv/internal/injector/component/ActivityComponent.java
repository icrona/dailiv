package com.dailiv.internal.injector.component;

import android.content.Context;

import com.dailiv.internal.injector.module.ActivityModule;
import com.dailiv.internal.injector.scope.ActivityContext;
import com.dailiv.internal.injector.scope.ActivityScope;
import com.dailiv.view.profile.history.OrderHistoryActivity;
import com.dailiv.view.cart.CartActivity;
import com.dailiv.view.delivery.DeliveryActivity;
import com.dailiv.view.location.LocationActivity;
import com.dailiv.view.login.LoginActivity;
import com.dailiv.view.main.MainActivity;
import com.dailiv.view.onboard.OnboardActivty;
import com.dailiv.view.payment.PaymentActivity;
import com.dailiv.view.profile.mealplan.MealPlanActivity;
import com.dailiv.view.recipe.detail.RecipeDetailActivity;
import com.dailiv.view.register.RegisterActivity;
import com.dailiv.view.shop.detail.IngredientDetailActivity;
import com.dailiv.view.splash.SplashActivity;

import dagger.Component;

/**
 * Created by aldo on 3/1/18.
 */

@ActivityScope
@Component(
        dependencies = ApplicationComponent.class,
        modules = {ActivityModule.class}
)
public interface ActivityComponent {

    @ActivityContext
    Context getContext();

    void inject(SplashActivity splashActivity);

    void inject(OnboardActivty onboardActivty);

    void inject(MainActivity mainActivity);

    void inject(LoginActivity loginActivity);

    void inject(RegisterActivity registerActivity);

    void inject(LocationActivity locationActivity);

    void inject(CartActivity cartActivity);

    void inject(DeliveryActivity deliveryActivity);

    void inject(PaymentActivity paymentActivity);

    void inject(RecipeDetailActivity recipeDetailActivity);

    void inject(IngredientDetailActivity ingredientDetailActivity);

    void inject(OrderHistoryActivity orderHistoryActivity);

    void inject(MealPlanActivity mealPlanActivity);

}
