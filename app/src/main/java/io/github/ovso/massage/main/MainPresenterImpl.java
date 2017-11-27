package io.github.ovso.massage.main;

import android.os.Bundle;
import javax.inject.Inject;

/**
 * Created by jaeho on 2017. 10. 16
 */

public class MainPresenterImpl implements MainPresenter {

  private MainPresenter.View view;

  @Inject MainPresenterImpl(MainPresenter.View view) {
    this.view = view;
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    view.setListener();
  }

    /*
    if (id == R.id.nav_camera) {
      // Handle the camera action
    } else if (id == R.id.nav_gallery) {

    } else if (id == R.id.nav_slideshow) {

    } else if (id == R.id.nav_manage) {

    } else if (id == R.id.nav_share) {

    } else if (id == R.id.nav_send) {

    }
    */

}