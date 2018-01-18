package io.github.ovso.massage.common;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
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
  @BindView(R.id.progressbar) ProgressBar progressBar;
  @Accessors(chain = true) @Setter private String url;
  @Accessors(chain = true) @Setter private boolean flag;

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
    WebSettings settings = webview.getSettings();
    settings.setJavaScriptEnabled(flag);// Javascript 사용하기
    webview.setWebChromeClient(new WebChromeClient());
    webview.setWebViewClient(new WebViewClient(){
      @Override public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        progressBar.setVisibility(View.VISIBLE);
      }

      @Override public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        progressBar.setVisibility(View.GONE);
      }
    });
    webview.setOnTouchListener((view, motionEvent) -> true);
    webview.loadUrl(url);
    progressBar.setVisibility(View.GONE);
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
