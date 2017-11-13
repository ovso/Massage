package io.github.ovso.massage.theme;

import android.os.Bundle;
import io.github.ovso.massage.R;
import io.github.ovso.massage.framework.customview.BaseFragment;

/**
 * Created by jaeho on 2017. 10. 20
 */

public class ThemeFragment extends BaseFragment {
  @Override protected int getLayoutResID() {
    return R.layout.fragment_theme;
  }

  @Override protected void onActivityCreate(Bundle savedInstanceState) {

  }

  @Override protected boolean isDagger() {
    return false;
  }

  public static ThemeFragment newInstance() {
    ThemeFragment f = new ThemeFragment();
    return f;
  }
}
