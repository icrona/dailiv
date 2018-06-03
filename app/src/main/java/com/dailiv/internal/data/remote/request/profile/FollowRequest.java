package com.dailiv.internal.data.remote.request.profile;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by aldo on 6/3/18.
 */

public class FollowRequest {

    @JsonProperty(value = "user_id")
    public int userId;
}
