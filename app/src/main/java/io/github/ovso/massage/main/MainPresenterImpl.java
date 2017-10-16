package io.github.ovso.massage.main;

import android.os.Bundle;
import hugo.weaving.DebugLog;
import javax.inject.Inject;

/**
 * Created by jaeho on 2017. 10. 16
 */

public class MainPresenterImpl implements MainPresenter {

  private MainPresenter.View view;

  @Inject MainPresenterImpl(MainPresenter.View view) {
    this.view = view;
  }

  @DebugLog @Override public void onCreate(Bundle savedInstanceState) {

  }
}
