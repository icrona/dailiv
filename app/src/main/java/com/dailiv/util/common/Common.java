package com.dailiv.util.common;

import android.content.Context;

/**
 * Created by aldo on 3/1/18.
 */

public class Common {

    public int getDpFromPixel(final Context context, final float pixels) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pixels * scale + 0.5f);
    }

}
