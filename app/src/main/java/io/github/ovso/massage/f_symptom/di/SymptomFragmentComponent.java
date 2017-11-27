package io.github.ovso.massage.f_symptom.di;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import io.github.ovso.massage.f_symptom.SymptomFragment;

/**
 * Created by jaeho on 2017. 10. 20
 */

@Subcomponent(modules = SymptomFragmentModule.class) public interface SymptomFragmentComponent
    extends AndroidInjector<SymptomFragment> {

  @Subcomponent.Builder abstract class Builder extends AndroidInjector.Builder<SymptomFragment> {
  }
}
