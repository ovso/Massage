package io.github.ovso.massage.f_symptom.di;

import dagger.Module;
import dagger.Provides;
import io.github.ovso.massage.f_symptom.SymptomFragment;
import io.github.ovso.massage.f_symptom.SymptomPresenter;
import io.github.ovso.massage.f_symptom.SymptomPresenterImpl;

/**
 * Created by jaeho on 2017. 10. 20
 */

@Module public class SymptomFragmentModule {

  @Provides SymptomPresenter.View provideSymptomView(SymptomFragment fragment) {
    return fragment;
  }
  @Provides SymptomPresenter provideSymptomPresenter(SymptomFragment fragment) {
    return new SymptomPresenterImpl(fragment);
  }
}
