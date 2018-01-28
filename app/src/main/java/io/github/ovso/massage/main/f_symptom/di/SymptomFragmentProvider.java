package io.github.ovso.massage.main.f_symptom.di;

import android.support.v4.app.Fragment;
import dagger.Binds;
import dagger.Module;
import dagger.android.AndroidInjector;
import dagger.android.support.FragmentKey;
import dagger.multibindings.IntoMap;
import io.github.ovso.massage.main.f_symptom.SymptomFragment;

/**
 * Created by jaeho on 2017. 10. 20
 */

@Module public abstract class SymptomFragmentProvider {
  @Binds @IntoMap @FragmentKey(SymptomFragment.class)
  abstract AndroidInjector.Factory<? extends Fragment> provideSymptomFragmentFactory(
      SymptomFragmentComponent.Builder builder);
}
