package io.github.ovso.massage.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import javax.inject.Inject;

import butterknife.BindView;
import de.psdev.licensesdialog.LicensesDialog;
import de.psdev.licensesdialog.model.Notices;
import io.github.ovso.massage.R;
import io.github.ovso.massage.ad.MyAdView;
import io.github.ovso.massage.framework.SystemUtils;
import io.github.ovso.massage.framework.customview.BaseActivity;
import io.github.ovso.massage.main.f_acupoints.AcupointsFragment;
import io.github.ovso.massage.main.f_symptom.SymptomFragment;
import io.github.ovso.massage.main.f_theme.ThemeFragment;

public class MainActivity extends BaseActivity
        implements MainPresenter.View {
//    implements MainPresenter.View, HasSupportFragmentInjector {

    @Inject
    MainPresenter presenter;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.fragment_container)
    FrameLayout fragmentContainer;
    @BindView(R.id.bottom_navigation_view)
    BottomNavigationView bottomNavigationView;
    @BindView(R.id.navigation_view)
    NavigationView navigationView;
    @BindView(R.id.ad_container)
    ViewGroup adContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.onCreate(savedInstanceState);
    }

    @Override
    public void setListener() {
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
        //CoordinatorLayout.LayoutParams layoutParams =
        //    (CoordinatorLayout.LayoutParams) bottomNavigationView.getLayoutParams();
        //layoutParams.setBehavior(new BottomNavigationViewBehavior());

        TextView versionTextView = navigationView.getHeaderView(0).findViewById(R.id.version_textview);
        versionTextView.setText(SystemUtils.getVersionName(getApplicationContext()));
    }

    @Override
    public void closeDrawer() {
        drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    public void showSymptomFragment() {
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.animator.enter_animation, R.animator.exit_animation,
                        R.animator.enter_animation, R.animator.exit_animation)
                .replace(R.id.fragment_container, SymptomFragment.newInstance())
                .commit();
    }

    @Override
    public void showThemeFrgament() {
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.animator.enter_animation, R.animator.exit_animation,
                        R.animator.enter_animation, R.animator.exit_animation)
                .replace(R.id.fragment_container, ThemeFragment.newInstance())
                .commit();
    }

    @Override
    public void showAcupoints() {
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.animator.enter_animation, R.animator.exit_animation,
                        R.animator.enter_animation, R.animator.exit_animation)
                .replace(R.id.fragment_container, AcupointsFragment.newInstance())
                .commit();
    }

    @Override
    public void showLicensesDialog(Notices notices) {
        new LicensesDialog.Builder(this).setNotices(notices).setIncludeOwnLicense(true).build().show();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected boolean isDagger() {
        return true;
    }

    @Override
    public void onBackPressed() {
        presenter.onBackPressed(drawer.isDrawerOpen(GravityCompat.START));
    }

/*
  @Inject
  DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

  @Override public AndroidInjector<Fragment> supportFragmentInjector() {
    return fragmentDispatchingAndroidInjector;
  }
*/

    @Override
    public void showMessage(int resId) {
        Toast.makeText(this, resId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMessage(@NonNull String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showHelpAlert(int resId) {
        new AlertDialog.Builder(this).setMessage(resId)
                .setPositiveButton(android.R.string.ok, ((dialog, which) -> dialog.dismiss()))
                .setNeutralButton(R.string.review, (dialog, which) -> {
                    dialog.dismiss();
                    navigateToStore();
                })
                .show();
    }

    private void navigateToStore() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(
                "https://play.google.com/store/apps/details?id=io.github.ovso.massage"));
        intent.setPackage("com.android.vending");
        startActivity(intent);
    }

    @Override
    public void showHelpAlert(String msg) {
        new AlertDialog.Builder(this).setMessage(msg)
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }

    @Override
    public void changeTheme() {
        setTheme(R.style.AppTheme_NoActionBar);
    }

    @Override
    public void showAd() {
        adContainer.addView(MyAdView.getAdmobAdView(getApplicationContext()));
    }
}