package com.dailiv.internal.data.local.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by aldo on 3/31/18.
 */

@Getter
@AllArgsConstructor
public enum SearchType {

    RECIPE(""),
    INGREDIENT("");

    //todo
    private String icon;
}
