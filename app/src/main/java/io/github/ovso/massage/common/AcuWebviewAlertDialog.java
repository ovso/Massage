package io.github.ovso.massage.common;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import butterknife.BindView;
import butterknife.OnClick;
import hugo.weaving.DebugLog;
import io.github.ovso.massage.R;
import io.github.ovso.massage.framework.customview.BaseAlertDialogFragment;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created by jaeho on 2017. 11. 28
 */

public class AcuWebviewAlertDialog extends BaseAlertDialogFragment {
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

  @Override protected boolean isNeutralButton() {
    return true;
  }

  @Override protected boolean isDagger() {
    return false;
  }

  @Override protected void onActivityCreate(Bundle savedInstanceState) {
    WebSettings settings = webview.getSettings();
    settings.setJavaScriptEnabled(flag);// Javascript 사용하기
    webview.setWebChromeClient(new WebChromeClient());
    webview.setWebViewClient(new WebViewClientImpl());
    webview.loadUrl(url);
    progressBar.setVisibility(View.GONE);
  }
/*
    settings.setSupportZoom(true);
    settings.setUseWideViewPort(false);
    settings.setDisplayZoomControls(true);
    settings.setJavaScriptCanOpenWindowsAutomatically(true);
    settings.setBuiltInZoomControls(false);
    settings.setLoadsImagesAutomatically(true);
    settings.setSupportZoom(true);

 */
  private class WebViewClientImpl extends WebViewClient {
    @Override public void onPageStarted(WebView view, String url, Bitmap favicon) {
      super.onPageStarted(view, url, favicon);
      progressBar.setVisibility(View.VISIBLE);
    }

    @Override public void onPageFinished(WebView view, String url) {
      super.onPageFinished(view, url);
      progressBar.setVisibility(View.GONE);
    }
  }

  @OnClick({ R.id.zoom_in_imageview, R.id.zoom_out_imageview }) void onZoomInOutClick(
      View view) {
    int id = view.getId();
    switch (id) {
      case R.id.zoom_in_imageview:
        webview.zoomIn();
        break;
      case R.id.zoom_out_imageview:
        webview.zoomOut();
        break;
    }
  }
  @DebugLog @OnClick(R.id.help_button) void onHelpClick() {

  }
  @Override protected int getLayoutResId() {
    return R.layout.dialog_acu_webview;
  }

  @Override protected View.OnClickListener onPositiveClickListener() {
    return view -> dismiss();
  }

  @Override protected View.OnClickListener onNegativeClickListener() {
    return null;
  }

  @Override protected View.OnClickListener onNeutralClickListener() {
    return view -> {
      if (webview.canGoBack()) {
        webview.goBack();
      }
    };
  }

}
