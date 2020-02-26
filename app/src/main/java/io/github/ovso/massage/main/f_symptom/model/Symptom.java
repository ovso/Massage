package io.github.ovso.massage.main.f_symptom.model;

import android.text.TextUtils;
import com.google.firebase.database.IgnoreExtraProperties;
import io.github.ovso.massage.utils.Language;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Created by jaeho on 2017. 11. 27
 */

@IgnoreExtraProperties @EqualsAndHashCode(callSuper = false) @Getter @ToString
public class Symptom {
  private int id;           // item id
  public String title;      // title
  private String title_en;
  public String url;       // youtube url or webpage url
  public boolean flag;     // webview javascriptenable flag
  private String video_id;  // youtube id
  private int rec;       // recommended count

  public static String getTitleByLanguage(String language, Symptom item) {
    if (!TextUtils.isEmpty(language) && language.equals(Language.KO.get())) {
      return item.title;
    } else {
      return item.title_en;
    }
  }

}