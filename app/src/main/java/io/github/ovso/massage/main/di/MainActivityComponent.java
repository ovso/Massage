package io.github.ovso.massage.main.di;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import io.github.ovso.massage.main.f_acupoints.di.AcupointsFragmentProvider;
import io.github.ovso.massage.main.f_symptom.di.SymptomFragmentProvider;
import io.github.ovso.massage.main.f_theme.di.ThemeFragmentProvider;
import io.github.ovso.massage.main.MainActivity;

/**
 * Created by jaeho on 2017. 10. 16
 */

@Subcomponent(modules = {
    MainActivityModule.class, SymptomFragmentProvider.class, ThemeFragmentProvider.class,
    AcupointsFragmentProvider.class
}) public interface MainActivityComponent extends AndroidInjector<MainActivity> {

  @Subcomponent.Builder abstract class Builder extends AndroidInjector.Builder<MainActivity> {
  }
}
