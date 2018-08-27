package io.github.ovso.massage.utils;

import android.content.Context;
import com.crashlytics.android.Crashlytics;
import com.google.android.gms.ads.MobileAds;
import io.fabric.sdk.android.Fabric;
import io.github.ovso.massage.BuildConfig;
import io.github.ovso.massage.Security;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import timber.log.Timber;

public class AppInitUtils {

  private AppInitUtils() {

  }

  public static void crashlytics(Context context, boolean debug) {
    if (!debug) {
      final Fabric fabric = new Fabric.Builder(context)
          .kits(new Crashlytics())
          .debuggable(true)           // Enables Crashlytics debugger
          .build();
      Fabric.with(fabric);
    }
  }

  public static void timer() {
    if (BuildConfig.DEBUG) {
      Timber.plant(new Timber.DebugTree());
    }
  }

  public static RealmConfiguration realm(Context context) {
    Realm.init(context);
    return new RealmConfiguration.Builder().schemaVersion(0).build();
  }

  public static void ads(Context context) {
    MobileAds.initialize(context, Security.ADMOB_APP_ID.getValue());
  }
}
