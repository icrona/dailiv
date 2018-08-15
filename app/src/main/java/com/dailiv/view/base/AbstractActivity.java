package com.dailiv.view.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.dailiv.App;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.dailiv.App.getContext;

/**
 * Created by aldo on 3/1/18.
 */

public abstract class AbstractActivity extends AppCompatActivity implements IDetachView{

    protected Unbinder unbinder;

    protected abstract int getContentView();

    protected abstract void initComponents(final Bundle savedInstanceState);

    protected abstract String getScreenName();

    private Tracker mTracker;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        unbinder = ButterKnife.bind(this);
        initComponents(savedInstanceState);

        mTracker = ((App)getContext()).getDefaultTracker();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mTracker.setScreenName(getScreenName());
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
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
