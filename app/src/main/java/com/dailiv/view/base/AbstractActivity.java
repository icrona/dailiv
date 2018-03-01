package com.dailiv.view.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.dailiv.util.common.Common;
import com.dailiv.util.common.Navigator;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by aldo on 3/1/18.
 */

public abstract class AbstractActivity extends AppCompatActivity implements IDetachView{

    protected Unbinder unbinder;

    protected abstract int getContentView();

    protected abstract void initComponents(final Bundle savedInstanceState);

    protected abstract Navigator getNavigator();

    protected abstract Common getCommon();

    //todo


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        unbinder = ButterKnife.bind(this);
        initComponents(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        onDetach();
        unbinder.unbind();
        super.onDestroy();
    }

    public void onShowError(final String message) {
        //TODO
    }

    protected int getStatusBarHeight() {

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) return 0;

        final int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");

        if(resourceId <= 0) return 0;

        return getResources().getDimensionPixelSize(resourceId);
    }

}
