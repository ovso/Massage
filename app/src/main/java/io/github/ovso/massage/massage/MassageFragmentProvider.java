package io.github.ovso.massage.massage;

import android.support.v4.app.Fragment;
import dagger.Binds;
import dagger.Module;
import dagger.android.AndroidInjector;
import dagger.android.support.FragmentKey;
import dagger.multibindings.IntoMap;

/**
 * Created by jaeho on 2017. 10. 20
 */

@Module public abstract class MassageFragmentProvider {
  @Binds @IntoMap @FragmentKey(MassageFragment.class)
  abstract AndroidInjector.Factory<? extends Fragment> provideMassageFragmentFactory(
      MassageFragmentComponent.Builder builder);
}
