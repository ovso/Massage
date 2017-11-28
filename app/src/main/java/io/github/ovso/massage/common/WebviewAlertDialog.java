package io.github.ovso.massage.common;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import butterknife.BindView;
import io.github.ovso.massage.R;
import io.github.ovso.massage.framework.customview.BaseAlertDialogFragment;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created by jaeho on 2017. 11. 28
 */

public class WebviewAlertDialog extends BaseAlertDialogFragment {
  @BindView(R.id.webview) WebView webview;
  @Accessors(chain = true) @Setter private String url;

  @Override protected boolean isNegativeButton() {
    return false;
  }

  @Override protected boolean isPositiveButton() {
    return true;
  }

  @Override protected boolean isDagger() {
    return false;
  }

  @Override protected void onActivityCreate(Bundle savedInstanceState) {
    //webview.getSettings().setJavaScriptEnabled(true);
    webview.setWebChromeClient(new WebChromeClient());
    webview.setWebViewClient(new WebViewClient());
    webview.setOnTouchListener((view, motionEvent) -> true);
    webview.loadUrl(url);
  }

  @Override protected boolean getAttatchRoot() {
    return false;
  }

  @Override protected int getLayoutResId() {
    return R.layout.dialog_webview;
  }

  @Override protected ViewGroup getInflateRoot() {
    return null;
  }

  @Override protected boolean isDialogCancelable() {
    return false;
  }

  @Override protected int getTitle() {
    return R.string.empty;
  }

  @Override protected View.OnClickListener onPositiveClickListener() {
    return view -> dismiss();
  }

  @Override protected View.OnClickListener onNegativeClickListener() {
    return null;
  }
}
