package io.github.ovso.massage.main.f_acupoints.di;

import dagger.Module;
import dagger.Provides;
import io.github.ovso.massage.common.Security;
import io.github.ovso.massage.framework.adapter.BaseAdapterView;
import io.github.ovso.massage.main.f_acupoints.AcupointsFragment;
import io.github.ovso.massage.main.f_acupoints.AcupointsPresenter;
import io.github.ovso.massage.main.f_acupoints.AcupointsPresenterImpl;
import io.github.ovso.massage.main.f_acupoints.adapter.ImagesAdapter;
import io.github.ovso.massage.main.f_acupoints.network.ImagesNetwork;
import io.reactivex.disposables.CompositeDisposable;
import javax.inject.Singleton;

/**
 * Created by jaeho on 2017. 10. 20
 */

@Module public class AcupointsFragmentModule {

  @Provides AcupointsPresenter provideAcupointsPresenter(AcupointsFragment fragment,
      ImagesNetwork imagesNetwork, ImagesAdapter adapter, CompositeDisposable compositeDisposable) {
    return new AcupointsPresenterImpl(fragment, adapter, compositeDisposable, imagesNetwork);
  }

  @Singleton @Provides ImagesAdapter provideImagesAdapter(AcupointsFragment fragment,
      CompositeDisposable compositeDisposable) {
    return new ImagesAdapter().setOnRecyclerItemClickListener(fragment)
        .setCompositeDisposable(compositeDisposable);
  }

  @Provides BaseAdapterView provideBaseAdapterView(ImagesAdapter adapter) {
    return adapter;
  }

  @Singleton @Provides ImagesNetwork provideImagesNetwork(AcupointsFragment fragment) {
    return new ImagesNetwork(fragment.getContext(), Security.API_BASE_URL.getValue());
  }

  @Singleton @Provides CompositeDisposable provideCompositeDisposable() {
    return new CompositeDisposable();
  }
}