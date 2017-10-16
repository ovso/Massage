package io.github.ovso.massage.main;

import android.os.Bundle;

/**
 * Created by jaeho on 2017. 10. 16
 */

public interface MainPresenter {

  void onCreate(Bundle savedInstanceState);

  boolean onNavigationItemSelected(int id);

  void onBackPressed(boolean isDrawerOpen);

  interface View {

    void closeDrawer();

    void finish();
  }
}
