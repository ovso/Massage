package io.github.ovso.massage.symptom;

import android.os.Bundle;
import io.github.ovso.massage.R;
import io.github.ovso.massage.framework.customview.BaseFragment;

/**
 * Created by jaeho on 2017. 10. 20
 */

public class SymptomFragment extends BaseFragment {
  @Override protected int getLayoutResID() {
    return R.layout.fragment_symptom;
  }

  @Override protected void onActivityCreate(Bundle savedInstanceState) {

  }

  @Override protected boolean isDagger() {
    return false;
  }

  public static SymptomFragment newInstance() {
    SymptomFragment f = new SymptomFragment();
    return f;
  }
}
