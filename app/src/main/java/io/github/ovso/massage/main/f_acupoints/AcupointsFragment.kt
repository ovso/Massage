package io.github.ovso.massage.main.f_acupoints

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import io.github.ovso.massage.ImageActivity
import io.github.ovso.massage.R
import io.github.ovso.massage.Security
import io.github.ovso.massage.framework.Constants
import io.github.ovso.massage.framework.customview.BaseFragment
import io.github.ovso.massage.main.f_acupoints.adapter.ImagesAdapter
import io.github.ovso.massage.main.f_acupoints.adapter.OnAcuRecyclerItemClickListener
import io.github.ovso.massage.main.f_acupoints.model.Documents
import io.github.ovso.massage.main.f_acupoints.network.ImagesNetwork
import io.reactivex.disposables.CompositeDisposable
import jp.wasabeef.recyclerview.animators.SlideInDownAnimator
import kotlinx.android.synthetic.main.fragment_acupoints.*

class AcupointsFragment : BaseFragment(), AcupointsPresenter.View,
    OnAcuRecyclerItemClickListener<Documents?> {
    private val compositeDisposable = CompositeDisposable()
    private var adapter: ImagesAdapter? = null
    private var presenter: AcupointsPresenter? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        adapter = ImagesAdapter(compositeDisposable, this)
    }

    override fun getLayoutResID(): Int {
        return R.layout.fragment_acupoints
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = AcupointsPresenterImpl(
            this,
            adapter!!,
            compositeDisposable,
            ImagesNetwork(requireContext(), Security.API_BASE_URL.value)
        )
        presenter?.onActivityCreate(resources)
    }

    override fun setRecyclerView() {
        if (recyclerview.itemAnimator != null) {
            recyclerview.itemAnimator?.changeDuration = Constants.DURATION_RECYCLERVIEW_ANI.toLong()
            recyclerview.itemAnimator?.removeDuration = Constants.DURATION_RECYCLERVIEW_ANI.toLong()
        }
        recyclerview.itemAnimator = SlideInDownAnimator()
        recyclerview.addItemDecoration(rvDivider)
        recyclerview.adapter = adapter
    }

    private val rvDivider: DividerItemDecoration
        get() {
            val divider = DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
            val drawable =
                ContextCompat.getDrawable(requireContext(), R.drawable.acupoints_rv_divider)
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

    override fun showLoading() {
        progressbar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progressbar.visibility = View.GONE
    }

    override fun refresh() {
        adapter?.refresh()
    }

    override fun onItemClick(item: Documents?) {
        presenter?.onItemClick(item)
    }

    override fun onDocUrlItemClick(item: Documents?) {
        presenter?.onDocUrlItemClick(item)
    }

    override fun showImageViewDialog(image_url: String) {}
    override fun showWebViewDialog(doc_url: String) {
        AlertDialog.Builder(requireActivity()).setTitle(R.string.origin)
            .setMessage(doc_url)
            .setPositiveButton(android.R.string.ok, null)
            .show()
    }

    override fun navigateToImage(item: Documents) {
        val intent = Intent(requireActivity(), ImageActivity::class.java)
        intent.putExtra("image_url", item.image_url)
        intent.putExtra("source", item.display_sitename)
        intent.putExtra("site", item.doc_url)
        startActivity(intent)
    }

    override fun onDestroyView() {
        presenter!!.onDestroyView()
        super.onDestroyView()
    }

    companion object {
        fun newInstance(): AcupointsFragment {
            return AcupointsFragment()
        }
    }
}