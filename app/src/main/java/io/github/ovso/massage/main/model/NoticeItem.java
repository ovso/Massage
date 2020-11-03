package io.github.ovso.massage.main.model;

import android.text.TextUtils;
import com.google.firebase.database.IgnoreExtraProperties;
import de.psdev.licensesdialog.licenses.ApacheSoftwareLicense20;
import de.psdev.licensesdialog.licenses.License;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@IgnoreExtraProperties @EqualsAndHashCode(callSuper = false) @Getter @ToString
public class NoticeItem {

  public String name;
  public String license;
  public String url;
  public String copyright;

  public static License getLicense(String license) {
    if (!TextUtils.isEmpty(license)) {
      if (license.equals("apache")) {
        return new ApacheSoftwareLicense20();
      } else {
        return null;
      }
    } else {
      return null;
    }
  }
}
