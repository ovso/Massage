package io.github.ovso.massage.main.f_symptom.di;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import dagger.Module;
import dagger.Provides;
import io.github.ovso.massage.framework.adapter.BaseAdapterDataModel;
import io.github.ovso.massage.main.f_symptom.SymptomFragment;
import io.github.ovso.massage.main.f_symptom.SymptomPresenter;
import io.github.ovso.massage.main.f_symptom.SymptomPresenterImpl;
import io.github.ovso.massage.main.f_symptom.adapter.SymptomAdapter;
import io.github.ovso.massage.main.f_symptom.adapter.SymptomAdapterView;
import io.github.ovso.massage.main.f_symptom.db.SymptomLocalDb;
import io.reactivex.disposables.CompositeDisposable;
import javax.inject.Singleton;

@Module public class SymptomFragmentModule {

  @Provides SymptomPresenter provideSymptomPresenter(SymptomFragment fragment,
      DatabaseReference databaseReference, SymptomLocalDb localDb,
      CompositeDisposable compositeDisposable, BaseAdapterDataModel dataModel) {
    return new SymptomPresenterImpl(fragment, dataModel, databaseReference, localDb,
        compositeDisposable);
  }

  @Provides @Singleton SymptomLocalDb provideLocalDatabase(SymptomFragment fragment) {
    return new SymptomLocalDb(fragment.getContext());
  }

  @Provides DatabaseReference provideDbRef() {
    return FirebaseDatabase.getInstance().getReference().child("symptom");
  }

  @Provides @Singleton SymptomAdapter provideSymptomAdapter(SymptomFragment fragment,
      CompositeDisposable compositeDisposable) {
    return new SymptomAdapter().setOnRecyclerItemClickListener(fragment)
        .setCompositeDisposable(compositeDisposable);
  }

  @Provides BaseAdapterDataModel provideAdapterDataModel(SymptomAdapter adapter) {
    return adapter;
  }

  @Provides SymptomAdapterView provideBaseAdapterView(SymptomAdapter adapter) {
    return adapter;
  }

  @Provides @Singleton CompositeDisposable provideCompositeDisposable() {
    return new CompositeDisposable();
  }
}
