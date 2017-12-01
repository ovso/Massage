package io.github.ovso.massage.f_theme.di;

import android.support.v4.app.Fragment;
import dagger.Binds;
import dagger.Module;
import dagger.android.AndroidInjector;
import dagger.android.support.FragmentKey;
import dagger.multibindings.IntoMap;
import io.github.ovso.massage.f_theme.ThemeFragment;

/**
 * Created by jaeho on 2017. 10. 20
 */

@Module public abstract class ThemeFragmentProvider {
  @Binds @IntoMap @FragmentKey(ThemeFragment.class)
  abstract AndroidInjector.Factory<? extends Fragment> provideThemeFragmentFactory(
      ThemeFragmentComponent.Builder builder);
}
