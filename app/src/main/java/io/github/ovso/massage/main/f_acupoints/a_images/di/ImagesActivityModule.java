package io.github.ovso.massage.main.f_acupoints.a_images.di;

import dagger.Module;
import dagger.Provides;
import io.github.ovso.massage.framework.adapter.BaseAdapterView;
import io.github.ovso.massage.main.f_acupoints.a_images.ImagesActivity;
import io.github.ovso.massage.main.f_acupoints.a_images.ImagesPresenter;
import io.github.ovso.massage.main.f_acupoints.a_images.ImagesPresenterImpl;
import io.github.ovso.massage.main.f_acupoints.a_images.adapter.ImagesAdapter;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by jaeho on 2017. 10. 16
 */

@Module(subcomponents = {}) public class ImagesActivityModule {

  @Provides ImagesPresenter provideImagesPresenter(ImagesActivity activity) {
    return new ImagesPresenterImpl(activity, activity.getAdapter(),
        activity.getCompositeDisposable());
  }

  @Provides ImagesAdapter provideImagesAdapter(ImagesActivity activity) {
    return new ImagesAdapter().setCompositeDisposable(activity.getCompositeDisposable())
        .setOnRecyclerItemClickListener(activity);
  }

  @Provides CompositeDisposable provideCompositeDisposable() {
    return new CompositeDisposable();
  }

  @Provides BaseAdapterView provideAdapterView(ImagesActivity activity) {
    return activity.getAdapter();
  }
}