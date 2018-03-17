package com.dailiv.internal.data.local.binding;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by aldo on 3/6/18.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterBinding extends BaseObservable{

    @Bindable
    private String email;

    @Bindable
    private String password;

    @Bindable
    private String fullName;

    @Bindable
    private String phone;
}
