package io.github.ovso.massage.main;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * Created by jaeho on 2017. 10. 16
 */

@Subcomponent(modules = { MainActivityModule.class }) public interface MainActivityComponent
    extends AndroidInjector<MainActivity> {

  @Subcomponent.Builder abstract class Builder extends AndroidInjector.Builder<MainActivity> {
  }
}
