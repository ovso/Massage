package io.github.ovso.massage.di;

import android.app.Activity;
import dagger.Binds;
import dagger.Module;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;
import io.github.ovso.massage.main.MainActivity;
import io.github.ovso.massage.main.MainActivityComponent;

/**
 * Created by jaeho on 2017. 10. 16
 */

@Module public abstract class ActivityBuilder {
  @Binds @IntoMap @ActivityKey(MainActivity.class)
  abstract AndroidInjector.Factory<? extends Activity> bindMainActivity(
      MainActivityComponent.Builder builder);
}
