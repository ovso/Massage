package io.github.ovso.massage.main.f_symptom.adapter

import android.view.View
import android.widget.TextView
import io.github.ovso.massage.framework.adapter.BaseRecyclerAdapter

internal class SymptomViewHolder(itemView: View?) : BaseRecyclerAdapter.BaseViewHolder(itemView) {
    @JvmField
    var titleTextview: TextView? = null
}