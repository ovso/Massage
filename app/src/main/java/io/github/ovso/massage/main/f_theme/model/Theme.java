package io.github.ovso.massage.main.f_theme.model;

import android.text.TextUtils;
import com.google.firebase.database.IgnoreExtraProperties;
import io.github.ovso.massage.main.f_symptom.model.Symptom;
import io.github.ovso.massage.utils.Language;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Created by jaeho on 2017. 11. 27
 */

@IgnoreExtraProperties @EqualsAndHashCode(callSuper = false) @Getter @ToString
public class Theme {
  public int id;           // item id
  public String title;      // title
  public String title_en;
  public String url;       // youtube url or webpage url
  public boolean flag;     // webview javascriptenable flag
  public String video_id;  // youtube id
  public int rec;       // recommended count

  public static String getTitleByLanguage(String language, Theme item) {
    if (!TextUtils.isEmpty(language) && language.equals(Language.KO.get())) {
      return item.title;
    } else {
      return item.title_en;
    }
  }

}