package io.github.ovso.massage.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import butterknife.BindView;
import com.fsn.cauly.CaulyAdInfo;
import com.fsn.cauly.CaulyAdInfoBuilder;
import com.fsn.cauly.CaulyAdView;
import com.fsn.cauly.CaulyAdViewListener;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import de.psdev.licensesdialog.LicensesDialog;
import de.psdev.licensesdialog.model.Notices;
import hugo.weaving.DebugLog;
import io.github.ovso.massage.R;
import io.github.ovso.massage.common.Security;
import io.github.ovso.massage.framework.SystemUtility;
import io.github.ovso.massage.framework.customview.BaseActivity;
import io.github.ovso.massage.framework.customview.BottomNavigationViewBehavior;
import io.github.ovso.massage.main.f_acupoints.AcupointsFragment;
import io.github.ovso.massage.main.f_symptom.SymptomFragment;
import io.github.ovso.massage.main.f_theme.ThemeFragment;
import javax.inject.Inject;

public class MainActivity extends BaseActivity
    implements MainPresenter.View, HasSupportFragmentInjector {

  @Inject MainPresenter presenter;
  @BindView(R.id.drawer_layout) DrawerLayout drawer;
  @BindView(R.id.fragment_container) FrameLayout fragmentContainer;
  @BindView(R.id.bottom_navigation_view) BottomNavigationView bottomNavigationView;
  @BindView(R.id.navigation_view) NavigationView navigationView;
  @BindView(R.id.ad_container) ViewGroup adContainer;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    presenter.onCreate(savedInstanceState);
  }

  @Override public void setListener() {
    ActionBarDrawerToggle toggle =
        new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open,
            R.string.navigation_drawer_close);
    drawer.addDrawerListener(toggle);
    toggle.syncState();
    navigationView.setNavigationItemSelectedListener(
        item -> presenter.onNavItemSelected(item.getItemId()));
    bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
      boolean isChecked = bottomNavigationView.getMenu().findItem(item.getItemId()).isChecked();
      return presenter.onBottomNavItemSelected(item.getItemId(), isChecked);
    });
    CoordinatorLayout.LayoutParams layoutParams =
        (CoordinatorLayout.LayoutParams) bottomNavigationView.getLayoutParams();
    layoutParams.setBehavior(new BottomNavigationViewBehavior());

    TextView versionTextView = navigationView.getHeaderView(0).findViewById(R.id.version_textview);
    versionTextView.setText(SystemUtility.getVersionName(getApplicationContext()));

    //bottomNavigationView.getMenu().removeItem(R.id.action_acupoints);
  }

  @Override public void closeDrawer() {
    drawer.closeDrawer(GravityCompat.START);
  }

  @Override public void showSymptomFragment() {
    getSupportFragmentManager().beginTransaction()
        .setCustomAnimations(R.animator.enter_animation, R.animator.exit_animation,
            R.animator.enter_animation, R.animator.exit_animation)
        .replace(R.id.fragment_container, SymptomFragment.newInstance())
        .commit();
  }

  @Override public void showThemeFrgament() {
    getSupportFragmentManager().beginTransaction()
        .setCustomAnimations(R.animator.enter_animation, R.animator.exit_animation,
            R.animator.enter_animation, R.animator.exit_animation)
        .replace(R.id.fragment_container, ThemeFragment.newInstance())
        .commit();
  }

  @Override public void showAcupoints() {
    getSupportFragmentManager().beginTransaction()
        .setCustomAnimations(R.animator.enter_animation, R.animator.exit_animation,
            R.animator.enter_animation, R.animator.exit_animation)
        .replace(R.id.fragment_container, AcupointsFragment.newInstance())
        .commit();
  }

  @Override public void showLicensesDialog(Notices notices) {
    new LicensesDialog.Builder(this).setNotices(notices).setIncludeOwnLicense(true).build().show();
  }

  @Override protected int getLayoutResId() {
    return R.layout.activity_main;
  }

  @Override protected boolean isDagger() {
    return true;
  }

  @Override public void onBackPressed() {
    presenter.onBackPressed(drawer.isDrawerOpen(GravityCompat.START));
  }

  @Inject DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

  @Override public AndroidInjector<Fragment> supportFragmentInjector() {
    return fragmentDispatchingAndroidInjector;
  }

  @Override public void showMessage(int resId) {
    Snackbar.make(drawer, resId, Snackbar.LENGTH_SHORT).show();
  }

  @Override public void showMessage(@NonNull String msg) {
    Snackbar.make(drawer, msg, Snackbar.LENGTH_SHORT).show();
  }

  @Override public void showHelpAlert(int resId) {
    new AlertDialog.Builder(this).setMessage(resId)
        .setPositiveButton(android.R.string.ok, null)
        .show();
  }

  @Override public void showHelpAlert(String msg) {
    new AlertDialog.Builder(this).setMessage(msg)
        .setPositiveButton(android.R.string.ok, null)
        .show();
  }

  @Override public void showAd() {
    CaulyAdView view;
    CaulyAdInfo info = new CaulyAdInfoBuilder(Security.CAULY_APP_CODE.getValue()).effect(
        CaulyAdInfo.Effect.Circle.toString()).build();
    view = new CaulyAdView(this);
    view.setAdInfo(info);
    view.setAdViewListener(new CaulyAdViewListener() {
      @DebugLog @Override public void onReceiveAd(CaulyAdView caulyAdView, boolean b) {

      }

      @DebugLog @Override
      public void onFailedToReceiveAd(CaulyAdView caulyAdView, int i, String s) {

      }

      @DebugLog @Override public void onShowLandingScreen(CaulyAdView caulyAdView) {

      }

      @DebugLog @Override public void onCloseLandingScreen(CaulyAdView caulyAdView) {

      }
    });

    adContainer.addView(view);
  }
}