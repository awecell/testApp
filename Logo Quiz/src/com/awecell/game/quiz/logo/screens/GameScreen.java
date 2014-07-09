package com.awecell.game.quiz.logo.screens;

import java.util.ArrayList;
import java.util.Random;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.awecell.game.quiz.category.logo.R;
import com.awecell.game.quiz.logo.utils.ConstantValues;
import com.awecell.game.quiz.logo.utils.UpdateDb;

public class GameScreen extends BaseScreen implements OnClickListener,AnimationListener{

	private ImageView logoView;
	private String answer;
	private EditText userInputEditText;
	private String categoryName ;
	private int rowId;
	private String hintDetail1;
	private String hintDetail2;


	private boolean isHintLayoutOpen = false;
	private boolean isHintDetail1ToUse ;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//adding GameScreen layout to BaseScreen
		LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.game_screen,null,false);
		layoutForChildView.addView(view);

		logoView = ((ImageView)findViewById(R.id.imageViewOnGameScreen));
		userInputEditText = ((EditText)findViewById(R.id.userInputEditText));


		// getting data from intent
		Intent intent = getIntent();
		categoryName  = intent.getStringExtra(ConstantValues.CATEGORY);
		rowId = intent.getIntExtra(ConstantValues.POSITION, 0);
		Bitmap logo = intent.getParcelableExtra(ConstantValues.IMAGE);
		answer = intent.getStringExtra(ConstantValues.ANSWER);
		hintDetail1 = intent.getStringExtra(ConstantValues.HINT1);
		hintDetail2 = intent.getStringExtra(ConstantValues.HINT2);

		logoView.setImageBitmap(logo);
		((Button)findViewById(R.id.checkAnswerButton)).setOnClickListener(this);
		((Button)findViewById(R.id.hint1)).setOnClickListener(this);
		((Button)findViewById(R.id.hint2)).setOnClickListener(this);
		((Button)findViewById(R.id.hint3)).setOnClickListener(this);
		((Button)findViewById(R.id.hint4)).setOnClickListener(this);
		((Button)findViewById(R.id.hint5)).setOnClickListener(this);
		((Button)findViewById(R.id.hint6)).setOnClickListener(this);

		((Button)findViewById(R.id.okHintBtn)).setOnClickListener(this);


	}



	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.checkAnswerButton:
			if(isAnswerRight()){
				doShakeAnimation();
				UpdateDb updateDb = new UpdateDb(this, categoryName, ConstantValues.ANSWERED, rowId);
				updateDb.start();
			}
			else{
				userInputEditText.setText("");
				Toast.makeText(this, ConstantValues.TEXT_FOR_WRONG_ANSWER, Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.hint1:
			//jumble keypad
			getHint1();
			break;
		case R.id.hint2:
			// hint detai 1
			isHintDetail1ToUse = true;
			moveDownHintLayout();
			break;
		case R.id.hint3:
			//hintr detail2
			isHintDetail1ToUse = false;
			moveDownHintLayout();
			break;
		case R.id.okHintBtn:
			//close hint layout
			moveUpHintLayout();
			break;

		default:
			// getting text from jumbled keypad and setting it on edit text
			String text = ((Button)v).getText().toString(); 
			userInputEditText.append(text);
			break;
		}

	}

	private void moveDownHintLayout(){
		TranslateAnimation trAnimation = new TranslateAnimation(0,0,-450, 0);
		trAnimation.setDuration(1500);
		trAnimation.setAnimationListener(this);
		((RelativeLayout)findViewById(R.id.fakeHintLayout)).startAnimation(trAnimation);
	}

	private void moveUpHintLayout(){
		TranslateAnimation trAnimation = new TranslateAnimation(0,0,0,-550);
		trAnimation.setDuration(1500);
		trAnimation.setAnimationListener(this);
		((RelativeLayout)findViewById(R.id.fakeHintLayout)).startAnimation(trAnimation);
	}

	private void getHint1(){
		userInputEditText.setInputType(InputType.TYPE_NULL);
		LinearLayout keypadFirstRow = ((LinearLayout)findViewById(R.id.keyPadFirstRow));
		LinearLayout KeypadSecondRow = ((LinearLayout)findViewById(R.id.keyPadSecondRow));
         
		keypadFirstRow.removeAllViews();
		KeypadSecondRow.removeAllViews();

		//creating a arraylist of answer string length
		ArrayList<Integer> randomNumberList = new ArrayList<Integer>();
		for(int i = 0;i<answer.length();i++ ){
			randomNumberList.add(i);
		}


		for(int i =0;i<answer.length();i++){
			Button btn = new Button(this);
            btn.setOnClickListener(this);
			
			// getting random number and using it to set answer button in jumbled way
			int index = new Random().nextInt(randomNumberList.size());
			btn.setText(""+answer.charAt(randomNumberList.get(index)));
			randomNumberList.remove(index);


			if(i<5)
				keypadFirstRow.addView(btn);
			else
				KeypadSecondRow.addView(btn);
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



	@Override
	public void onAnimationEnd(Animation animation) {
		if(isHintLayoutOpen){
			((RelativeLayout)findViewById(R.id.hintLayout)).setVisibility(View.VISIBLE);
		}else if(!isHintLayoutOpen){
			setAllButtonEnable(true);
		}
	}



	@Override
	public void onAnimationRepeat(Animation animation) {
		// TODO Auto-generated method stub

	}



	@Override
	public void onAnimationStart(Animation animation) {
		if(!isHintLayoutOpen){
			if(isHintDetail1ToUse){         // checking which hint detail text to use
				((TextView)findViewById(R.id.fakehintTxtView)).setText(hintDetail1);
				((TextView)findViewById(R.id.hintTxtView)).setText(hintDetail1);
			}else{
				((TextView)findViewById(R.id.fakehintTxtView)).setText(hintDetail2);
				((TextView)findViewById(R.id.hintTxtView)).setText(hintDetail2);
			}
			setAllButtonEnable(false);
			isHintLayoutOpen = true;
		}else if(isHintLayoutOpen){
			isHintLayoutOpen = false;
			((RelativeLayout)findViewById(R.id.hintLayout)).setVisibility(View.INVISIBLE);

		}
	}


	private void setAllButtonEnable(boolean isEnable){
		((Button)findViewById(R.id.hint1)).setEnabled(isEnable);
		((Button)findViewById(R.id.hint2)).setEnabled(isEnable);
		((Button)findViewById(R.id.hint3)).setEnabled(isEnable);
	}


}
