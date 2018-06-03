package com.dailiv.internal.data.remote.response.profile;

import com.dailiv.internal.data.remote.response.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by aldo on 6/1/18.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProfileResponse {

    public User user;

    @JsonProperty(value = "total_recipe_by_me")
    public int totalRecipeByMe;

    @JsonProperty(value = "total_follower")
    public int totalFollower;

    @JsonProperty(value = "total_following")
    public int totalFollowing;

    public boolean followed;

}
