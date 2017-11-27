package io.github.ovso.massage.main;

import android.os.Bundle;
import android.support.annotation.IdRes;
import io.github.ovso.massage.R;
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
    view.showSymptomFragment();
  }

  @Override public boolean onNavItemSelected(int itemId) {

    view.closeDrawer();
    return true;
  }

  @Override public boolean onBottomNavItemSelected(@IdRes int itemId) {
    switch (itemId) {
      case R.id.action_symptom:
        view.showSymptomFragment();
        break;
      case R.id.action_theme:
        view.showThemeFrgament();
        break;
      case R.id.action_acupoints:
        view.showAcupoints();
        break;
    }
    return true;
  }

  @Override public void onBackPressed(boolean isDrawerOpen) {
    if (isDrawerOpen) {
      view.closeDrawer();
    } else {
      view.finish();
    }
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
