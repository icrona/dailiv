package com.dailiv.view.custom;

import android.content.Context;
import android.support.v7.widget.AppCompatSpinner;
import android.util.AttributeSet;

/**
 * Created by aldo on 4/8/18.
 */

public class ReselectSpinner extends AppCompatSpinner {

    public ReselectSpinner(Context context) {
        super(context);
    }

    public ReselectSpinner(Context context, int mode) {
        super(context, mode);
    }

    public ReselectSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ReselectSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setSelection(int position, boolean animate) {
        boolean sameSelected = position == getSelectedItemPosition();
        super.setSelection(position, animate);
        if (sameSelected) {

            getOnItemSelectedListener().onItemSelected(this, getSelectedView(), position, getSelectedItemId());
        }
    }

    @Override
    public void setSelection(int position) {
        boolean sameSelected = position == getSelectedItemPosition();
        super.setSelection(position);
        if (sameSelected) {

            getOnItemSelectedListener().onItemSelected(this, getSelectedView(), position, getSelectedItemId());
        }
    }

}
