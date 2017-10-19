package io.github.ovso.massage.main;

import android.os.Bundle;
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
    view.setViewPager();
    view.setTabLayout();
  }

  @Override public boolean onNavigationItemSelected(int id) {
    if (id == R.id.nav_camera) {
      // Handle the camera action
    } else if (id == R.id.nav_gallery) {

    } else if (id == R.id.nav_slideshow) {

    } else if (id == R.id.nav_manage) {

    } else if (id == R.id.nav_share) {

    } else if (id == R.id.nav_send) {

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

  @Override public void onTabSelected(int position) {

  }

  @Override public void onTabReselected(int position) {

  }
}
