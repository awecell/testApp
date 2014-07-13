package com.awecell.game.quiz.logo.screens;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.awecell.game.quiz.category.logo.R;
import com.awecell.game.quiz.logo.utils.ReferenceWrapper;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.example.games.basegameutils.BaseGameActivity;

public class BaseScreen extends BaseGameActivity {
	private InterstitialAd interstitialAd;
	private AdView adView;
	protected RelativeLayout layoutForChildView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_screen);
        layoutForChildView = (RelativeLayout)findViewById(R.id.layoutForChildView);
        loadAds();
        fullScreenAdOnExit();
    }
    

	private void fullScreenAdOnExit() {
		interstitialAd = new InterstitialAd(this);
		ReferenceWrapper.getReferenceWrapper().getMyAdd().intersialAdGms(interstitialAd, BaseScreen.this);
	}
	
	
	private void loadAds() {
		adView = new AdView(BaseScreen.this);
		LinearLayout adslayout = ((LinearLayout)(findViewById(R.id.addOnHomeScreen)));
		ReferenceWrapper.getReferenceWrapper().getMyAdd().androidGmsAdsLoad(adView, adslayout, AdSize.BANNER);
	}
	
	@Override
	public void onBackPressed() {
		if(interstitialAd.isLoaded()){
			interstitialAd.show();
		}
		super.onBackPressed();
	}
	
	
	@Override
	protected void onResume() {
		if(adView!=null){
			adView.resume();
		}
		super.onResume();
	}
	
	@Override
	protected void onDestroy() {
		if(adView!=null){
			adView.destroy();
		}
		super.onDestroy();
	}
	
	@Override
	protected void onPause() {
		adView.pause();
		super.onPause();
	}


	@Override
	public void onSignInFailed() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onSignInSucceeded() {
		// TODO Auto-generated method stub
		
	}    

}
