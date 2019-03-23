package com.hearatale.bw2000;

import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.hearatale.bw2000.data.AppDataManager;
import com.hearatale.bw2000.util.Config;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import io.fabric.sdk.android.Fabric;

public class Application extends android.app.Application {

    public static android.content.Context Context;

    @Override
    public void onCreate() {
        super.onCreate();

        Context = this;

        Logger.addLogAdapter(new AndroidLogAdapter() {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });

        if (Config.PRODUCTION) {
            Fabric.with(this, new Crashlytics());
        }
    }
}
