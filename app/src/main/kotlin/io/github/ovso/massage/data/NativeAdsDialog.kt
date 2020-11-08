package io.github.ovso.massage.data

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import io.github.ovso.massage.databinding.DialogNativeAds2Binding

class NativeAdsDialog2(context: Context) : AlertDialog.Builder(context) {

    private val binding by lazy {
        DialogNativeAds2Binding.inflate(LayoutInflater.from(context))
    }

    private var unitId: String? = null
    fun setUnitId(unitId: String?): AlertDialog.Builder {
        this.unitId = unitId
        setView(binding.root)
        val templateView = binding.templateView
        val adLoader = AdLoader.Builder(context, unitId).apply {
            forUnifiedNativeAd {
                templateView.setNativeAd(it)
                binding.progressBar.isVisible = false
            }.withAdListener(object : AdListener() {
                override fun onAdFailedToLoad(p0: LoadAdError?) {
                    binding.progressBar.isVisible = false
                }
            })
        }.build()
        adLoader.loadAd(AdRequest.Builder().build())
        return this
    }

    override fun setView(view: View): AlertDialog.Builder {
        if (unitId == null) throw KotlinNullPointerException("NativeId must not null")
        return super.setView(view)
    }

    override fun show(): AlertDialog {
        if (unitId == null) throw KotlinNullPointerException("NativeId must not null")
        binding.progressBar.isVisible = true
        return super.show()
    }
}