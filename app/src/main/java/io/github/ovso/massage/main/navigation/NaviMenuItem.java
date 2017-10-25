package io.github.ovso.massage.main.navigation;

import android.support.annotation.DrawableRes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * Created by jaeho on 2017. 10. 25
 */

@ToString @AllArgsConstructor public class NaviMenuItem {
  @Getter private String title;
  @Getter private int itemId;
  @Getter private @DrawableRes int icon;
}
