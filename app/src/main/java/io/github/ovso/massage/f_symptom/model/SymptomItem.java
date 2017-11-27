package io.github.ovso.massage.f_symptom.model;

import lombok.Getter;
import lombok.ToString;

/**
 * Created by jaeho on 2017. 11. 27
 */

@ToString @Getter public class SymptomItem {
  private String title;
  private int type;     // video or webpage
  private String date;  // upload date
  private String url;   // youtube url or webpage url
}