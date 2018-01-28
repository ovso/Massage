package io.github.ovso.massage.main.f_acupoints.model;

import java.io.Serializable;
import lombok.Getter;
import lombok.ToString;

/**
 * Created by jaeho on 2017. 12. 28
 */

@ToString @Getter public class Documents implements Serializable {
  private String collection;
  private String datetime;
  private int height;
  private int width;
  private String thumbnail_url;
  private String image_url;
  private String display_sitename;
  private String doc_url;
}