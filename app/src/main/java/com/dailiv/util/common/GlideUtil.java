package com.dailiv.util.common;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.MemoryCategory;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.dailiv.R;
import com.dailiv.view.custom.RoundedCornersTransformation;

import static com.dailiv.App.getContext;

/**
 * Created by aldo on 6/15/18.
 */

public class GlideUtil {

    public static void glide(ImageView imageView, String imageUrl) {

        Glide.get(imageView.getContext()).setMemoryCategory(MemoryCategory.HIGH);

        Glide.with(imageView.getContext())
                .load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .thumbnail(Glide.with(getContext()).load(R.mipmap.ic_loading))
                .dontAnimate()
                .into(imageView);
    }

    public static void glideRounded(ImageView imageView, String imageUrl) {
        Glide.get(imageView.getContext()).setMemoryCategory(MemoryCategory.HIGH);

        Glide.with(imageView.getContext())
                .load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .thumbnail(Glide.with(getContext()).load(R.mipmap.ic_loading))
                .bitmapTransform(new RoundedCornersTransformation(getContext(), getContext().getResources().getDimensionPixelOffset(R.dimen.xs), 0, RoundedCornersTransformation.CornerType.TOP))
                .dontAnimate()
                .into(imageView);
    }
}
