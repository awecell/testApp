package com.awecell.game.quiz.logo.screens;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.awecell.game.quiz.logo.R;
import com.awecell.game.quiz.logo.utils.ConstantClass;
import com.awecell.game.quiz.logo.utils.SingletonClass;
import com.awecell.game.quiz.logo.utils.UpdateDb;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

public class GameScreen extends Activity implements OnClickListener{

	private ImageView logoView;
	private String answer;
	private AdView adView;
	private EditText userInputEditText;
	private String levelName ;
	private int rowId;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_screen);
		logoView = ((ImageView)findViewById(R.id.imageViewOnGameScreen));
		userInputEditText = ((EditText)findViewById(R.id.userInputEditText));


		// getting data from intent
		Intent intent = getIntent();
		levelName  = intent.getStringExtra(ConstantClass.LEVEL_NAME);
		rowId = intent.getIntExtra(ConstantClass.POSITION, 0);
		Bitmap logo = intent.getParcelableExtra(ConstantClass.IMAGE);
		answer = intent.getStringExtra(ConstantClass.ANSWER);

		logoView.setImageBitmap(logo);
		((Button)findViewById(R.id.checkAnswerButton)).setOnClickListener(this);
		((Button)findViewById(R.id.hint1)).setOnClickListener(this);
		((Button)findViewById(R.id.hint2)).setOnClickListener(this);
		((Button)findViewById(R.id.hint3)).setOnClickListener(this);
		((Button)findViewById(R.id.hint4)).setOnClickListener(this);
		((Button)findViewById(R.id.hint5)).setOnClickListener(this);
		((Button)findViewById(R.id.hint6)).setOnClickListener(this);

		adsLoad();
	}


	private void adsLoad() {
		adView = new AdView(GameScreen.this);
		LinearLayout adslayout = ((LinearLayout)(findViewById(R.id.addOnGameScreen)));
		SingletonClass.getSingletonObject().getMyAdd().androidGmsAdsLoad(adView, adslayout, AdSize.BANNER);
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
		if(adView!=null){
			adView.pause();
		}
		super.onPause();
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.checkAnswerButton:
			if(isAnswerRight()){
				doShakeAnimation();
				UpdateDb updateDb = new UpdateDb(this, levelName, ConstantClass.ANSWERED, rowId);
				updateDb.start();
			}
			else{
				Toast.makeText(this, ConstantClass.TEXT_FOR_WRONG_ANSWER, Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.hint1:
			//
			break;
		case R.id.hint2:
			break;
		case R.id.hint3:
			break;
		case R.id.hint4:
			break;
		case R.id.hint5:
			break;
		case R.id.hint6:
			break;

		default:
			break;
		}

	}


	private boolean isAnswerRight(){
		String userAnswer = userInputEditText.getText().toString();
		userAnswer = userAnswer.trim(); 
		if(userAnswer.equals(answer)){
			return true;
		}
		return false;
	}


	private void doShakeAnimation(){
		TranslateAnimation shakeAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.1f, Animation.RELATIVE_TO_SELF, -0.1f, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
		shakeAnimation.setRepeatCount(10);
		shakeAnimation.setRepeatMode(Animation.REVERSE);
		logoView.startAnimation(shakeAnimation);
	}


}
