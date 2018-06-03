package com.dailiv.internal.data.local.pojo;

import com.dailiv.internal.data.remote.response.profile.ProfileResponse;

import lombok.AllArgsConstructor;
import lombok.Data;

import static com.dailiv.util.common.AssetUtil.getUserImageUrl;

/**
 * Created by aldo on 6/1/18.
 */

@Data
@AllArgsConstructor
public class Profile {

    private int userId;

    private String firstName;

    private String lastName;

    private String userName;

    private String userPhoto;

    private String headline;

    private String email;

    private String phoneNumber;

    private int numOfRecipe;

    private int numOfFollower;

    private int numOfFollowing;

    public Profile(ProfileResponse response) {

        this.userId = response.user.id;
        this.firstName =response.user.firstname;
        this.lastName =response.user.lastname;
        this.userName = response.user.getFullname();
        this.userPhoto = response.user.photo;
        this.headline = response.user.headline;
        this.email = response.user.email;
        this.phoneNumber = response.user.phone;
        this.numOfRecipe = response.totalRecipeByMe;
        this.numOfFollower = response.totalFollower;
        this.numOfFollowing = response.totalFollowing;

    }

    public String getImageUrl() {

        return getUserImageUrl(getUserPhoto());

    }
}
