package com.awecell.game.quiz.logo.advertisement;

import android.app.Activity;
import android.util.Log;
import android.view.ViewGroup;

import com.awecell.game.quiz.logo.utils.ConstantValues;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

public class CustomAd {

	
	public void androidGmsAdsLoad(AdView adView,ViewGroup adslayout,AdSize adSize) {
		adView.setAdUnitId(StoreType.admob_id);
		AdRequest adRequest = new AdRequest.Builder().build();
		//AdRequest adRequest = new AdRequest.Builder().addTestDevice("B3EEABB8EE11C2BE770B684D95219ECB").build();
		adView.setAdSize(adSize);
		adView.loadAd(adRequest);
		adslayout.addView(adView);
	}

	public void intersialAdGms(InterstitialAd mInterstitialAd,final Activity activity) {    
		mInterstitialAd.setAdUnitId(StoreType.mInterstitialAdId);
		AdRequest.Builder adRequestBuilder = new AdRequest.Builder();
		 mInterstitialAd.loadAd(adRequestBuilder.build());
		 
		 mInterstitialAd.setAdListener(new AdListener() {
			 @Override
			public void onAdLeftApplication() {
				activity.finish();
				super.onAdLeftApplication();
			}
			 
			 @Override
			public void onAdLoaded() {
				 Log.e("",ConstantValues.INTERSTITIAL_AD_LOADED);
				super.onAdLoaded();
			}
			 
		});
	}


}
