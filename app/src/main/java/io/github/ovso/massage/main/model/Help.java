package io.github.ovso.massage.main.model;

import com.google.firebase.database.IgnoreExtraProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Created by jaeho on 2017. 12. 17
 */
@IgnoreExtraProperties @EqualsAndHashCode(callSuper = false) @Getter @ToString public class Help {
  private String msg;
}
