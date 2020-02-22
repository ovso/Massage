package io.github.ovso.massage;

import android.app.Application;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import io.github.ovso.massage.framework.SystemUtils;
import io.github.ovso.massage.utils.AppInitUtils;
import lombok.Getter;

public class App extends DaggerApplication {
    @Getter
    private static boolean debug;
    @Getter
    public static Application instance = null;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        debug = SystemUtils.isDebuggable(this);
        AppInitUtils.crashlytics(this, debug);
        AppInitUtils.timer();
        AppInitUtils.ads(this);
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().application(this).build();
    }
}