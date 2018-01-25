package io.github.ovso.massage.main.f_acupoints.a_images;

import android.os.Bundle;
import android.view.MenuItem;
import io.github.ovso.massage.R;
import io.github.ovso.massage.framework.customview.BaseActivity;
import timber.log.Timber;

/**
 * Created by jaeho on 2018. 1. 25
 */

public class ImagesActivity extends BaseActivity {
  @Override protected int getLayoutResId() {
    return R.layout.activity_images;
  }

  @Override protected boolean isDagger() {
    return false;
  }

  @Override protected void onCreated(Bundle savedInstanceState) {
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    if (getIntent().hasExtra("items")) {
      Timber.d("Items");
    }
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    finish();
    return true;
  }
}
