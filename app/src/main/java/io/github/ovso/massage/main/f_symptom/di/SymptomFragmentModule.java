package io.github.ovso.massage.main.f_symptom.di;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import dagger.Module;
import dagger.Provides;
import io.github.ovso.massage.main.f_symptom.SymptomFragment;
import io.github.ovso.massage.main.f_symptom.db.SymptomLocalDb;
import io.github.ovso.massage.main.f_symptom.SymptomPresenter;
import io.github.ovso.massage.main.f_symptom.SymptomPresenterImpl;
import io.github.ovso.massage.main.f_symptom.adapter.SymptomAdapter;
import io.github.ovso.massage.main.f_symptom.adapter.SymptomAdapterView;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by jaeho on 2017. 10. 20
 */

@Module public class SymptomFragmentModule {

  @Provides SymptomPresenter provideSymptomPresenter(SymptomFragment fragment,
      DatabaseReference databaseReference, SymptomLocalDb localDb) {
    return new SymptomPresenterImpl(fragment, fragment.getAdapter(), databaseReference, localDb,
        fragment.getCompositeDisposable());
  }

  @Provides SymptomLocalDb provideLocalDatabase(SymptomFragment fragment) {
    return new SymptomLocalDb(fragment.getContext());
  }

  @Provides DatabaseReference provideDbRef() {
    return FirebaseDatabase.getInstance().getReference().child("symptom");
  }

  @Provides SymptomAdapter provideSymptomAdapter(SymptomFragment fragment) {
    return new SymptomAdapter().setOnRecyclerItemClickListener(fragment)
        .setCompositeDisposable(fragment.getCompositeDisposable());
  }

  @Provides SymptomAdapterView provideBaseAdapterView(SymptomFragment fragment) {
    return fragment.getAdapter();
  }

  @Provides CompositeDisposable provideCompositeDisposable() {
    return new CompositeDisposable();
  }
}
