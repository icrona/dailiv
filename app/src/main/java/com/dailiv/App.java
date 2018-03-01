package com.dailiv;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.dailiv.internal.injector.component.ApplicationComponent;
import com.dailiv.internal.injector.component.DaggerApplicationComponent;
import com.dailiv.internal.injector.module.ApplicationModule;
import com.dailiv.internal.injector.module.NetworkModule;
import com.dailiv.internal.injector.module.UtilityModule;

/**
 * Created by aldo on 3/1/18.
 */

public class App extends Application {

    private static volatile ApplicationComponent component;

    public static synchronized ApplicationComponent getComponent() {
        return component;
    }

    private static App mInstance;

    public static Context getContext(){
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        // Initialize dagger component for android application
        component = DaggerApplicationComponent.builder().applicationModule(
                new ApplicationModule(this))
                .networkModule(new NetworkModule())
                .utilityModule(new UtilityModule())
                .build();

//        OneSignal.startInit(this)
//                .setNotificationOpenedHandler(new PushNotifOpenedHandler())
//                .setNotificationReceivedHandler(new PushNotifReceivedHandler())
//                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
//                .init();


    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    protected void attachBaseContext(final Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
