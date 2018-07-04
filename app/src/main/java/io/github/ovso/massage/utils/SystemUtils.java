package io.github.ovso.massage.utils;

import android.content.Context;
import java.util.Locale;

public final class SystemUtils {

  private SystemUtils() {
  }

  public static String getLanguage(Context context) {
    Locale systemLocale = context.getResources().getConfiguration().locale;
    return systemLocale.getLanguage();
  }
}
