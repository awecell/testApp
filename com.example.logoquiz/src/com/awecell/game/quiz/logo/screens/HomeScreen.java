package com.awecell.game.quiz.logo.screens;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

import com.awecell.game.quiz.logo.R;
import com.awecell.game.quiz.logo.utils.CreateDb;
import com.awecell.game.quiz.logo.utils.SingletonClass;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

public class HomeScreen extends Activity implements OnClickListener{
	private InterstitialAd interstitialAd;
	private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);
        ((Button)findViewById(R.id.playButton)).setOnClickListener(this);
        adsLoad();
        fullScreenAddOnExit();
        
        CreateDb createDb = new CreateDb(this);
        createDb.start();
    }
    

	private void fullScreenAddOnExit() {
		interstitialAd = new InterstitialAd(this);
		SingletonClass.getSingletonObject().getMyAdd().intersialAdGms(interstitialAd, HomeScreen.this);
	}
	
	
	private void adsLoad() {
		adView = new AdView(HomeScreen.this);
		LinearLayout adslayout = ((LinearLayout)(findViewById(R.id.addOnHomeScreen)));
		SingletonClass.getSingletonObject().getMyAdd().androidGmsAdsLoad(adView, adslayout, AdSize.BANNER);
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
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.playButton:
			startActivity(new Intent(this,LevelScreen.class));
			break;

		default:
			break;
		}
	}
    
    

}
