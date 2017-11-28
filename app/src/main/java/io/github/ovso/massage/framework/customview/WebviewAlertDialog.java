package io.github.ovso.massage.framework.customview;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by jaeho on 2017. 11. 28
 */

public class WebviewAlertDialog extends BaseAlertDialogFragment {
  @Override protected boolean isNegativeButton() {
    return false;
  }

  @Override protected boolean isPositiveButton() {
    return false;
  }

  @Override protected boolean isDagger() {
    return false;
  }

  @Override protected void onActivityCreate(Bundle savedInstanceState) {

  }

  @Override protected boolean getAttatchRoot() {
    return false;
  }

  @Override protected int getLayoutResId() {
    return 0;
  }

  @Override protected ViewGroup getInflateRoot() {
    return null;
  }

  @Override protected boolean isDialogCancelable() {
    return false;
  }

  @Override protected int getTitle() {
    return 0;
  }

  @Override protected View.OnClickListener onPositiveClickListener() {
    return null;
  }

  @Override protected View.OnClickListener onNegativeClickListener() {
    return null;
  }
}
