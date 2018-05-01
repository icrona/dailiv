package com.dailiv.util.common;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.dailiv.view.login.LoginActivity;
import com.dailiv.view.main.MainActivity;
import com.dailiv.view.onboard.OnboardActivty;

/**
 * Created by aldo on 3/1/18.
 */

public final class Navigator {


    public void openActivity(final Activity activity, final Class destination) {

        final Intent intent = new Intent(activity, destination);
        activity.startActivity(intent);

    }

    public void openDetails(final Activity activity, final Class destination, final String identifier) {

        final Intent intent = new Intent(activity, destination);
        Bundle bundle = new Bundle();
        bundle.putString("identifier", identifier);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    public void openMainActivity(final Activity activity) {

        openActivityWitClearTask(activity, MainActivity.class);
    }

    public void openOnboardActivity(final Activity activity) {

        openActivityWitClearTask(activity, OnboardActivty.class);
    }

    public void openLoginActivity(final Activity activity) {

        openActivityWitClearTask(activity, LoginActivity.class);
    }

    public void openActivityWitClearTask(final Activity activity, final Class destination) {
        final Intent intent = new Intent(activity, destination);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        activity.startActivity(intent);
    }
}
