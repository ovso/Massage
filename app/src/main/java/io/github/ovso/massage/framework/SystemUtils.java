package io.github.ovso.massage.framework;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import java.util.Locale;

public class SystemUtils {

  public static String getVersionName(Context context) {
    String versionName;
    try {
      PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
      versionName = info.versionName;
    } catch (PackageManager.NameNotFoundException e) {
      e.printStackTrace();
      versionName = "0.0.1";
    }
    return versionName;
  }

  public static boolean isDebuggable(Context context) {
    boolean debuggable;

    PackageManager pm = context.getPackageManager();
    try {
      ApplicationInfo appinfo = pm.getApplicationInfo(context.getPackageName(), 0);
      debuggable = (0 != (appinfo.flags & ApplicationInfo.FLAG_DEBUGGABLE));
    } catch (PackageManager.NameNotFoundException e) {
      /* debuggable variable will remain false */
      debuggable = false;
    }

    return debuggable;
  }

  public static String getLanguage(Context context) {
    Locale systemLocale = context.getResources().getConfiguration().locale;
    return systemLocale.getLanguage();
  }
}
