package com.dailiv.internal.data.local.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by aldo on 3/31/18.
 */

@Data
@AllArgsConstructor
public class Location {

    private int locationId;

    private String locationName;

    private int storeId;

    public boolean isAvailable() {

        return getLocationId() != 0 && getLocationName() != null && getStoreId() !=0;
    }
}
