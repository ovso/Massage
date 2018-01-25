package io.github.ovso.massage.main.f_acupoints.di;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import io.github.ovso.massage.main.f_acupoints.AcupointsFragment;

/**
 * Created by jaeho on 2017. 10. 20
 */

@Subcomponent(modules = AcupointsFragmentModule.class) public interface AcupointsFragmentComponent
    extends AndroidInjector<AcupointsFragment> {

  @Subcomponent.Builder abstract class Builder extends AndroidInjector.Builder<AcupointsFragment> {
  }
}
