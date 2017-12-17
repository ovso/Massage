package io.github.ovso.massage.main;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import de.psdev.licensesdialog.model.Notices;

/**
 * Created by jaeho on 2017. 10. 16
 */

public interface MainPresenter {

  void onCreate(Bundle savedInstanceState);

  boolean onNavItemSelected(@IdRes int itemId);

  boolean onBottomNavItemSelected(@IdRes int itemId);

  void onBackPressed(boolean isDrawerOpen);

  interface View {

    void setListener();

    void closeDrawer();

    void finish();

    void showSymptomFragment();

    void showThemeFrgament();

    void showAcupoints();

    void showLicensesDialog(Notices notices);

    void showMessage(@StringRes int resId);

    void showMessage(String msg);

    void showMessageAlert(@StringRes int resId);

    void showAd();
  }
}
