package io.github.ovso.massage.main.base;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import butterknife.BindView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import io.github.ovso.massage.R;
import io.github.ovso.massage.framework.customview.BaseAlertDialogFragment;

public class ImageViewAlertDialog extends BaseAlertDialogFragment {
    @BindView(R.id.imageview)
    ImageView imageView;
    private String imageUrl;

    public ImageViewAlertDialog setImageUrl(String url) {
        imageUrl = url;
        return this;
    }
    @Override
    protected boolean isNegativeButton() {
        return false;
    }

    @Override
    protected boolean isPositiveButton() {
        return true;
    }

    @Override
    protected boolean isDagger() {
        return false;
    }

    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        Glide.with(this).load(imageUrl).override(Target.SIZE_ORIGINAL).into(imageView);
    }

    @Override
    protected boolean getAttatchRoot() {
        return false;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.dialog_imageview;
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
        return null;
    }
}
