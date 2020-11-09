package io.github.ovso.massage.main.f_acupoints.adapter

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.google.android.ads.nativetemplates.NativeTemplateStyle
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import io.github.ovso.massage.R
import io.github.ovso.massage.databinding.ItemNativeAdsSmallBinding
import io.github.ovso.massage.framework.adapter.BaseRecyclerAdapter
import timber.log.Timber

class NativeAdsViewHolder private constructor(
    private val binding: ItemNativeAdsSmallBinding
) : BaseRecyclerAdapter.BaseViewHolder(binding.root) {

    fun onBindViewHolder() {
        Timber.d("OJH onBindViewHolder")
        binding.progressBar.isVisible = true
        val templateView = binding.templateView
        val nativeUnitId = itemView.resources.getString(R.string.ads_native_unit_id)
        val adLoader = AdLoader.Builder(itemView.context, nativeUnitId).apply {
            forUnifiedNativeAd {
                val styles = NativeTemplateStyle.Builder()
                    .withCallToActionTextTypeface(Typeface.DEFAULT_BOLD)
                    .build()
                templateView.setStyles(styles)
                templateView.setNativeAd(it)
                binding.progressBar.isVisible = false
            }
            withAdListener(object : AdListener() {
                override fun onAdFailedToLoad(p0: LoadAdError?) {
                    binding.progressBar.isVisible = false
                }
            })
        }.build()
        adLoader.loadAd(AdRequest.Builder().build())
    }

    companion object {
        @JvmStatic
        fun create(parent: ViewGroup): NativeAdsViewHolder {
            return NativeAdsViewHolder(
                ItemNativeAdsSmallBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }
}