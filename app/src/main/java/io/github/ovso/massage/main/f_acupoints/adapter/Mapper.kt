package io.github.ovso.massage.main.f_acupoints.adapter

import io.github.ovso.massage.main.f_acupoints.model.Documents


fun List<Documents?>.getAdsAddedItem(): List<Documents?> {
    val newItems = mutableListOf<Documents?>()
    val originCount = this.count()
    val adsStep = 5
    for (i in 0 until originCount step adsStep) {
        val toIndex = if (i + adsStep > originCount) originCount else i + adsStep
        val subList = subList(fromIndex = i, toIndex = toIndex)
        with(newItems) {
            add(NativeAdsModel())
            addAll(subList)
        }
    }
    return newItems
}