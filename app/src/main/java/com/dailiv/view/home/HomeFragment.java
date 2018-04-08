package com.dailiv.view.home;

import android.os.Bundle;
import android.widget.TextView;

import com.dailiv.App;
import com.dailiv.R;
import com.dailiv.internal.injector.component.DaggerFragmentComponent;
import com.dailiv.internal.injector.module.FragmentModule;
import com.dailiv.util.common.Navigator;
import com.dailiv.view.base.AbstractFragment;
import com.dailiv.view.location.LocationActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.dailiv.util.common.Preferences.getLocation;

/**
 * Created by aldo on 4/1/18.
 */

public class HomeFragment extends AbstractFragment implements HomeView{


    @Inject
    Navigator navigator;

    @Inject
    HomePresenter presenter;

    @BindView(R.id.tv_current_location)
    TextView tvLocation;

    @Override
    public void inject() {

        DaggerFragmentComponent.builder()
                .applicationComponent(App.getComponent())
                .fragmentModule(new FragmentModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void onAttach() {
        presenter.onAttach(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initComponents(Bundle savedInstanceState) {
        inject();
        onAttach();
        setLocation();
        presenter.getHome();
        presenter.getIngredient();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        presenter.onDetach();
    }

    @Override
    public void onShowProgressBar() {

    }

    @Override
    public void onHideProgressBar() {

    }

    public void setLocation() {
        tvLocation.setText(getLocation().getLocationName());
    }

    @OnClick(R.id.btn_change_location)
    public void changeLocation() {
        navigator.openActivity(getActivity(), LocationActivity.class);
    }

    @Override
    public void onResume() {
        setLocation();
        super.onResume();
    }
}
