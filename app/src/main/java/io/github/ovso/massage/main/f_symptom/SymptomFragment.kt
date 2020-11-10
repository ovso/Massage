package io.github.ovso.massage.main.f_symptom

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase
import io.github.ovso.massage.R
import io.github.ovso.massage.framework.Constants
import io.github.ovso.massage.framework.SelectableItem
import io.github.ovso.massage.framework.customview.BaseFragment
import io.github.ovso.massage.framework.listener.OnCustomRecyclerItemClickListener
import io.github.ovso.massage.main.f_symptom.adapter.SymptomAdapter
import io.github.ovso.massage.main.f_symptom.model.Symptom
import io.github.ovso.massage.view.ui.player.LandscapeVideoActivity
import io.github.ovso.massage.view.ui.player.PortraitVideoActivity
import io.reactivex.disposables.CompositeDisposable
import jp.wasabeef.recyclerview.animators.SlideInDownAnimator
import kotlinx.android.synthetic.main.fragment_theme.*

class SymptomFragment : BaseFragment(), SymptomPresenter.View,
    OnCustomRecyclerItemClickListener<SelectableItem<Symptom?>?> {
    private val adapter = SymptomAdapter()
    private var presenter: SymptomPresenter? = null
    private val compositeDisposable = CompositeDisposable()
    override fun getLayoutResID(): Int {
        return R.layout.fragment_symptom
    }

    override fun onActivityCreate(savedInstanceState: Bundle) {
        adapter.compositeDisposable = compositeDisposable
        presenter = SymptomPresenterImpl(
            this,
            adapter,
            FirebaseDatabase.getInstance().reference,
            compositeDisposable
        )
        presenter?.onActivityCreate()
    }

    override fun setRecyclerView() {
        if (recyclerview.itemAnimator != null) {
            recyclerview.itemAnimator?.setChangeDuration(Constants.DURATION_RECYCLERVIEW_ANI.toLong())
            recyclerview.itemAnimator?.setRemoveDuration(Constants.DURATION_RECYCLERVIEW_ANI.toLong())
        }
        recyclerview.setItemAnimator(SlideInDownAnimator())
        recyclerview.addItemDecoration(rvDivider)
        recyclerview.setAdapter(adapter)
    }

    private val rvDivider: DividerItemDecoration
        private get() {
            val divider = DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
            val drawable = ContextCompat.getDrawable(requireContext(), R.drawable.all_rv_divider)
            if (drawable != null) {
                divider.setDrawable(drawable)
            }
            return divider
        }

    override fun showMessage(resId: Int) {
        Toast.makeText(context, resId, Toast.LENGTH_SHORT).show()
    }

    override fun showMessage(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    override fun refreshRemove(position: Int) {
        adapter.refreshRemove(position)
    }

    override fun showLoading() {
        progressbar.setVisibility(View.VISIBLE)
    }

    override fun hideLoading() {
        progressbar.setVisibility(View.GONE)
    }

    override fun showYoutubeUseWarningDialog() {
        AlertDialog.Builder(activity).setMessage(R.string.youtube_use_warning)
            .setPositiveButton(android.R.string.ok, null)
            .show()
    }

    override fun refresh() {
        adapter.refresh()
    }

    override fun refresh(position: Int) {
        adapter.refresh(position)
    }

    override fun showPortraitVideo(videoId: String) {
        val intent = Intent(context, PortraitVideoActivity::class.java)
        intent.putExtra("video_id", videoId)
        startActivity(intent)
    }

    override fun showLandscapeVideo(videoId: String) {
        val intent = Intent(context, LandscapeVideoActivity::class.java)
        intent.putExtra("video_id", videoId)
        startActivity(intent)
    }

    override fun showVideoTypeDialog(`$onClickListener`: DialogInterface.OnClickListener) {
        AlertDialog.Builder(context).setMessage(R.string.please_select_the_player_mode)
            .setPositiveButton(
                R.string.portrait_mode,
                `$onClickListener`
            )
            .setNeutralButton(R.string.landscape_mode, `$onClickListener`)
            .setNegativeButton(android.R.string.cancel, `$onClickListener`)
            .show()
    }

    override fun showWebViewDialog(item: Symptom) {}
    override fun removeRefresh() {
        adapter.refreshRemove()
    }

    override fun onItemClick(item: SelectableItem<Symptom?>?) {
        presenter?.onItemClick(item)
    }

    override fun onVideoClick(position: Int, item: SelectableItem<Symptom?>?) {
        presenter?.onVideoClick(position, item)
    }

    override fun onDestroyView() {
        presenter!!.onDestroyView()
        compositeDisposable.clear()
        super.onDestroyView()
    }

    companion object {
        fun newInstance(): SymptomFragment {
            return SymptomFragment()
        }
    }
}