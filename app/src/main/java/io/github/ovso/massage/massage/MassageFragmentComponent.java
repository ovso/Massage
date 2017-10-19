package io.github.ovso.massage.massage;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * Created by jaeho on 2017. 10. 20
 */

@Subcomponent(modules = MassageFragmentModule.class) public interface MassageFragmentComponent
    extends AndroidInjector<MassageFragment> {

  @Subcomponent.Builder abstract class Builder extends AndroidInjector.Builder<MassageFragment> {
  }
}
