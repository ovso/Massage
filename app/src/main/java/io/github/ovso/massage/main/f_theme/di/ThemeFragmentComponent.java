package io.github.ovso.massage.main.f_theme.di;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import io.github.ovso.massage.main.f_theme.ThemeFragment;

/**
 * Created by jaeho on 2017. 10. 20
 */

@Subcomponent(modules = ThemeFragmentModule.class) public interface ThemeFragmentComponent
    extends AndroidInjector<ThemeFragment> {

  @Subcomponent.Builder abstract class Builder extends AndroidInjector.Builder<ThemeFragment> {
  }
}
