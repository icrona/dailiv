package com.dailiv.internal.data.local.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by aldo on 3/12/18.
 */

@Getter
@AllArgsConstructor
public enum SortBy {

    NEWEST("Newest", "newest"),
    POPULARITY("Popularity", "popular"),
    MOST_LIKED("Most Liked", "liked");

    private String text;
    private String key;
}
