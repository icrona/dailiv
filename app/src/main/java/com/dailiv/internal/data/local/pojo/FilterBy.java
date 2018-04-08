package com.dailiv.internal.data.local.pojo;

import lombok.Data;

/**
 * Created by aldo on 4/8/18.
 */

@Data
public class FilterBy {

    private String text;

    private boolean selected;

    private String info;

    public FilterBy(String text) {
        this.text = text;
        this.info = "";
    }

    public String getSpinnerView() {
        return getText() + " " +getInfo();
    }
}
