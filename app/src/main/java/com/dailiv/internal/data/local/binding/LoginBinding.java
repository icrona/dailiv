package com.dailiv.internal.data.local.binding;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by aldo on 3/6/18.
 */

@Getter
@Setter
@AllArgsConstructor
public class LoginBinding extends BaseObservable{

    @Bindable
    private String email;

    @Bindable
    private String password;

}
