package com.dailiv.util.validator;

import android.text.TextUtils;
import android.util.Patterns;

/**
 * Created by aldo on 3/1/18.
 */

public class EmailValidator implements IValidator<String> {

    @Override
    public boolean isValid(final String object) {
        return !TextUtils.isEmpty(object) && Patterns.EMAIL_ADDRESS.matcher(object).matches();
    }
}
