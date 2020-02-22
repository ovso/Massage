package io.github.ovso.massage.main.di;

import androidx.fragment.app.FragmentManager;

import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;
import io.github.ovso.massage.main.MainActivity;
import io.github.ovso.massage.main.MainPresenter;
import io.github.ovso.massage.main.MainPresenterImpl;
import io.github.ovso.massage.main.f_acupoints.AcupointsFragment;
import io.github.ovso.massage.main.f_acupoints.di.AcupointsFragmentModule;
import io.github.ovso.massage.main.f_symptom.SymptomFragment;
import io.github.ovso.massage.main.f_symptom.di.SymptomFragmentModule;
import io.github.ovso.massage.main.f_theme.ThemeFragment;
import io.github.ovso.massage.main.f_theme.di.ThemeFragmentModule;
import javax.inject.Singleton;

@Module public abstract class MainActivityModule {

  @Singleton @ContributesAndroidInjector(modules = SymptomFragmentModule.class)
  abstract SymptomFragment providePhoneFragmentFactory();

  @Singleton @ContributesAndroidInjector(modules = { AcupointsFragmentModule.class })
  abstract AcupointsFragment provideAcupointsFragmentFactory();

  @Singleton @ContributesAndroidInjector(modules = { ThemeFragmentModule.class })
  abstract ThemeFragment provideThemeFragmentFactory();

  @Provides static MainPresenter provideMainPresenter(MainActivity activity) {
    return new MainPresenterImpl(activity);
  }

  @Provides static FragmentManager provideFragmentManager(MainActivity mainActivity) {
    return mainActivity.getSupportFragmentManager();
  }
}