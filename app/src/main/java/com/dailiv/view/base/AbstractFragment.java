package com.dailiv.view.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dailiv.util.common.Common;
import com.dailiv.util.common.Navigator;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by aldo on 3/1/18.
 */

public abstract class AbstractFragment extends Fragment implements IDetachView{

    protected View view;

    protected Unbinder unbinder;

    protected abstract int getContentView();

    protected abstract void initComponents(final Bundle savedInstanceState);

    protected abstract Navigator getNavigator();

    protected abstract Common getCommon();

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
    }
}
