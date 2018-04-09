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
import io.github.ovso.massage.main.model.Help;
import io.github.ovso.massage.main.model.NoticeItem;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import javax.inject.Inject;

/**
 * Created by jaeho on 2017. 10. 16
 */

public class MainPresenterImpl implements MainPresenter {

  private MainPresenter.View view;
  private CompositeDisposable compositeDisposable;
  private DatabaseReference databaseReference;

  @Inject public MainPresenterImpl(MainPresenter.View view) {
    this.view = view;
    this.compositeDisposable = new CompositeDisposable();
    this.databaseReference = FirebaseDatabase.getInstance().getReference().child("licenses");
    view.changeTheme();
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    view.setListener();
    view.showSymptomFragment();
    view.showAd();
  }

  @Override public boolean onNavItemSelected(int itemId) {
    if (itemId == R.id.nav_opensource) {
      compositeDisposable.add(RxFirebaseDatabase.data(databaseReference)
          .subscribeOn(Schedulers.io())
          .map(dataSnapshot -> {
            final Notices notices = new Notices();
            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
              NoticeItem item = snapshot.getValue(NoticeItem.class);
              Notice notice = new Notice(item.getName(), item.getUrl(), item.getCopyright(),
                  NoticeItem.getLicense(item.getLicense()));
              notices.addNotice(notice);
            }
            return notices;
          })
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(notices -> view.showLicensesDialog(notices), throwable -> {
            view.showMessage(R.string.error_server);
          }));
    } else if (itemId == R.id.nav_help) {
      compositeDisposable.add(
          RxFirebaseDatabase.data(FirebaseDatabase.getInstance().getReference().child("help"))
              .subscribeOn(Schedulers.io())
              .map(dataSnapshot -> {
                String msg = "";
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                  Help help = snapshot.getValue(Help.class);
                  msg += help.getMsg() + "\n\n";
                }
                return msg;
              })
              .observeOn(AndroidSchedulers.mainThread())
              .subscribe(msg -> view.showHelpAlert(msg), throwable -> {
                view.showHelpAlert(R.string.help_long_click_video);
              }));
    }
    view.closeDrawer();
    return true;
  }

  @Override public boolean onBottomNavItemSelected(@IdRes int itemId, boolean isChecked) {
    if (!isChecked) {
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
