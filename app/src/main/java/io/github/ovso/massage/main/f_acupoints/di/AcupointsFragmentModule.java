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

/**
 * Created by jaeho on 2017. 10. 20
 */

@Module public class AcupointsFragmentModule {

  @Provides AcupointsPresenter provideAcupointsPresenter(AcupointsFragment fragment,
      ImagesNetwork imagesNetwork) {
    return new AcupointsPresenterImpl(fragment, fragment.getAdapter(),
        fragment.getCompositeDisposable(), imagesNetwork);
  }

  @Provides ImagesAdapter provideImagesAdapter(AcupointsFragment fragment) {
    return new ImagesAdapter().setOnRecyclerItemClickListener(fragment)
        .setCompositeDisposable(fragment.getCompositeDisposable());
  }

  @Provides BaseAdapterView provideBaseAdapterView(AcupointsFragment fragment) {
    return fragment.getAdapter();
  }

  @Provides ImagesNetwork provideImagesNetwork(AcupointsFragment fragment) {
    return new ImagesNetwork(fragment.getContext(), Security.API_BASE_URL.getValue());
  }

  @Provides CompositeDisposable provideCompositeDisposable() {
    return new CompositeDisposable();
  }
}
