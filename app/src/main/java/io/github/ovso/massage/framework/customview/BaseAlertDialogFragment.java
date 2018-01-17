package io.github.ovso.massage.framework.customview;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.AndroidSupportInjection;
import io.github.ovso.massage.R;
import io.github.ovso.massage.framework.ObjectUtils;

/**
 * Created by jaeho on 2017. 10. 24
 */

public abstract class BaseAlertDialogFragment extends DialogFragment {
  private Unbinder unbinder;
  @NonNull @Override public Dialog onCreateDialog(Bundle savedInstanceState) {
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

  @Override public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    onActivityCreate(savedInstanceState);
  }

  @Override public void onAttach(Context context) {
    if (isDagger()) AndroidSupportInjection.inject(this);
    super.onAttach(context);
  }

  protected AlertDialog alertDialog;
  protected View contentView;

  @Override public void onStart() {
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

  protected abstract @LayoutRes int getLayoutResId();

  protected ViewGroup getInflateRoot() {
    return null;
  }

  protected boolean isDialogCancelable() {
    return true;
  }

  protected @StringRes int getTitle() {
    return R.string.empty;
  }

  protected abstract View.OnClickListener onPositiveClickListener();

  protected abstract View.OnClickListener onNegativeClickListener();

  protected View.OnClickListener onNeutralClickListener() {
    return null;
  }

  @Override public void onDetach() {
    super.onDetach();
    if(!ObjectUtils.isEmpty(unbinder)) {
      unbinder.unbind();
    }
  }
}
