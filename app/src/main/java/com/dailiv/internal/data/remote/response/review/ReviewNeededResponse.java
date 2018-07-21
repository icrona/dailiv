package com.dailiv.internal.data.remote.response.review;

import com.dailiv.internal.data.local.pojo.Review;
import com.dailiv.internal.data.remote.response.BaseResponse;
import com.dailiv.internal.data.remote.response.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by aldo on 5/19/18.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class ReviewNeededResponse extends BaseResponse{

    @JsonProperty(value = "user_id")
    public int userId;

    @JsonProperty(value = "payment_method_id")
    public int paymentMethodId;

    @JsonProperty(value = "user_location_id")
    public int userLocationId;

    public int price;

    @JsonProperty(value = "delivery_fee")
    public int deliveryFee;

    public String status;

    public String note;

    @JsonProperty(value = "driver_assign_purchase")
    public List<ReviewUser> driverAssignPurchase;

    public static class ReviewUser extends User {

        public ReviewPivot pivot;
    }

    public static class ReviewPivot extends BaseResponse{

        @JsonProperty(value = "purchase_ingredient_id")
        public int purchaseIngredientId;

        @JsonProperty(value = "user_id")
        public int userId;

        public String rating;

        public String review;

        public int status;

    }

    //"pivot": {
//        "purchase_ingredient_id": 16,
//        "user_id": 32,
//        "rating": null,
//        "review": null,
//        "status": 4,
//        "id": 7
//        }
}