package io.github.ovso.massage.main;

import android.os.Bundle;

import androidx.annotation.IdRes;

import io.github.ovso.massage.R;
import io.reactivex.disposables.CompositeDisposable;

import static io.github.ovso.massage.R.id.action_acupoints;
import static io.github.ovso.massage.R.id.action_symptom;
import static io.github.ovso.massage.R.id.action_theme;

public class MainPresenterImpl implements MainPresenter {

    private final MainPresenter.View view;
    private final CompositeDisposable compositeDisposable;

    public MainPresenterImpl(MainPresenter.View view) {
        this.view = view;
        this.compositeDisposable = new CompositeDisposable();
        view.changeTheme();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        view.setListener();
        view.showSymptomFragment();
        view.showAd();
    }

    @Override
    public boolean onNavItemSelected(int itemId) {
        if (itemId == R.id.nav_opensource) {
            view.navigateToOssLicensesMenu();
        } else if (itemId == R.id.nav_help) {
            view.showHelpAlert(R.string.help_msg);
        }
        view.closeDrawer();
        return true;
    }

    @Override
    public boolean onBottomNavItemSelected(@IdRes int itemId, boolean isChecked) {
        if (!isChecked) {
            switch (itemId) {
                case action_symptom:
                    view.showSymptomFragment();
                    break;
                case action_theme:
                    view.showThemeFrgament();
                    break;
                case action_acupoints:
                    view.showAcupoints();
                    break;
            }
        }
        return true;
    }

    @Override
    public void onBackPressed(boolean isDrawerOpen) {
        if (isDrawerOpen) {
            view.closeDrawer();
        } else {
            compositeDisposable.clear();
            view.finish();
        }
    }
}
