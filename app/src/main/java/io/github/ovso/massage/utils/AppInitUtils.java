package io.github.ovso.massage.utils;

import android.content.Context;

import com.google.android.gms.ads.MobileAds;

import io.github.ovso.massage.BuildConfig;
import timber.log.Timber;

public class AppInitUtils {

    private AppInitUtils() {

    }

    public static void timer() {
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    public static void ads(Context context) {
        MobileAds.initialize(context);
    }
}
