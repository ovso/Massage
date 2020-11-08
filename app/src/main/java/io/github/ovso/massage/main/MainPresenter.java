package io.github.ovso.massage.main;

import android.os.Bundle;

import androidx.annotation.IdRes;
import androidx.annotation.StringRes;

/**
 * Created by jaeho on 2017. 10. 16
 */

public interface MainPresenter {

    void onCreate(Bundle savedInstanceState);

    boolean onNavItemSelected(@IdRes int itemId);

    void onBackPressed(boolean isDrawerOpen);

    boolean onBottomNavItemSelected(@IdRes int itemId, boolean isChecked);

    void onDestroy();

    interface View {

        void setListener();

        void closeDrawer();

        void showSymptomFragment();

        void showThemeFrgament();

        void showAcupoints();

        void navigateToOssLicensesMenu();

        void showMessage(@StringRes int resId);

        void showMessage(String msg);

        void showHelpAlert(@StringRes int resId);

        void showAd();

        void showNativeAdsDialog();
    }
}
