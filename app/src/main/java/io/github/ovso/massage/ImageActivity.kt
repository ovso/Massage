package io.github.ovso.massage

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import io.github.ovso.massage.framework.ObjectUtils
import kotlinx.android.synthetic.main.activity_image.*

class ImageActivity : AppCompatActivity() {

    private lateinit var interstitialAd: InterstitialAd

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupInterstitialAd()

        title = getString(R.string.acupressure_point)
        setContentView(R.layout.activity_image)
        setSupportActionBar(toolbar_image)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        Glide.with(photo_view_image)
            .load(intent.getStringExtra("image_url")).into(photo_view_image)
        tv_image.text = intent.getStringExtra("site")
    }

    private fun setupInterstitialAd() {
        interstitialAd = provideInterstitialAd()
        interstitialAd.adListener = interstitialAdListener
    }

    private val interstitialAdListener: AdListener = object : AdListener() {
        override fun onAdClosed() {
            super.onAdClosed()
            showInterstitialAd()
        }
    }

    private fun provideInterstitialAd() =
        InterstitialAd(applicationContext).apply {
            adUnitId = getString(R.string.ads_interstitial_unit_id)
            val adRequestBuilder =
                AdRequest.Builder()
            loadAd(adRequestBuilder.build())
        }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        showInterstitialAd()
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        showInterstitialAd()
    }

    private fun showInterstitialAd() {
        if (!ObjectUtils.isEmpty(interstitialAd)) {
            if (interstitialAd.isLoaded) {
                interstitialAd.show()
            } else {
                finish()
            }
        }
    }
}
