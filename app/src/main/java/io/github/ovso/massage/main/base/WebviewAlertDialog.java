package io.github.ovso.massage.main.base;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.appcompat.app.AlertDialog;

import butterknife.BindView;
import io.github.ovso.massage.R;
import io.github.ovso.massage.framework.customview.BaseAlertDialogFragment;
import lombok.Setter;
import lombok.experimental.Accessors;

public class WebviewAlertDialog extends BaseAlertDialogFragment {
    @BindView(R.id.webview)
    WebView webview;
    @BindView(R.id.progressbar)
    ProgressBar progressBar;
    @Accessors(chain = true) @Setter public String url;
    @Accessors(chain = true) @Setter public boolean flag;

    @Override
    protected boolean isNegativeButton() {
        return true;
    }

    @Override
    protected boolean isPositiveButton() {
        return true;
    }

    @Override
    protected boolean isNeutralButton() {
        return true;
    }

    @Override
    protected boolean isDagger() {
        return false;
    }

    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        WebSettings settings = webview.getSettings();
        settings.setJavaScriptEnabled(true);// Javascript 사용하기
        settings.setBuiltInZoomControls(true);
        webview.setWebChromeClient(new WebChromeClient());
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if (progressBar != null) {
                    progressBar.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (progressBar != null) {
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
        //webview.setOnTouchListener((view, motionEvent) -> true);
        webview.loadUrl(url);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    protected boolean getAttatchRoot() {
        return false;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.dialog_webview;
    }

    @Override
    protected ViewGroup getInflateRoot() {
        return null;
    }

    @Override
    protected boolean isDialogCancelable() {
        return false;
    }

    @Override
    protected int getTitle() {
        return R.string.empty;
    }

    @Override
    protected View.OnClickListener onNegativeClickListener() {
        return view -> new AlertDialog.Builder(view.getContext()).setTitle(R.string.how_to_zoom_img)
                .setMessage(R.string.help_webview_image)
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }

    @Override
    public void onStart() {
        super.onStart();
        alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setText(R.string.how_to_zoom_img);
        alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setText(R.string.close);
    }

    @Override
    protected View.OnClickListener onNeutralClickListener() {
        return view -> {
            if (webview.canGoBack()) {
                webview.goBack();
            }
        };
    }
}
