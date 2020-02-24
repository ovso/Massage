package io.github.ovso.massage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_image.*

class ImageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)
        Glide.with(photo_view_image)
            .load(intent.getStringExtra("image_url")).into(photo_view_image)
        tv_image.text = intent.getStringExtra("site")
    }
}
