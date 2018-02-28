package io.github.ovso.massage.main.f_theme.di;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import dagger.Module;
import dagger.Provides;
import io.github.ovso.massage.main.f_theme.ThemeFragment;
import io.github.ovso.massage.main.f_theme.ThemePresenter;
import io.github.ovso.massage.main.f_theme.ThemePresenterImpl;
import io.github.ovso.massage.main.f_theme.adapter.ThemeAdapter;
import io.github.ovso.massage.main.f_theme.adapter.ThemeAdapterView;
import io.github.ovso.massage.main.f_theme.db.ThemeLocalDb;
import io.reactivex.disposables.CompositeDisposable;
import javax.inject.Singleton;

/**
 * Created by jaeho on 2017. 10. 20
 */

@Module public class ThemeFragmentModule {

  @Provides @Singleton ThemePresenter provideThemePresenter(ThemeFragment fragment,
      DatabaseReference databaseReference, ThemeLocalDb localDb, ThemeAdapter adapter,
      CompositeDisposable compositeDisposable) {
    return new ThemePresenterImpl(fragment, adapter, databaseReference, localDb,
        compositeDisposable);
  }

  @Provides @Singleton ThemeLocalDb provideLocalDatabase(ThemeFragment fragment) {
    return new ThemeLocalDb(fragment.getContext());
  }

  @Provides DatabaseReference provideDbRef() {
    return FirebaseDatabase.getInstance().getReference().child("theme");
  }

  @Provides @Singleton ThemeAdapter provideThemeAdapter(ThemeFragment fragment,
      CompositeDisposable compositeDisposable) {
    return new ThemeAdapter().setOnRecyclerItemClickListener(fragment)
        .setCompositeDisposable(compositeDisposable);
  }

  @Provides ThemeAdapterView provideBaseAdapterView(ThemeAdapter adapter) {
    return adapter;
  }

  @Provides @Singleton CompositeDisposable provideCompositeDisposable() {
    return new CompositeDisposable();
  }
}