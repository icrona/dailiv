package com.dailiv.internal.data.local.pojo;

import com.dailiv.internal.data.remote.request.review.SubmitReviewRequest;
import com.dailiv.internal.data.remote.response.review.ReviewNeededResponse;

import lombok.AllArgsConstructor;
import lombok.Data;

import static com.dailiv.util.common.AssetUtil.getUserImageUrl;

/**
 * Created by aldo on 5/19/18.
 */

@Data
@AllArgsConstructor
public class Review {

    private int driverId;

    private String driverName;

    private String driverPhoto;

    private String review;

    private int rating;

    public SubmitReviewRequest toRequest() {

        SubmitReviewRequest submitReviewRequest = new SubmitReviewRequest();

        submitReviewRequest.driverAssignId = driverId;

        submitReviewRequest.rating = rating;

        submitReviewRequest.review = review;

        return submitReviewRequest;
    }

    public Review(ReviewNeededResponse response) {

        this(
                response.driverAssignPurchase.get(0).pivot.id,
                response.driverAssignPurchase.get(0).getFullname(),
                response.driverAssignPurchase.get(0).photo,
                "",
                0
        );
    }

    public String getDriverPhotoUrl() {

        return getUserImageUrl(getDriverPhoto());
    }
}
