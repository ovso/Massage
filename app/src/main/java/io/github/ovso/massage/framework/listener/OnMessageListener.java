package io.github.ovso.massage.framework.listener;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import java.io.Serializable;

/**
 * Created by jaeho on 2017. 12. 12
 */

public interface OnMessageListener extends Serializable {
  public void onMessage(@StringRes int resId);
  public void onMessage(@NonNull String msg);
}
