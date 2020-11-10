package io.github.ovso.massage.main.model;

import android.text.TextUtils;
import io.github.ovso.massage.utils.Language;
public class Help {
  public String msg;
  public String msg_en;

  public static String getMsgByLanguage(String language, Help help) {
    if (!TextUtils.isEmpty(language) && language.equals(Language.KO.get())) {
      return "\n\n" + help.msg;
    } else {
      return "\n\n" + help.msg_en;
    }
  }
}
