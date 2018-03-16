package com.dailiv.internal.data.local.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by aldo on 3/12/18.
 */

@Getter
@AllArgsConstructor
public enum SortBy {

    NEWEST("newest"),
    POPULARITY("popular"),
    MOST_LIKED("liked");

    private String key;
}
