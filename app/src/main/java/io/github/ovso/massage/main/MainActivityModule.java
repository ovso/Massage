package io.github.ovso.massage.main;

import android.support.v4.app.FragmentManager;
import dagger.Module;
import dagger.Provides;
import io.github.ovso.massage.f_acupoints.di.AcupointsFragmentComponent;
import io.github.ovso.massage.f_symptom.di.SymptomFragmentComponent;
import io.github.ovso.massage.f_theme.di.ThemeFragmentComponent;

/**
 * Created by jaeho on 2017. 10. 16
 */

@Module(subcomponents = {
    SymptomFragmentComponent.class, ThemeFragmentComponent.class, AcupointsFragmentComponent.class
}) public class MainActivityModule {

  @Provides MainPresenter provideMainPresenter(MainActivity activity) {
    return new MainPresenterImpl(activity);
  }

  @Provides FragmentManager provideFragmentManager(MainActivity mainActivity) {
    return mainActivity.getSupportFragmentManager();
  }
}