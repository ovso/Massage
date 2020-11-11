package io.github.ovso.massage.main.f_acupoints.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import io.github.ovso.massage.R
import io.github.ovso.massage.framework.adapter.BaseRecyclerAdapter

class ImagesViewHolder(itemView: View) : BaseRecyclerAdapter.BaseViewHolder(itemView) {
    @JvmField
    var docUrlTextview: TextView = itemView.findViewById<View>(R.id.doc_url_textview) as TextView

    @JvmField
    var imageview: ImageView = itemView.findViewById<View>(R.id.imageview) as ImageView

}