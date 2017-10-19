package io.github.ovso.massage.massage;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import dagger.android.support.AndroidSupportInjection;
import io.github.ovso.massage.R;
import io.github.ovso.massage.framework.customview.BaseFragment;

/**
 * Created by jaeho on 2017. 10. 20..
 */

public class MassageFragment extends BaseFragment {
  @Override protected int getLayoutResID() {
    return R.layout.fragment_massage;
  }

  @Override protected void onActivityCreate(Bundle savedInstanceState) {

  }

  @Override public void onAttach(Context context) {
    AndroidSupportInjection.inject(this);
    super.onAttach(context);
  }

  public static Fragment newInstance() {
    MassageFragment f = new MassageFragment();
    return f;
  }
}
