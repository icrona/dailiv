package com.dailiv.util.validator;

/**
 * Created by aldo on 3/1/18.
 */

public interface IValidator<T> {

    boolean isValid(T object);
}
