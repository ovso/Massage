package io.github.ovso.massage.main.base;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import butterknife.BindView;

import com.bumptech.glide.request.target.Target;

import io.github.ovso.massage.R;
import io.github.ovso.massage.di.GlideApp;
import io.github.ovso.massage.framework.customview.BaseAlertDialogFragment;
import lombok.Setter;
import lombok.experimental.Accessors;

public class ImageViewAlertDialog extends BaseAlertDialogFragment {
    @BindView(R.id.imageview)
    ImageView imageView;
    @Accessors(chain = true)
    @Setter
    private String imageUrl;

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
        GlideApp.with(this).load(imageUrl).override(Target.SIZE_ORIGINAL).into(imageView);
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
