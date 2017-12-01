package io.github.ovso.massage.main;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import io.github.ovso.massage.f_symptom.di.SymptomFragmentProvider;
import io.github.ovso.massage.f_theme.di.ThemeFragmentProvider;

/**
 * Created by jaeho on 2017. 10. 16
 */

@Subcomponent(modules = { MainActivityModule.class, SymptomFragmentProvider.class, ThemeFragmentProvider.class })
public interface MainActivityComponent extends AndroidInjector<MainActivity> {

  @Subcomponent.Builder abstract class Builder extends AndroidInjector.Builder<MainActivity> {
  }
}
