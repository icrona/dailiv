package com.dailiv.util.common;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;

import com.dailiv.R;
import com.dailiv.view.custom.BadgeDrawable;

/**
 * Created by aldo on 3/1/18.
 */

public class Common {

    public int getDpFromPixel(final Context context, final float pixels) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pixels * scale + 0.5f);
    }

    public void setBadgeCount(Context context, LayerDrawable icon, int count) {

        BadgeDrawable badge;

        // Reuse drawable if possible
        Drawable reuse = icon.findDrawableByLayerId(R.id.ic_badge);
        if (reuse != null && reuse instanceof BadgeDrawable) {
            badge = (BadgeDrawable) reuse;
        } else {
            badge = new BadgeDrawable(context);
        }

        badge.setCount(count);
        icon.mutate();
        icon.setDrawableByLayerId(R.id.ic_badge, badge);
    }


}
