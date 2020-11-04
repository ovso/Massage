package io.github.ovso.massage.main.f_acupoints;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import io.github.ovso.massage.ImageActivity;
import io.github.ovso.massage.R;
import io.github.ovso.massage.Security;
import io.github.ovso.massage.framework.Constants;
import io.github.ovso.massage.framework.customview.BaseFragment;
import io.github.ovso.massage.main.base.ImageViewAlertDialog;
import io.github.ovso.massage.main.f_acupoints.adapter.ImagesAdapter;
import io.github.ovso.massage.main.f_acupoints.adapter.OnAcuRecyclerItemClickListener;
import io.github.ovso.massage.main.f_acupoints.model.Documents;
import io.github.ovso.massage.main.f_acupoints.network.ImagesNetwork;
import io.reactivex.disposables.CompositeDisposable;
import jp.wasabeef.recyclerview.animators.SlideInDownAnimator;

@SuppressWarnings("SpellCheckingInspection")
public class AcupointsFragment extends BaseFragment
        implements AcupointsPresenter.View, OnAcuRecyclerItemClickListener<Documents> {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.progressbar)
    ProgressBar progressBar;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private ImagesAdapter adapter = null;
    private AcupointsPresenter presenter = null;

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        adapter = new ImagesAdapter(compositeDisposable, this);
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_acupoints;
    }

    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        presenter = new AcupointsPresenterImpl(
                this,
                adapter,
                compositeDisposable,
                new ImagesNetwork(requireContext(), Security.API_BASE_URL.value)
        );
        presenter.onActivityCreate(getResources());
    }

    public static AcupointsFragment newInstance() {
        return new AcupointsFragment();
    }

    @Override
    public void setRecyclerView() {
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        if (recyclerView.getItemAnimator() != null) {
            recyclerView.getItemAnimator().setChangeDuration(Constants.DURATION_RECYCLERVIEW_ANI);
            recyclerView.getItemAnimator().setRemoveDuration(Constants.DURATION_RECYCLERVIEW_ANI);
        }
        recyclerView.setItemAnimator(new SlideInDownAnimator());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showMessage(int resId) {
        Toast.makeText(getContext(), resId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void refresh() {
        adapter.refresh();
    }

    @Override
    public void onItemClick(Documents item) {
        presenter.onItemClick(item);
    }

    @Override
    public void onDocUrlItemClick(Documents item) {
        presenter.onDocUrlItemClick(item);
    }

    @Override
    public void showImageViewDialog(String image_url) {
        new ImageViewAlertDialog().setImageUrl(image_url)
                .show(getChildFragmentManager(), ImageViewAlertDialog.class.getSimpleName());
    }

    @Override
    public void showWebViewDialog(String doc_url) {
        new AlertDialog.Builder(requireActivity()).setTitle(R.string.origin)
                .setMessage(doc_url)
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }

    @Override
    public void navigateToImage(Documents item) {
        Intent intent = new Intent(requireActivity(), ImageActivity.class);
        intent.putExtra("image_url", item.image_url);
        intent.putExtra("source", item.display_sitename);
        intent.putExtra("site", item.doc_url);
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        presenter.onDestroyView();
        super.onDestroyView();
    }
}
