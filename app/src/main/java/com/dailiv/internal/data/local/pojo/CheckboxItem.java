package com.dailiv.internal.data.local.pojo;

import com.dailiv.internal.data.remote.response.Category;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by aldo on 4/15/18.
 */

@Data
@AllArgsConstructor
public class CheckboxItem {

    private String name;

    private boolean selected;

    public CheckboxItem(Category category) {
        this(category.getName(), false);
    }
}
