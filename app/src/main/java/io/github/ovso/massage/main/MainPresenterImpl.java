package io.github.ovso.massage.main;

import android.os.Bundle;
import android.support.annotation.IdRes;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import de.psdev.licensesdialog.model.Notice;
import de.psdev.licensesdialog.model.Notices;
import io.github.ovso.massage.R;
import io.github.ovso.massage.main.model.NoticeItem;
import io.github.ovso.massage.utils.ResourceProvider;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import javax.inject.Inject;

public class MainPresenterImpl implements MainPresenter {

  private MainPresenter.View view;
  private CompositeDisposable compositeDisposable;

  @Inject public MainPresenterImpl(MainPresenter.View view) {
    this.view = view;
    this.compositeDisposable = new CompositeDisposable();
    view.changeTheme();
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    view.setListener();
    view.showSymptomFragment();
    view.showAd();
  }

  @Override public boolean onNavItemSelected(int itemId) {
    if (itemId == R.id.nav_opensource) {

      Disposable subscribe = Single.fromCallable(() -> "")
          .map(empty -> {
            final Notices notices = new Notices();
            String licenses = ResourceProvider.INSTANCE.fromAssets("licenses.json");
            JsonArray jsonArray = new Gson().fromJson(licenses, JsonArray.class);
            for (JsonElement jsonElement : jsonArray) {
              NoticeItem noticeItem =
                  new Gson().fromJson(jsonElement.getAsJsonObject(), NoticeItem.class);
              Notice notice =
                  new Notice(noticeItem.getName(), noticeItem.getUrl(),
                      noticeItem.getCopyright(),
                      NoticeItem.getLicense(noticeItem.getLicense()));
              notices.addNotice(notice);
            }
            return notices;
          })
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(notices -> view.showLicensesDialog(notices),
              throwable -> view.showMessage(R.string.error_server));

      compositeDisposable.add(subscribe);
    } else if (itemId == R.id.nav_help) {
      view.showHelpAlert(R.string.help_msg);
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
