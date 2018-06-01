package com.dailiv.internal.data.local.pojo;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by aldo on 6/1/18.
 */

@Data
@AllArgsConstructor
@Parcel(Parcel.Serialization.BEAN)
@NoArgsConstructor(onConstructor = @__(@ParcelConstructor))
public class ProfileRecipeList {

    private Integer userId;

    private String recipeType;

    private String toolbarTitle;

}
