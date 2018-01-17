package io.github.ovso.massage.common;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
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
    settings.setSupportZoom(true);
    settings.setUseWideViewPort(false);
    settings.setDisplayZoomControls(true);
    settings.setJavaScriptCanOpenWindowsAutomatically(true);
    settings.setBuiltInZoomControls(false);
    settings.setLoadsImagesAutomatically(true);
    settings.setSupportZoom(true);
    webview.setWebChromeClient(new WebChromeClient());
    webview.setWebViewClient(new WebViewClientImpl());
    //webview.setOnTouchListener((view, motionEvent) -> true);
    webview.loadUrl(url);
  }

  private class WebViewClientImpl extends WebViewClient {
    @DebugLog @Override public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
      return super.shouldOverrideUrlLoading(view, request);
    }
  }

  @DebugLog @OnClick({ R.id.zoom_in_imageview, R.id.zoom_out_imageview }) void onZoomInOutClick(View view) {
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
