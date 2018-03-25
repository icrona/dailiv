package com.dailiv.view.custom;

import android.content.Context;
import android.support.v7.widget.AppCompatRadioButton;
import android.util.AttributeSet;

/**
 * Created by aldo on 3/25/18.
 */

public class CustomToggleRadioButton extends AppCompatRadioButton {

    public CustomToggleRadioButton(Context context) {
        super(context);
    }

    public CustomToggleRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomToggleRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void toggle() {
        setChecked(!isChecked());
    }
}
