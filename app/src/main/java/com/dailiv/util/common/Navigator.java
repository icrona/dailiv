package com.dailiv.util.common;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.dailiv.BuildConfig;
import com.dailiv.internal.data.local.pojo.Checkout;
import com.dailiv.util.IConstants;
import com.dailiv.view.login.LoginActivity;
import com.dailiv.view.main.MainActivity;
import com.dailiv.view.onboard.OnboardActivty;

import org.parceler.Parcels;

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

    public void openCheckoutActivity(final Activity activity, final Class destination, Checkout checkout) {
        final Intent intent = new Intent(activity, destination);
        intent.putExtra(IConstants.CHECKOUT, Parcels.wrap(checkout));
        activity.startActivityForResult(intent, IConstants.CHECKOUT_REQUEST_CODE);
    }

    public void openTerms(final Activity activity) {

        openUrl(activity, BuildConfig.TERMS);
    }

    public void openPrivacyPolicy(final Activity activity) {

        openUrl(activity, BuildConfig.PRIVACY_POLICY);
    }

    public void openUrl(final Activity activity, final String url) {

        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        activity.startActivity(intent);
    }

    public void openMainActivityFragment(final Activity activity, final int fragmentIndex) {

        final Intent intent = new Intent(activity, MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("fragmentIndex", fragmentIndex);
        intent.putExtras(bundle);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        activity.startActivity(intent);

    }
}
