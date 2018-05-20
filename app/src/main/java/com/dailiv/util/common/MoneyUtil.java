package com.dailiv.util.common;

import android.text.SpannableString;

import com.dailiv.view.custom.TopAlignSuperscriptSpan;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by aldo on 5/20/18.
 */

public class MoneyUtil {

    public static String getMoney(int value) {

        return "Rp"+ NumberFormat.getNumberInstance(Locale.GERMANY).format(value);
    }

    public static SpannableString getMoneyString(int value) {

        SpannableString ss = new SpannableString(getMoney(value));

        ss.setSpan(new TopAlignSuperscriptSpan(0.5f), 0,2, 0);

        return ss;
    }
}
