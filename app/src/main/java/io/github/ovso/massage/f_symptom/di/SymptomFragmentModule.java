package io.github.ovso.massage.f_symptom.di;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import dagger.Module;
import dagger.Provides;
import io.github.ovso.massage.f_symptom.SymptomFragment;
import io.github.ovso.massage.f_symptom.SymptomPresenter;
import io.github.ovso.massage.f_symptom.SymptomPresenterImpl;

/**
 * Created by jaeho on 2017. 10. 20
 */

@Module public class SymptomFragmentModule {

  @Provides SymptomPresenter provideSymptomPresenter(SymptomFragment fragment,
      DatabaseReference databaseReference) {
    return new SymptomPresenterImpl(fragment, databaseReference);
  }

  @Provides DatabaseReference provideDbRef() {
    return FirebaseDatabase.getInstance().getReference().child("symptom");
  }
}
