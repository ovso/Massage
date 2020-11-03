package io.github.ovso.massage.framework.customview;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.github.ovso.massage.R;

/**
 * Created by jaeho on 2017. 10. 19
 */

public abstract class BaseActivity extends AppCompatActivity {
  private Unbinder unbinder;
  protected @BindView(R.id.toolbar)
  Toolbar toolbar;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
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

  protected void onCreated(Bundle savedInstanceState) {

  }
}
