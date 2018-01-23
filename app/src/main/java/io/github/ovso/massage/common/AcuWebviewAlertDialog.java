package io.github.ovso.massage.common;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
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

public class AcuWebviewAlertDialog extends BaseAlertDialogFragment {

  @BindView(R.id.webview) WebView webview;
  @BindView(R.id.progressbar) ProgressBar progressBar;
  @Accessors(chain = true) @Setter private String url;
  @Accessors(chain = true) @Setter private boolean flag;

  @Override protected boolean isNegativeButton() {
    return true;
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
    settings.setJavaScriptEnabled(flag);
    settings.setBuiltInZoomControls(true);

    webview.setWebChromeClient(new WebChromeClient());
    webview.setWebViewClient(new WebViewClientImpl());
    webview.loadUrl(url);
    progressBar.setVisibility(View.GONE);
  }

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

  @Override protected int getLayoutResId() {
    return R.layout.dialog_acu_webview;
  }

  @Override protected View.OnClickListener onPositiveClickListener() {
    return view -> dismiss();
  }

  @Override protected View.OnClickListener onNegativeClickListener() {
    return view -> new AlertDialog.Builder(getContext()).setTitle(R.string.how_to_zoom_img)
        .setMessage(R.string.help_webview_image)
        .setPositiveButton(android.R.string.ok, null)
        .show();
  }

  @Override protected View.OnClickListener onNeutralClickListener() {
    return view -> {
      if (webview.canGoBack()) {
        webview.goBack();
      }
    };
  }

  @Override public void onStart() {
    super.onStart();
    alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setText(R.string.how_to_zoom_img);
    alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setText(R.string.close);
  }
}
