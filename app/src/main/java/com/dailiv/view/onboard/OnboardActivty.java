package com.dailiv.view.onboard;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.dailiv.App;
import com.dailiv.R;
import com.dailiv.internal.injector.component.DaggerActivityComponent;
import com.dailiv.internal.injector.module.ActivityModule;
import com.github.paolorotolo.appintro.AppIntro2;

/**
 * Created by aldo on 3/9/18.
 */

public class OnboardActivty extends AppIntro2 {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inject();
        addSlide(OnboardSlide.newInstance(R.layout.onboard_slide_1));
        addSlide(OnboardSlide.newInstance(R.layout.onboard_slide_2));

        showSkipButton(false);

        setFadeAnimation();
    }

    private void inject(){
        DaggerActivityComponent.builder()
                .applicationComponent(App.getComponent())
                .activityModule(new ActivityModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        finish();
    }
}
