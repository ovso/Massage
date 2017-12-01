package io.github.ovso.massage.f_theme.di;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import dagger.Module;
import dagger.Provides;
import io.github.ovso.massage.f_theme.ThemeFragment;
import io.github.ovso.massage.f_theme.ThemePresenter;
import io.github.ovso.massage.f_theme.ThemePresenterImpl;
import io.github.ovso.massage.f_theme.adapter.ThemeAdapter;
import io.github.ovso.massage.f_theme.adapter.ThemeAdapterView;
import io.github.ovso.massage.f_theme.db.ThemeLocalDb;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by jaeho on 2017. 10. 20
 */

@Module public class ThemeFragmentModule {

  @Provides ThemePresenter provideThemePresenter(ThemeFragment fragment,
      DatabaseReference databaseReference, ThemeLocalDb localDb) {
    return new ThemePresenterImpl(fragment, fragment.getAdapter(), databaseReference, localDb,
        fragment.getCompositeDisposable());
  }

  @Provides ThemeLocalDb provideLocalDatabase() {
    return new ThemeLocalDb();
  }

  @Provides DatabaseReference provideDbRef() {
    return FirebaseDatabase.getInstance().getReference().child("theme");
  }

  @Provides ThemeAdapter provideThemeAdapter(ThemeFragment fragment) {
    return new ThemeAdapter().setOnRecyclerItemClickListener(fragment)
        .setCompositeDisposable(fragment.getCompositeDisposable());
  }

  @Provides ThemeAdapterView provideBaseAdapterView(ThemeFragment fragment) {
    return fragment.getAdapter();
  }

  @Provides CompositeDisposable provideCompositeDisposable() {
    return new CompositeDisposable();
  }
}
