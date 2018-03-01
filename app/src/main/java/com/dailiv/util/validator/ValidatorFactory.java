package com.dailiv.util.validator;

/**
 * Created by aldo on 3/1/18.
 */

public final class ValidatorFactory {

    public final IValidator<String> getEmailValidator() {
        return new EmailValidator();
    }
}
