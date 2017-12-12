package io.github.ovso.massage.f_symptom.model;

import com.google.firebase.database.IgnoreExtraProperties;
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
  private String url;       // youtube url or webpage url
  private boolean flag;     // webview javascriptenable flag
  private String video_id;  // youtube id
  private int rec;       // recommended count
}