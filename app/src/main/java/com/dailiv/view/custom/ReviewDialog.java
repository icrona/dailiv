package com.dailiv.view.custom;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dailiv.R;
import com.dailiv.internal.data.local.pojo.Review;
import com.dailiv.internal.data.remote.request.review.SubmitReviewRequest;

import de.hdodenhof.circleimageview.CircleImageView;
import rx.functions.Action1;

import static com.dailiv.util.common.GlideUtil.glide;

/**
 * Created by aldo on 5/19/18.
 */

public abstract class ReviewDialog extends BaseDialog{

    private Review review;

    public ReviewDialog(Context context, LayoutInflater layoutInflater) {
        super(context, layoutInflater, R.layout.dialog_review);
    }

    public void show(Review review) {

        TextView tvName = mView.findViewById(R.id.tv_driver);
        tvName.setText(review.getDriverName());

        CircleImageView civDriver = mView.findViewById(R.id.civ_driver);

        glide(civDriver, review.getDriverPhotoUrl());

        EditText etReview = mView.findViewById(R.id.et_review);

        RatingBar ratingBar = mView.findViewById(R.id.rb_rating);

        this.review = review;

        btnApply.setOnClickListener(view -> {

            int rating = (int) ratingBar.getRating();

            if(rating == 0) {

                Toast.makeText(context, R.string.please_give_rating, Toast.LENGTH_SHORT).show();
                return;
            }
            this.review.setReview(etReview.getText().toString());
            this.review.setRating(rating);
            dialog.dismiss();
            submitAction().call(this.review.toRequest());
        });

        dialog.setCancelable(false);

        dialog.show();

    }

    public abstract Action1<SubmitReviewRequest> submitAction();
}
