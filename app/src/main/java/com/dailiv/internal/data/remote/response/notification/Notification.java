package com.dailiv.internal.data.remote.response.notification;

import com.dailiv.internal.data.remote.response.BaseResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by aldo on 3/11/18.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Notification extends BaseResponse {

    @JsonProperty(value = "user_id")
    public int userId;

    public String url;

    public String title;

    public String description;

    public String photo;

    public String category;

    public String diffForHuman;

}
