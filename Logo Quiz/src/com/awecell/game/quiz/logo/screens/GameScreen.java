package com.awecell.game.quiz.logo.screens;

import java.util.ArrayList;
import java.util.Random;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.google.android.gms.games.Games;

public class GameScreen extends BaseScreen implements OnClickListener,AnimationListener{

	private ImageView logoView;
	private String answer;
	private EditText userInputEditText;
	private TextView hintText;
	private String categoryName ;
	private int rowId;
	private String hintDetail1;
	private String hintDetail2;
	private Dialog dialog;

	private int total_hint = 0;
	private int total_score = 0;

	private Button jumbleHintButton ;
	private Button hint1Button;
	private Button hint2Button;


	private boolean isHintLayoutOpen = false;
	private int hint_name;
	private SharedPreferences preferences;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//adding GameScreen layout to BaseScreen
		LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.game_screen,null,false);
		layoutForChildView.addView(view);

		logoView = ((ImageView)findViewById(R.id.imageViewOnGameScreen));
		userInputEditText = ((EditText)findViewById(R.id.userInputEditText));
		hintText = ((TextView)findViewById(R.id.noOfHintView));
		hintText.setText(ConstantValues.HINT+" : "+total_hint);

		preferences = getSharedPreferences(ConstantValues.PACKAGE_NAME,MODE_PRIVATE);

		total_hint = preferences.getInt(ConstantValues.HINT, ConstantValues.INITIAL_HINT);
		total_score = preferences.getInt(ConstantValues.SCORE, 0);

		dialog = new Dialog(this);
		dialog.setContentView(R.layout.alert_layout);
		dialog.setCanceledOnTouchOutside(false);

		// getting data from intent
		Intent intent = getIntent();
		categoryName  = intent.getStringExtra(ConstantValues.CATEGORY);
		rowId = intent.getIntExtra(ConstantValues.POSITION, 0);
		Bitmap logo = intent.getParcelableExtra(ConstantValues.IMAGE);
		answer = intent.getStringExtra(ConstantValues.ANSWER);
		hintDetail1 = intent.getStringExtra(ConstantValues.HINT1_TEXT);
		hintDetail2 = intent.getStringExtra(ConstantValues.HINT2_TEXT);

		logoView.setImageBitmap(logo);
		((Button)findViewById(R.id.checkAnswerButton)).setOnClickListener(this);

		jumbleHintButton  = ((Button)findViewById(R.id.hint1));
		hint1Button = ((Button)findViewById(R.id.hint2));
		hint2Button = ((Button)findViewById(R.id.hint3));

		jumbleHintButton.setOnClickListener(this);
		hint1Button.setOnClickListener(this);
		hint2Button.setOnClickListener(this);

		jumbleHintButton.setTag(ConstantValues.UNUSED_HINT);
		hint1Button.setTag(ConstantValues.UNUSED_HINT);
		hint2Button.setTag(ConstantValues.UNUSED_HINT);
		((Button)findViewById(R.id.okHintBtn)).setOnClickListener(this);

	}

	@Override
	protected void onDestroy() {
		if(dialog!=null){
			dialog.dismiss();
		}
		super.onDestroy();
	}

	public void showAlertBeforeUsingHint(String title,int hintCost){
		dialog.setTitle(title);
		dialog.show();
		((TextView)dialog.findViewById(R.id.alertText)).setText(ConstantValues.COST+hintCost+ConstantValues.HINT);
		((Button)dialog.findViewById(R.id.alertCancel)).setOnClickListener(this);
		((Button)dialog.findViewById(R.id.alertOk)).setOnClickListener(this);
	}



	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.checkAnswerButton:
			if(isAnswerRight()){
				doShakeAnimation();
				total_score++;
				preferences.edit().putInt(ConstantValues.SCORE,total_score).commit();
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
			total_hint = preferences.getInt(ConstantValues.HINT,5);
			int state = Integer.parseInt(jumbleHintButton.getTag().toString());
			if(state==ConstantValues.UNUSED_HINT){
				if(total_hint>0){
					hint_name = ConstantValues.JUMBLE_HINT;
					showAlertBeforeUsingHint(ConstantValues.ABOUT_JUMBLE_HINT, ConstantValues.JUMBLE_HINT_COST);
				}else{
					Toast.makeText(this,ConstantValues.NO_HINT_STATEMENT,Toast.LENGTH_SHORT).show();
				}
			}else{
				getJumbledKeyPad();
			}
			break;
		case R.id.hint2:
			// hint detail 1
			total_hint = preferences.getInt(ConstantValues.HINT,5);
			state = Integer.parseInt(hint1Button.getTag().toString());
			if(state==ConstantValues.UNUSED_HINT){
				if(total_hint>0){
					hint_name = ConstantValues.HINT1;
					showAlertBeforeUsingHint(ConstantValues.ABOUT_HINT, ConstantValues.HINT1_COST);
				}else{
					Toast.makeText(this,ConstantValues.NO_HINT_STATEMENT,Toast.LENGTH_SHORT).show();
				}
			}else{
				moveDownHintLayout();
			}
			break;
		case R.id.hint3:
			//hint detail2
			total_hint = preferences.getInt(ConstantValues.HINT,5);
			state = Integer.parseInt(hint2Button.getTag().toString());
			if(state==ConstantValues.UNUSED_HINT){
				if(total_hint>0){
					hint_name = ConstantValues.HINT2;
					showAlertBeforeUsingHint(ConstantValues.ABOUT_HINT, ConstantValues.HINT2_COST);
				}else{
					Toast.makeText(this,ConstantValues.NO_HINT_STATEMENT,Toast.LENGTH_SHORT).show();
				}
			}else{
				moveDownHintLayout();
			}
			break;
		case R.id.okHintBtn:
			//close hint layout
			moveUpHintLayout();
			break;
		case R.id.alertCancel:
			dialog.hide();
			break;
		case R.id.alertOk:
			switch (hint_name) {
			case ConstantValues.JUMBLE_HINT:
				dialog.hide();
				getJumbledKeyPad();
				total_hint -= ConstantValues.JUMBLE_HINT_COST;
				preferences.edit().putInt(ConstantValues.HINT, total_hint).commit();
				jumbleHintButton.setTag(ConstantValues.USED_HINT);
				hintText.setText(ConstantValues.HINT+" : "+total_hint);
				break;
			case ConstantValues.HINT1:
				dialog.hide();
				total_hint -= ConstantValues.HINT1_COST;
				preferences.edit().putInt(ConstantValues.HINT, total_hint).commit();
				moveDownHintLayout();
				hint1Button.setTag(ConstantValues.USED_HINT);
				hintText.setText(ConstantValues.HINT+" : "+total_hint);
				break;
			case ConstantValues.HINT2:
				dialog.hide();
				total_hint -= ConstantValues.HINT2_COST;
				preferences.edit().putInt(ConstantValues.HINT, total_hint).commit();
				moveDownHintLayout();
				hint2Button.setTag(ConstantValues.USED_HINT);
				hintText.setText(ConstantValues.HINT+" : "+total_hint);
				break;
			}
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

	private void getJumbledKeyPad(){
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
		shakeAnimation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				GameScreen.this.finish();

			}
		});
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
			if(hint_name==ConstantValues.HINT1){         // checking which hint detail text to use
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


	public void googleleaderBoard() {
		beginUserInitiatedSignIn();
		if(isSignedIn()){
			total_score = preferences.getInt(ConstantValues.SCORE, 0);
			Games.Leaderboards.submitScore(getApiClient(), ConstantValues.LEADERBOARD_ID,total_score);
			startActivityForResult(Games.Leaderboards.getLeaderboardIntent(getApiClient(), ConstantValues.LEADERBOARD_ID), 9002);
		}
	}


}
