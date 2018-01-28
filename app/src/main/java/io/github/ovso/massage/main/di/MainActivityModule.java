package io.github.ovso.massage.main.di;

import android.support.v4.app.FragmentManager;
import dagger.Module;
import dagger.Provides;
import io.github.ovso.massage.main.f_acupoints.di.AcupointsFragmentComponent;
import io.github.ovso.massage.main.f_symptom.di.SymptomFragmentComponent;
import io.github.ovso.massage.main.f_theme.di.ThemeFragmentComponent;
import io.github.ovso.massage.main.MainActivity;
import io.github.ovso.massage.main.MainPresenter;
import io.github.ovso.massage.main.MainPresenterImpl;

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