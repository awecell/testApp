package com.awecell.game.quiz.logo.screens;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.awecell.game.quiz.logo.R;
import com.awecell.game.quiz.logo.utils.ConstantValues;
import com.awecell.game.quiz.logo.utils.CreateDb;

public class CategoryScreen extends BaseScreen implements OnClickListener{

	private int screenWidth;
	private int screenHeight;
	private LinearLayout layoutForLevels;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// adding category screen to BaseScreen
		LayoutInflater inflator = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflator.inflate(R.layout.category_screen, null,false);
        layoutForChildView.addView(view);
        
        
		screenWidth = getResources().getDisplayMetrics().widthPixels;
		screenHeight = getResources().getDisplayMetrics().heightPixels;
		layoutForLevels = ((LinearLayout)findViewById(R.id.layoutForLevels));
		createCategories();
		CreateDb createDb = new CreateDb(this);
        createDb.start();
	}
	

	private void createCategories() {
		AssetManager assetManager = this.getAssets();
		InputStream inStream  = null;
		InputStreamReader file = null;
		BufferedReader reader = null;
		try {
			inStream = assetManager.open(ConstantValues.CATEGORY+ConstantValues.FILE_EXTENSION);
			file = new InputStreamReader(inStream);
			reader = new BufferedReader(file);
			String category = ConstantValues.CATEGORY;
			while (category!=null) {
				category = reader.readLine();
				if(category!=null){
					TextView levelText = new TextView(this);
					levelText.setOnClickListener(this);
					levelText.setGravity(Gravity.CENTER);
					levelText.setText(category);
					levelText.setBackgroundColor(Color.YELLOW);
					int width = (int) (screenWidth*0.78f);
					int height = (int) (screenHeight*0.208f);
					levelText.setLayoutParams(getLayoutParams(width, height));
					layoutForLevels.addView(levelText);
				} 
			}
		} catch (IOException e) {
			Log.e("",ConstantValues.IO_EXCEPTION);
		}finally{
			
			try {
				inStream.close();
				file.close();
				reader.close();
			} catch (IOException e) {
				Log.e("",ConstantValues.IO_EXCEPTION);
			}
		}
	}
	
	

	private LinearLayout.LayoutParams getLayoutParams(int width,int height){
		LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(width, height);
		param.topMargin = (int) (screenWidth*0.041);
		return param;
	}
	
	

	@Override
	public void onClick(View view) {
		String categoryName = ((TextView)view).getText().toString();
		Intent intent = new Intent(this, SelectPuzzleScreen.class);
		intent.putExtra(ConstantValues.CATEGORY, categoryName);
		startActivity(intent);
	}




}
