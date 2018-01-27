package io.github.ovso.massage.framework.customview;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.AndroidInjection;
import io.github.ovso.massage.R;

/**
 * Created by jaeho on 2017. 10. 19
 */

public abstract class BaseActivity extends AppCompatActivity {
  private Unbinder unbinder;
  protected @BindView(R.id.toolbar) Toolbar toolbar;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    if (isDagger()) AndroidInjection.inject(this);
    super.onCreate(savedInstanceState);
    setContentView(getLayoutResId());
    unbinder = ButterKnife.bind(this);
    setSupportActionBar(toolbar);
    setNavigationBarColor();
    onCreated(savedInstanceState);
  }

  private void setNavigationBarColor() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
    }
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    unbinder.unbind();
  }

  protected abstract int getLayoutResId();

  protected abstract boolean isDagger();

  protected void onCreated(Bundle savedInstanceState) {

  }
}
