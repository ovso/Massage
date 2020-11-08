package io.github.ovso.massage.main.f_acupoints.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import io.github.ovso.massage.databinding.ItemNativeAdsSmallBinding
import timber.log.Timber

class NativeAdsViewHolder private constructor(
    private val binding: ItemNativeAdsSmallBinding
) : ImagesViewHolder(binding.root) {

    fun onBindViewHolder() {
        Timber.d("OJH onBindViewHolder")
    }

    companion object {
        fun create(parent: ViewGroup): NativeAdsViewHolder {
            return NativeAdsViewHolder(
                ItemNativeAdsSmallBinding.inflate(LayoutInflater.from(parent.context))
            )
        }
    }
}