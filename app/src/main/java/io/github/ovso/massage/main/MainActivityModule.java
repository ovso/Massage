package io.github.ovso.massage.main;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jaeho on 2017. 10. 16
 */

@Module
public class MainActivityModule {

  @Provides
  MainPresenter.View provideView(MainActivity mainActivity) {
    return mainActivity;
  }

  @Provides
  MainPresenter provideMainPresenter(MainPresenter.View view) {
    return new MainPresenterImpl(view);
  }
}
