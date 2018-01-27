package io.github.ovso.massage.main.f_acupoints.a_images;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import butterknife.BindView;
import io.github.ovso.massage.R;
import io.github.ovso.massage.framework.customview.BaseActivity;
import javax.inject.Inject;

/**
 * Created by jaeho on 2018. 1. 25
 */

public class ImagesActivity extends BaseActivity implements ImagesPresenter.View {
  @BindView(R.id.recyclerview) RecyclerView recyclerView;
  @Inject ImagesPresenter presenter;

  @Override protected int getLayoutResId() {
    return R.layout.activity_images2;
  }

  @Override protected boolean isDagger() {
    return true;
  }

  @Override protected void onCreated(Bundle savedInstanceState) {
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    presenter.onCreated(getIntent());
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    finish();
    return true;
  }

  @Override public void setRecyclerView() {
    GridLayoutManager layout = new GridLayoutManager(getApplicationContext(), 2);
    recyclerView.setLayoutManager(layout);
  }
}
