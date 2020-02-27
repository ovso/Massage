package io.github.ovso.massage

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import io.github.ovso.massage.ad.MyAdView
import io.github.ovso.massage.databinding.ActivityImageBinding
import kotlinx.android.synthetic.main.activity_image.*

class ImageActivity : AppCompatActivity() {

    private lateinit var interstitialAd: InterstitialAd

    private lateinit var binding: ActivityImageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = getString(R.string.acupressure_point)
        initAds()
        setSupportActionBar(binding.toolbarImage)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        Glide.with(photo_view_image)
            .load(intent.getStringExtra("image_url")).into(photo_view_image)
        tv_image.text = intent.getStringExtra("site")
    }

    private fun initAds() {
        setupInterstitialAd()
        setupBannerAd()
    }

    private fun setupBannerAd() {
        binding.adContainer.addView(MyAdView.getAdmobAdView(applicationContext))
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
        if (interstitialAd.isLoaded) {
            interstitialAd.show()
        } else {
            finish()
        }
    }
}
