package com.example.logoquiz_screens;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.logoquiz.R;

public class Level_screen extends Activity implements OnClickListener{

	private int screenWidth;
	private int screenHeight;
	private LinearLayout layoutForLevels;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.level_screen);

		screenWidth = getResources().getDisplayMetrics().widthPixels;
		screenHeight = getResources().getDisplayMetrics().heightPixels;
		layoutForLevels = ((LinearLayout)findViewById(R.id.layoutForLevels));
		createLevels();
	}

	private void createLevels() {
		AssetManager assetManager = this.getAssets();
		try {
			InputStream inStream = assetManager.open("levels.txt");
			InputStreamReader file = new InputStreamReader(inStream);
			BufferedReader reader = new BufferedReader(file);
			String level = "level1";
			while (level!=null) {
				level = reader.readLine();
				if(level!=null){
					TextView levelText = new TextView(this);
					levelText.setOnClickListener(this);
					levelText.setGravity(Gravity.CENTER);
					levelText.setText(level);
					levelText.setBackgroundColor(Color.YELLOW);
					int width = (int) (screenWidth*0.78f);
					int height = (int) (screenHeight*0.208f);
					levelText.setLayoutParams(getLayoutParams(width, height));
					layoutForLevels.addView(levelText);
				}
			}
			inStream.close();
			file.close();
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private LinearLayout.LayoutParams getLayoutParams(int width,int height){
		LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(width, height);
		param.topMargin = (int) (screenWidth*0.041);
		return param;
	}

	@Override
	public void onClick(View view) {
		
		String level = ((TextView)view).getText().toString();
		Intent intent = new Intent(this, LevelDetailScreen.class);
		intent.putExtra("levelName", level);
		startActivity(intent);
	}




}
