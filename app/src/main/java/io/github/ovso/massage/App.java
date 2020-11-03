package io.github.ovso.massage;

import android.app.Application;

import io.github.ovso.massage.framework.SystemUtils;
import io.github.ovso.massage.utils.AppInitUtils;

public class App extends Application {
    private static boolean debug;
    public static Application instance = null;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        debug = SystemUtils.isDebuggable(this);
        AppInitUtils.timer();
        AppInitUtils.ads(this);
    }
}