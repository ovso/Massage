package io.github.ovso.massage.main;

import android.support.v4.app.FragmentManager;
import dagger.Module;
import dagger.Provides;
import io.github.ovso.massage.f_symptom.di.SymptomFragmentComponent;
import io.github.ovso.massage.f_theme.di.ThemeFragmentComponent;

/**
 * Created by jaeho on 2017. 10. 16
 */

@Module(subcomponents = { SymptomFragmentComponent.class, ThemeFragmentComponent.class })
public class MainActivityModule {

  @Provides MainPresenter.View provideMainView(MainActivity mainActivity) {
    return mainActivity;
  }

  @Provides MainPresenter provideMainPresenter(MainPresenter.View view) {
    return new MainPresenterImpl(view);
  }

  @Provides FragmentManager provideFragmentManager(MainActivity mainActivity) {
    return mainActivity.getSupportFragmentManager();
  }
}