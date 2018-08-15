package com.dailiv;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.dailiv.internal.injector.component.ApplicationComponent;
import com.dailiv.internal.injector.component.DaggerApplicationComponent;
import com.dailiv.internal.injector.module.ApplicationModule;
import com.dailiv.internal.injector.module.NetworkModule;
import com.dailiv.internal.injector.module.UtilityModule;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

/**
 * Created by aldo on 3/1/18.
 */

public class App extends Application {

    private static GoogleAnalytics sAnalytics;
    private static Tracker sTracker;

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

        sAnalytics = GoogleAnalytics.getInstance(this);

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

    /**
     * Gets the default {@link Tracker} for this {@link Application}.
     * @return tracker
     */
    synchronized public Tracker getDefaultTracker() {
        // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
        if (sTracker == null) {
            sTracker = sAnalytics.newTracker(R.xml.global_tracker);
        }

        return sTracker;
    }
}
