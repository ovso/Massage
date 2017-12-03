package io.github.ovso.massage.f_acupoints.di;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import dagger.Module;
import dagger.Provides;
import io.github.ovso.massage.f_acupoints.AcupointsFragment;
import io.github.ovso.massage.f_acupoints.AcupointsPresenter;
import io.github.ovso.massage.f_acupoints.AcupointsPresenterImpl;
import io.github.ovso.massage.f_acupoints.adapter.AcupointsAdapter;
import io.github.ovso.massage.f_acupoints.adapter.AcupointsAdapterView;
import io.github.ovso.massage.f_acupoints.db.AcupointsLocalDb;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by jaeho on 2017. 10. 20
 */

@Module public class AcupointsFragmentModule {

  @Provides AcupointsPresenter provideAcupointsPresenter(AcupointsFragment fragment,
      DatabaseReference databaseReference, AcupointsLocalDb localDb) {
    return new AcupointsPresenterImpl(fragment, fragment.getAdapter(), databaseReference, localDb,
        fragment.getCompositeDisposable());
  }

  @Provides AcupointsLocalDb provideLocalDatabase(AcupointsFragment fragment) {
    return new AcupointsLocalDb(fragment.getContext());
  }

  @Provides DatabaseReference provideDbRef() {
    return FirebaseDatabase.getInstance().getReference().child("acupoints");
  }

  @Provides AcupointsAdapter provideAcupointsAdapter(AcupointsFragment fragment) {
    return new AcupointsAdapter().setOnRecyclerItemClickListener(fragment)
        .setCompositeDisposable(fragment.getCompositeDisposable());
  }

  @Provides AcupointsAdapterView provideBaseAdapterView(AcupointsFragment fragment) {
    return fragment.getAdapter();
  }

  @Provides CompositeDisposable provideCompositeDisposable() {
    return new CompositeDisposable();
  }
}
