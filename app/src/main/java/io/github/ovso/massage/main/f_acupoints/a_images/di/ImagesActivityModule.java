package io.github.ovso.massage.main.f_acupoints.a_images.di;

import dagger.Module;
import dagger.Provides;
import io.github.ovso.massage.main.f_acupoints.a_images.ImagesActivity;
import io.github.ovso.massage.main.f_acupoints.a_images.ImagesPresenter;
import io.github.ovso.massage.main.f_acupoints.a_images.ImagesPresenterImpl;

/**
 * Created by jaeho on 2017. 10. 16
 */

@Module(subcomponents = {}) public class ImagesActivityModule {

  @Provides ImagesPresenter provideImagesPresenter(ImagesActivity activity) {
    return new ImagesPresenterImpl(activity);
  }
}