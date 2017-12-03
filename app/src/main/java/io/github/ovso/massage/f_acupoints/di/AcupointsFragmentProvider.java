package io.github.ovso.massage.f_acupoints.di;

import android.support.v4.app.Fragment;
import dagger.Binds;
import dagger.Module;
import dagger.android.AndroidInjector;
import dagger.android.support.FragmentKey;
import dagger.multibindings.IntoMap;
import io.github.ovso.massage.f_acupoints.AcupointsFragment;

/**
 * Created by jaeho on 2017. 10. 20
 */

@Module public abstract class AcupointsFragmentProvider {
  @Binds @IntoMap @FragmentKey(AcupointsFragment.class)
  abstract AndroidInjector.Factory<? extends Fragment> provideAcupointsFragmentFactory(
      AcupointsFragmentComponent.Builder builder);
}
