package io.github.ovso.massage.acupoints;

import android.os.Bundle;
import io.github.ovso.massage.R;
import io.github.ovso.massage.framework.customview.BaseFragment;

/**
 * Created by jaeho on 2017. 10. 20
 */

public class AcupointsFragment extends BaseFragment {
  @Override protected int getLayoutResID() {
    return R.layout.fragment_acupoints;
  }

  @Override protected void onActivityCreate(Bundle savedInstanceState) {

  }

  @Override protected boolean isDagger() {
    return false;
  }

  public static AcupointsFragment newInstance() {
    AcupointsFragment f = new AcupointsFragment();
    return f;
  }
}
