package io.github.ovso.massage.f_symptom.model;

import com.google.firebase.database.IgnoreExtraProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by jaeho on 2017. 11. 27
 */

@IgnoreExtraProperties @EqualsAndHashCode(callSuper = false) @Getter @ToString
public class Symptom {
  public String title;
  private String url;   // youtube url or webpage url
  private int type;     // video or webpage
  private int id;     // content id
  @Setter private int rec;      // ..
  @Setter private boolean flag; // webview javascriptenable flag
}