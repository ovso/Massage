package io.github.ovso.massage.massage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import io.github.ovso.massage.R;
import io.github.ovso.massage.framework.customview.BaseFragment;

/**
 * Created by jaeho on 2017. 10. 20
 */

public class MassageFragment extends BaseFragment {
  @Override protected int getLayoutResID() {
    return R.layout.fragment_massage;
  }

  @Override protected void onActivityCreate(Bundle savedInstanceState) {

  }

  @Override protected boolean isDagger() {
    return false;
  }

  public static Fragment newInstance() {
    MassageFragment f = new MassageFragment();
    return f;
  }
}