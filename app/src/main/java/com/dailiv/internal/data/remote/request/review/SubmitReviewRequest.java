package com.dailiv.internal.data.remote.request.review;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by aldo on 5/19/18.
 */

public class SubmitReviewRequest {

    @JsonProperty("driver_assign_id")
    public int driverAssignId;

    public String review;

    public int rating;
}
