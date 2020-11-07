package io.github.ovso.massage.main.f_acupoints.model;

import java.io.Serializable;

public class Documents implements Serializable {
  public String collection;
  public String datetime;
  public int height;
  public int width;
  public String thumbnail_url;
  public String image_url;
  public String display_sitename;
  public String doc_url;

  @Override
  public String toString() {
    return "Documents{" +
            "collection='" + collection + '\'' +
            ", datetime='" + datetime + '\'' +
            ", height=" + height +
            ", width=" + width +
            ", thumbnail_url='" + thumbnail_url + '\'' +
            ", image_url='" + image_url + '\'' +
            ", display_sitename='" + display_sitename + '\'' +
            ", doc_url='" + doc_url + '\'' +
            '}';
  }
}