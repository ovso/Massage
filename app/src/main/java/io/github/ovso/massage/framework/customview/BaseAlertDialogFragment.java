package io.github.ovso.massage.framework.customview;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.AndroidSupportInjection;
import io.github.ovso.massage.R;
import io.github.ovso.massage.framework.ObjectUtils;

public abstract class BaseAlertDialogFragment extends DialogFragment {
    private Unbinder unbinder;
    private InterstitialAd interstitialAd;
    protected AlertDialog alertDialog;
    protected View contentView;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getTitle());
        if (isPositiveButton()) {
            builder.setPositiveButton(android.R.string.ok, null);
        }
        if (isNegativeButton()) {
            builder.setNegativeButton(android.R.string.cancel, null);
        }
        if (isNeutralButton()) {
            builder.setNeutralButton(R.string.go_back, null);
        }
        builder.setView(getContentView());
        builder.create();
        return builder.create();
    }

    protected boolean isNeutralButton() {
        return false;
    }

    protected abstract boolean isNegativeButton();

    protected abstract boolean isPositiveButton();

    private View getContentView() {
        View view = LayoutInflater.from(getContext())
                .inflate(getLayoutResId(), getInflateRoot(), getAttatchRoot());
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        interstitialAd = provideInterstitialAd(getContext());
        interstitialAd.setAdListener(interstitialAdListener);
        onActivityCreate(savedInstanceState);
    }

    private InterstitialAd provideInterstitialAd(Context context) {
        InterstitialAd interstitialAd = new InterstitialAd(context);
        interstitialAd.setAdUnitId(getString(R.string.ads_interstitial_unit_id));
        AdRequest.Builder adRequestBuilder = new AdRequest.Builder();
        interstitialAd.loadAd(adRequestBuilder.build());
        return interstitialAd;
    }

    private AdListener interstitialAdListener = new AdListener() {
        @Override
        public void onAdClosed() {
            super.onAdClosed();
            dismissAllowingStateLoss();
        }
    };

    private void showInterstitialAd() {
        if (!ObjectUtils.isEmpty(interstitialAd)) {
            if (interstitialAd.isLoaded()) {
                interstitialAd.show();
            } else {
                dismissAllowingStateLoss();
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        if (isDagger()) AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public void onStart() {
        super.onStart();
        alertDialog = (AlertDialog) getDialog();
        if (alertDialog != null) {
            contentView = alertDialog.findViewById(R.id.root_view);
            setCancelable(isDialogCancelable());
            alertDialog.getButton(DialogInterface.BUTTON_POSITIVE)
                    .setOnClickListener(onPositiveClickListener());
            alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE)
                    .setOnClickListener(onNegativeClickListener());
            alertDialog.getButton(DialogInterface.BUTTON_NEUTRAL)
                    .setOnClickListener(onNeutralClickListener());
        }
    }

    protected abstract boolean isDagger();

    protected abstract void onActivityCreate(Bundle savedInstanceState);

    protected boolean getAttatchRoot() {
        return false;
    }

    protected abstract @LayoutRes
    int getLayoutResId();

    protected ViewGroup getInflateRoot() {
        return null;
    }

    protected boolean isDialogCancelable() {
        return true;
    }

    protected @StringRes
    int getTitle() {
        return R.string.empty;
    }

    protected View.OnClickListener onPositiveClickListener() {
        return v -> showInterstitialAd();
    }

    protected abstract View.OnClickListener onNegativeClickListener();

    protected View.OnClickListener onNeutralClickListener() {
        return null;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (!ObjectUtils.isEmpty(unbinder)) {
            unbinder.unbind();
        }
    }
}
