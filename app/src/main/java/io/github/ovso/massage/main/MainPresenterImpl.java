package io.github.ovso.massage.main;

import android.os.Bundle;
import android.support.annotation.IdRes;
import com.androidhuman.rxfirebase2.database.RxFirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import de.psdev.licensesdialog.model.Notice;
import de.psdev.licensesdialog.model.Notices;
import io.github.ovso.massage.R;
import io.github.ovso.massage.main.model.NoticeItem;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import javax.inject.Inject;

/**
 * Created by jaeho on 2017. 10. 16
 */

public class MainPresenterImpl extends Exception implements MainPresenter {

  private MainPresenter.View view;
  private CompositeDisposable compositeDisposable;
  private DatabaseReference databaseReference;

  @Inject MainPresenterImpl(MainPresenter.View view) {
    this.view = view;
    this.compositeDisposable = new CompositeDisposable();
    this.databaseReference = FirebaseDatabase.getInstance().getReference().child("licenses");
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    view.setListener();
    view.showSymptomFragment();
  }

  @Override public boolean onNavItemSelected(int itemId) {
    if (itemId == R.id.nav_opensource) {
      final Notices notices = new Notices();
      compositeDisposable.add(RxFirebaseDatabase.data(databaseReference)
          .subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(dataSnapshot -> {
            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
              NoticeItem item = snapshot.getValue(NoticeItem.class);
              Notice notice = new Notice(item.getName(), item.getUrl(), item.getCopyright(),
                  NoticeItem.getLicense(item.getLicense()));
              notices.addNotice(notice);
            }
            view.showLicensesDialog(notices);
          }, throwable -> {

          }));
    } else if (itemId == R.id.nav_help) {
      view.showMessageAlert(R.string.help_long_click_video);
    }
    view.closeDrawer();
    return true;
  }

  @Override public boolean onBottomNavItemSelected(@IdRes int itemId) {
    switch (itemId) {
      case R.id.action_symptom:
        view.showSymptomFragment();
        break;
      case R.id.action_theme:
        view.showThemeFrgament();
        break;
      case R.id.action_acupoints:
        view.showAcupoints();
        break;
    }
    return true;
  }

  @Override public void onBackPressed(boolean isDrawerOpen) {
    if (isDrawerOpen) {
      view.closeDrawer();
    } else {
      compositeDisposable.clear();
      view.finish();
    }
  }
}
