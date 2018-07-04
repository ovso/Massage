package io.github.ovso.massage.main.model;

import android.text.TextUtils;
import com.google.firebase.database.IgnoreExtraProperties;
import io.github.ovso.massage.utils.Language;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Created by jaeho on 2017. 12. 17
 */
@IgnoreExtraProperties @EqualsAndHashCode(callSuper = false) @Getter @ToString public class Help {
  private String msg;
  private String msg_en;

  public static String getMsgByLanguage(String language, Help help) {
    if (!TextUtils.isEmpty(language) && language.equals(Language.KO.get())) {
      return "\n\n" + help.getMsg();
    } else {
      return "\n\n" + help.getMsg_en();
    }
  }
}
