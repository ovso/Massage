package io.github.ovso.massage.main;

import android.annotation.SuppressLint;
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

import com.google.android.gms.oss.licenses.OssLicensesMenuActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import butterknife.BindView;
import io.github.ovso.massage.ad.MyAdView;
import io.github.ovso.massage.data.NativeAdsDialog2;
import io.github.ovso.massage.framework.SystemUtils;
import io.github.ovso.massage.framework.customview.BaseActivity;
import io.github.ovso.massage.main.f_acupoints.AcupointsFragment;
import io.github.ovso.massage.main.f_symptom.SymptomFragment;
import io.github.ovso.massage.main.f_theme.ThemeFragment;

import static io.github.ovso.massage.R.animator;
import static io.github.ovso.massage.R.id;
import static io.github.ovso.massage.R.id.ad_container;
import static io.github.ovso.massage.R.id.bottom_navigation_view;
import static io.github.ovso.massage.R.id.drawer_layout;
import static io.github.ovso.massage.R.id.fragment_container;
import static io.github.ovso.massage.R.id.navigation_view;
import static io.github.ovso.massage.R.layout;
import static io.github.ovso.massage.R.string;

@SuppressLint("NonConstantResourceId")
public class MainActivity extends BaseActivity
        implements MainPresenter.View {

    @BindView(drawer_layout)
    DrawerLayout drawer;
    @BindView(fragment_container)
    FrameLayout fragmentContainer;
    @BindView(bottom_navigation_view)
    BottomNavigationView bottomNavigationView;
    @BindView(navigation_view)
    NavigationView navigationView;
    @BindView(ad_container)
    ViewGroup adContainer;

    private final MainPresenter presenter = new MainPresenterImpl(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.onCreate(savedInstanceState);
    }

    @Override
    public void setListener() {
        ActionBarDrawerToggle toggle =
                new ActionBarDrawerToggle(this, drawer, toolbar, string.navigation_drawer_open,
                        string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(
                item -> presenter.onNavItemSelected(item.getItemId()));
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            boolean isChecked = bottomNavigationView.getMenu().findItem(item.getItemId()).isChecked();
            return presenter.onBottomNavItemSelected(item.getItemId(), isChecked);
        });
        TextView versionTextView = navigationView.getHeaderView(0).findViewById(id.version_textview);
        versionTextView.setText(SystemUtils.getVersionName(getApplicationContext()));
    }

    @Override
    public void closeDrawer() {
        drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    public void showSymptomFragment() {
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(animator.enter_animation, animator.exit_animation,
                        animator.enter_animation, animator.exit_animation)
                .replace(fragment_container, SymptomFragment.newInstance())
                .commit();
    }

    @Override
    public void showThemeFrgament() {
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(animator.enter_animation, animator.exit_animation,
                        animator.enter_animation, animator.exit_animation)
                .replace(fragment_container, ThemeFragment.newInstance())
                .commit();
    }

    @Override
    public void showAcupoints() {
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(animator.enter_animation, animator.exit_animation,
                        animator.enter_animation, animator.exit_animation)
                .replace(fragment_container, AcupointsFragment.newInstance())
                .commit();
    }

    @Override
    public void navigateToOssLicensesMenu() {
        startActivity(new Intent(this, OssLicensesMenuActivity.class));
    }

    @Override
    protected int getLayoutResId() {
        return layout.activity_main;
    }


    @Override
    public void onBackPressed() {
        presenter.onBackPressed(drawer.isDrawerOpen(GravityCompat.START));
    }

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
                .setNeutralButton(string.review, (dialog, which) -> {
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
    public void showNativeAdsDialog() {
        new NativeAdsDialog2(this)
                .setUnitId(getString(string.ads_native_unit_id))
                .setPositiveButton(string.exit, (dialog, i) -> {
                    dialog.dismiss();
                    finish();
                })
                .setNeutralButton(string.review, (dialog, i) -> {
                    dialog.dismiss();
                    navigateToStore();
                    finish();
                })
                .setTitle(string.do_you_want_to_quit)
                .show();

    }

    @Override
    public void showAd() {
        adContainer.addView(MyAdView.getAdmobAdView(getApplicationContext()));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}