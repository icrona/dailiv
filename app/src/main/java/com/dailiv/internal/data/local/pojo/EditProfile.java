package com.dailiv.internal.data.local.pojo;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by aldo on 6/2/18.
 */


@Data
@AllArgsConstructor
@Parcel(Parcel.Serialization.BEAN)
@NoArgsConstructor(onConstructor = @__(@ParcelConstructor))
public class EditProfile {

    private String photoUrl;

    private String fullName;

    private String headline;

    private String email;

    private String phoneNumber;

    public EditProfile(Profile profile) {

        this.photoUrl = profile.getImageUrl();
        this.fullName = profile.getUserName();
        this.headline = profile.getHeadline();
        this.email = profile.getEmail();
        this.phoneNumber = profile.getPhoneNumber();
    }
}
