package io.github.ovso.massage.main.f_symptom.di;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.github.ovso.massage.framework.SelectableItem;
import io.github.ovso.massage.framework.adapter.BaseAdapterDataModel;
import io.github.ovso.massage.main.f_symptom.SymptomFragment;
import io.github.ovso.massage.main.f_symptom.SymptomPresenter;
import io.github.ovso.massage.main.f_symptom.SymptomPresenterImpl;
import io.github.ovso.massage.main.f_symptom.adapter.SymptomAdapter;
import io.github.ovso.massage.main.f_symptom.adapter.SymptomAdapterView;
import io.github.ovso.massage.main.f_symptom.model.Symptom;
import io.reactivex.disposables.CompositeDisposable;

@Module
public class SymptomFragmentModule {

    @Provides
    SymptomPresenter provideSymptomPresenter(
            SymptomFragment fragment,
            DatabaseReference databaseReference,
            CompositeDisposable compositeDisposable,
            BaseAdapterDataModel<SelectableItem<Symptom>> dataModel) {
        return new SymptomPresenterImpl(
                fragment,
                dataModel,
                databaseReference,
                compositeDisposable);
    }

    @Provides
    DatabaseReference provideDbRef() {
        return FirebaseDatabase.getInstance().getReference().child("symptom");
    }

    @Provides
    @Singleton
    SymptomAdapter provideSymptomAdapter(SymptomFragment fragment,
                                         CompositeDisposable compositeDisposable) {
        return new SymptomAdapter().setOnRecyclerItemClickListener(fragment)
                .setCompositeDisposable(compositeDisposable);
    }

    @Provides
    BaseAdapterDataModel<SelectableItem<Symptom>> provideAdapterDataModel(
            SymptomAdapter adapter) {
        return adapter;
    }

    @Provides
    SymptomAdapterView provideBaseAdapterView(SymptomAdapter adapter) {
        return adapter;
    }

    @Provides
    @Singleton
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }
}
