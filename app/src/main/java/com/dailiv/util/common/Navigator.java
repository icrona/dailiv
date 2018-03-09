package com.dailiv.util.common;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by aldo on 3/1/18.
 */

public final class Navigator {


    public void openActivity(final Activity activity, final Class destination) {
        final Intent intent = new Intent(activity, destination);
        activity.startActivity(intent);

    }
}
