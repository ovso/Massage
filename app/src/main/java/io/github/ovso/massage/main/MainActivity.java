package io.github.ovso.massage.main;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import butterknife.BindView;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import io.github.ovso.massage.R;
import io.github.ovso.massage.framework.customview.BaseActivity;
import javax.inject.Inject;

public class MainActivity extends BaseActivity
    implements NavigationView.OnNavigationItemSelectedListener, MainPresenter.View,
    HasSupportFragmentInjector {

  @BindView(R.id.navigation_view) NavigationView navigationView;
  @BindView(R.id.viewpager) ViewPager viewPager;
  @BindView(R.id.tabs) TabLayout tabLayout;
  @Inject MainPresenter presenter;

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

    navigationView.setNavigationItemSelectedListener(this);
  }

  @Override protected int getLayoutResID() {
    return R.layout.activity_main;
  }

  @Override public void onBackPressed() {
    presenter.onBackPressed(drawer.isDrawerOpen(GravityCompat.START));
  }

  @SuppressWarnings("StatementWithEmptyBody") @Override
  public boolean onNavigationItemSelected(MenuItem item) {
    drawer.closeDrawer(GravityCompat.START);
    return presenter.onNavigationItemSelected(item.getItemId());
  }

  @Override public void closeDrawer() {
    drawer.closeDrawer(GravityCompat.START);
  }

  @Inject MassageFragmentPagerAdapter adapter;

  @Override public void setViewPager() {
    viewPager.setAdapter(adapter);
  }

  @Override public void setTabLayout() {
    tabLayout.setupWithViewPager(viewPager);
    tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
    tabLayout.setTabMode(TabLayout.GRAVITY_FILL);
    tabLayout.addOnTabSelectedListener(onTabSelectedListener);
    tabLayout.getTabAt(0).setText("발");
    tabLayout.getTabAt(1).setText("머리-목");
    tabLayout.getTabAt(2).setText("등");
    tabLayout.getTabAt(3).setText("다리");
    tabLayout.getTabAt(4).setText("허벅지");
  }

  private TabLayout.OnTabSelectedListener onTabSelectedListener =
      new TabLayout.OnTabSelectedListener() {
        @Override public void onTabSelected(TabLayout.Tab tab) {
          presenter.onTabSelected(tab.getPosition());
        }

        @Override public void onTabUnselected(TabLayout.Tab tab) {
        }

        @Override public void onTabReselected(TabLayout.Tab tab) {
          presenter.onTabReselected(tab.getPosition());
        }
      };

  @Inject DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

  @Override public AndroidInjector<Fragment> supportFragmentInjector() {
    return fragmentDispatchingAndroidInjector;
  }
}
