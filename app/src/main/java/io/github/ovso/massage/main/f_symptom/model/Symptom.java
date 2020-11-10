package io.github.ovso.massage.main.f_symptom.model;

import android.text.TextUtils;
import io.github.ovso.massage.utils.Language;
public class Symptom {
  public int id;           // item id
  public String title;      // title
  public String title_en;
  public String url;       // youtube url or webpage url
  public boolean flag;     // webview javascriptenable flag
  public String video_id;  // youtube id
  public int rec;       // recommended count

  public static String getTitleByLanguage(String language, Symptom item) {
    if (!TextUtils.isEmpty(language) && language.equals(Language.KO.get())) {
      return item.title;
    } else {
      return item.title_en;
    }
  }

}