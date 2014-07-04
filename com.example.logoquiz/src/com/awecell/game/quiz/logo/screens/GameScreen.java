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
import android.widget.Toast;

import com.awecell.game.quiz.logo.R;

public class GameScreen extends Activity implements OnClickListener{
	
	private ImageView logoView;
	private String answer;
	private EditText userInputEditText;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_screen);
		logoView = ((ImageView)findViewById(R.id.imageViewOnGameScreen));
		userInputEditText = ((EditText)findViewById(R.id.userInputEditText));
		
		
		// getting data from intent
		Intent intent = getIntent();
		Bitmap logo = intent.getParcelableExtra("image");
		answer = intent.getStringExtra("answer");
		
		logoView.setImageBitmap(logo);
		((Button)findViewById(R.id.checkAnswerButton)).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.checkAnswerButton:
			if(isAnswerRight())
				doShakeAnimation();
			else
				Toast.makeText(this, "Wrong Answer", Toast.LENGTH_SHORT).show();
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
