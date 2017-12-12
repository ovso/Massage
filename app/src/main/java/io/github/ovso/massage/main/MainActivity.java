package io.github.ovso.massage.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.widget.FrameLayout;
import android.widget.TextView;
import butterknife.BindView;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import de.psdev.licensesdialog.LicensesDialog;
import de.psdev.licensesdialog.model.Notices;
import io.github.ovso.massage.R;
import io.github.ovso.massage.f_acupoints.AcupointsFragment;
import io.github.ovso.massage.f_symptom.SymptomFragment;
import io.github.ovso.massage.f_theme.ThemeFragment;
import io.github.ovso.massage.framework.SystemUtility;
import io.github.ovso.massage.framework.customview.BaseActivity;
import io.github.ovso.massage.framework.customview.BottomNavigationViewBehavior;
import io.github.ovso.massage.framework.listener.OnMessageListener;
import javax.inject.Inject;

public class MainActivity extends BaseActivity
    implements MainPresenter.View, HasSupportFragmentInjector {

  @Inject MainPresenter presenter;
  @BindView(R.id.fragment_container) FrameLayout fragmentContainer;
  @BindView(R.id.bottom_navigation_view) BottomNavigationView bottomNavigationView;
  @BindView(R.id.navigation_view) NavigationView navigationView;

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
    bottomNavigationView.setOnNavigationItemSelectedListener(
        item -> presenter.onBottomNavItemSelected(item.getItemId()));
    CoordinatorLayout.LayoutParams layoutParams =
        (CoordinatorLayout.LayoutParams) bottomNavigationView.getLayoutParams();
    layoutParams.setBehavior(new BottomNavigationViewBehavior());

    TextView versionTextView = navigationView.getHeaderView(0).findViewById(R.id.version_textview);
    versionTextView.setText(SystemUtility.getVersionName(getApplicationContext()));
  }

  @Override public void closeDrawer() {
    drawer.closeDrawer(GravityCompat.START);
  }

  @Override public void showSymptomFragment(OnMessageListener listener) {
    Bundle bundle = new Bundle();
    bundle.putSerializable("message", listener);

    getSupportFragmentManager().beginTransaction()
        .setCustomAnimations(R.animator.enter_animation, R.animator.exit_animation,
            R.animator.enter_animation, R.animator.exit_animation)
        .replace(R.id.fragment_container, SymptomFragment.newInstance(bundle))
        .commit();
  }

  @Override public void showThemeFrgament(OnMessageListener listener) {
    Bundle bundle = new Bundle();
    bundle.putSerializable("message", listener);

    getSupportFragmentManager().beginTransaction()
        .setCustomAnimations(R.animator.enter_animation, R.animator.exit_animation,
            R.animator.enter_animation, R.animator.exit_animation)
        .replace(R.id.fragment_container, ThemeFragment.newInstance(bundle))
        .commit();
  }

  @Override public void showAcupoints(OnMessageListener listener) {
    Bundle bundle = new Bundle();
    bundle.putSerializable("message", listener);

    getSupportFragmentManager().beginTransaction()
        .setCustomAnimations(R.animator.enter_animation, R.animator.exit_animation,
            R.animator.enter_animation, R.animator.exit_animation)
        .replace(R.id.fragment_container, AcupointsFragment.newInstance(bundle))
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
}