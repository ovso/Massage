package io.github.ovso.massage.main.f_theme

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
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
import io.github.ovso.massage.main.f_theme.adapter.ThemeAdapter
import io.github.ovso.massage.main.f_theme.adapter.ThemeAdapterView
import io.github.ovso.massage.main.f_theme.model.Theme
import io.github.ovso.massage.view.ui.player.LandscapeVideoActivity
import io.github.ovso.massage.view.ui.player.PortraitVideoActivity
import io.reactivex.disposables.CompositeDisposable
import jp.wasabeef.recyclerview.animators.SlideInDownAnimator
import kotlinx.android.synthetic.main.fragment_theme.*

class ThemeFragment : BaseFragment(), ThemePresenter.View,
    OnCustomRecyclerItemClickListener<SelectableItem<Theme?>?> {
    var progressBar: ProgressBar? = null
    private val compositeDisposable = CompositeDisposable()
    private val adapter = ThemeAdapter()
    var adapterView: ThemeAdapterView? = null
    private var presenter: ThemePresenter? = null
    override fun getLayoutResID(): Int {
        return R.layout.fragment_theme
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter.compositeDisposable = compositeDisposable
        presenter = ThemePresenterImpl(
            this,
            adapter,
            FirebaseDatabase.getInstance().reference,
            compositeDisposable
        )
        presenter?.onActivityCreate()

    }

    override fun showYoutubeUseWarningDialog() {
        AlertDialog.Builder(activity).setMessage(R.string.youtube_use_warning)
            .setPositiveButton(android.R.string.ok, null)
            .show()
    }

    override fun showLandscapeVideo(videoId: String) {
        val intent = Intent(context, LandscapeVideoActivity::class.java)
        intent.putExtra("video_id", videoId)
        startActivity(intent)
    }

    override fun showVideoTypeDialog(`$onClickListener`: DialogInterface.OnClickListener) {
        val onClickListener =
            DialogInterface.OnClickListener { dialog: DialogInterface?, which: Int ->
                `$onClickListener`.onClick(
                    dialog,
                    which
                )
            }
        AlertDialog.Builder(context).setMessage(R.string.please_select_the_player_mode)
            .setPositiveButton(
                R.string.portrait_mode,
                onClickListener
            )
            .setNeutralButton(R.string.landscape_mode, onClickListener)
            .setNegativeButton(android.R.string.cancel, onClickListener)
            .show()
    }

    override fun setRecyclerView() {
        if (recyclerview.itemAnimator != null) {
            recyclerview.itemAnimator!!.changeDuration =
                Constants.DURATION_RECYCLERVIEW_ANI.toLong()
            recyclerview.itemAnimator!!.removeDuration =
                Constants.DURATION_RECYCLERVIEW_ANI.toLong()
        }
        recyclerview.itemAnimator = SlideInDownAnimator()
        recyclerview.addItemDecoration(rvDivider)
        recyclerview.adapter = adapter
    }

    private val rvDivider: DividerItemDecoration
        get() {
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
        adapterView!!.refreshRemove(position)
    }

    override fun showLoading() {
        progressBar!!.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progressBar!!.visibility = View.GONE
    }

    override fun refresh() {
        adapter.refresh()
    }

    override fun refresh(position: Int) {
        adapterView!!.refresh(position)
    }

    override fun showPortraitVideo(videoId: String) {
        val intent = Intent(context, PortraitVideoActivity::class.java)
        intent.putExtra("video_id", videoId)
        startActivity(intent)
    }

    override fun showWebViewDialog(item: Theme) {
    }

    override fun removeRefresh() {
        adapterView!!.refreshRemove()
    }

    override fun onItemClick(item: SelectableItem<Theme?>?) {
        presenter?.onItemClick(item)
    }

    override fun onVideoClick(position: Int, item: SelectableItem<Theme?>?) {
        presenter?.onVideoClick(position, item)
    }

    override fun onDestroyView() {
        presenter?.onDestroyView()
        compositeDisposable.clear()
        super.onDestroyView()
    }

    companion object {
        @JvmStatic
        fun newInstance(): ThemeFragment {
            return ThemeFragment()
        }
    }

}