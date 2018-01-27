package io.github.ovso.massage.main.f_acupoints.a_images.di;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import io.github.ovso.massage.main.f_acupoints.a_images.ImagesActivity;

/**
 * Created by jaeho on 2017. 10. 16
 */

@Subcomponent(modules = {
    ImagesActivityModule.class
}) public interface ImagesActivityComponent extends AndroidInjector<ImagesActivity> {

  @Subcomponent.Builder abstract class Builder extends AndroidInjector.Builder<ImagesActivity> {
  }
}
