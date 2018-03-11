package com.dailiv.internal.data.remote.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by aldo on 3/5/18.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class User extends BaseResponse {

    @JsonProperty(value = "facebook_id")
    public Integer facebookId;

    @JsonProperty(value = "google_id")
    public Integer googleId;

    public String firstname;

    public String lastname;

    public String email;

    public String slug;

    public String photo;

    public String headline;

    public boolean confirmed;

    public String phone;

    public String gender;

    public String token;

    public String balance;

    @JsonProperty(value = "last_activity")
    public String lastActivity;

}