package io.github.ovso.massage.main;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import butterknife.BindView;
import io.github.ovso.massage.R;
import io.github.ovso.massage.framework.BaseActivity;
import javax.inject.Inject;

public class MainActivity extends BaseActivity
    implements NavigationView.OnNavigationItemSelectedListener, MainPresenter.View {

  @BindView(R.id.navigation_view) NavigationView navigationView;

  @Inject MainPresenter presenter;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    presenter.onCreate(savedInstanceState);
    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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
}
class PagerAdapter extends FragmentPagerAdapter {

  public PagerAdapter(FragmentManager fm) {
    super(fm);
  }

  @Override public Fragment getItem(int position) {
    return null;
  }

  @Override public int getCount() {
    return 0;
  }
}