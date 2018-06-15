package com.dailiv.view.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by aldo on 3/1/18.
 */

public abstract class AbstractActivity extends AppCompatActivity implements IDetachView{

    protected Unbinder unbinder;

    protected abstract int getContentView();

    protected abstract void initComponents(final Bundle savedInstanceState);

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

        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    protected int getStatusBarHeight() {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return 0;
        }

        final int resourceId = getResources().getIdentifier("status_bar_height", "dimen",
                "android");

        int result = 0;
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }

        return result;
    }

}
