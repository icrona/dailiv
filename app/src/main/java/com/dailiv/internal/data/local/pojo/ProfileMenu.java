package com.dailiv.internal.data.local.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import rx.functions.Action0;

/**
 * Created by aldo on 5/12/18.
 */

@Data
@AllArgsConstructor
public class ProfileMenu {

    private String name;

    private int iconResId;

    private Action0 profileAction;
}
