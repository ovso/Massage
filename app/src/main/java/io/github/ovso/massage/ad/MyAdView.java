package io.github.ovso.massage.ad;

import android.content.Context;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import io.github.ovso.massage.R;

public class MyAdView {

    public static AdView getAdmobAdView(Context context) {
        AdView adView = new AdView(context);
        adView.setAdSize(AdSize.SMART_BANNER);
        adView.setAdUnitId(context.getString(R.string.ads_banner_unit_id));
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        return adView;
    }

    public static InterstitialAd getAdmobInterstitialAd(Context context) {
        InterstitialAd interstitialAd = new InterstitialAd(context);
        interstitialAd.setAdUnitId(context.getString(R.string.ads_interstitial_unit_id));
        AdRequest.Builder adRequestBuilder = new AdRequest.Builder();
        interstitialAd.loadAd(adRequestBuilder.build());
        return interstitialAd;
    }
}