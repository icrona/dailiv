package com.dailiv.view.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dailiv.App;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.dailiv.App.getContext;

/**
 * Created by aldo on 3/1/18.
 */

public abstract class AbstractFragment extends Fragment implements IDetachView{

    protected View view;

    protected Unbinder unbinder;

    protected abstract int getContentView();

    protected abstract void initComponents(final Bundle savedInstanceState);

    protected abstract String getScreenName();

    private Tracker mTracker;

    //todo

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initComponents(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(getContentView(), container, false);
        unbinder = ButterKnife.bind(this, view);

        mTracker = ((App)App.getContext()).getDefaultTracker();
        return view;

    }

    @Override
    public void onDestroyView() {
        onDetach();
        unbinder.unbind();
        view = null;
        super.onDestroyView();
    }

    public void onShowError(final String message) {
        //TODO
        System.out.println(message);
    }

    @Override
    public void onResume() {
        super.onResume();
        mTracker.setScreenName(getScreenName());
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }
}
