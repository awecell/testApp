package com.awecell.game.quiz.logo.screens;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.ImageButton;

import com.awecell.game.quiz.category.logo.R;
import com.awecell.game.quiz.logo.adapters.CustomAdapterForCategoryScreen;
import com.awecell.game.quiz.logo.utils.ConstantValues;
import com.awecell.game.quiz.logo.utils.CreateDb;

public class CategoryScreen extends BaseScreen implements OnClickListener{

	private GridView categoriesView;
	private ArrayList<String> categoriesName = new ArrayList<String>();


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		new LoadCategories().execute();
		CreateDb createDb = new CreateDb(this);
        createDb.start();
	}
	
	private class LoadCategories extends AsyncTask<Void, Void, Void>{
		
		private View view;
		
		@Override
		protected void onPreExecute() {
			LayoutInflater inflator = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflator.inflate(R.layout.category_screen, null,false);
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(Void... params) {
			createCategories();
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					categoriesView = ((GridView)view.findViewById(R.id.categoriesView));
					categoriesView.setAdapter(new CustomAdapterForCategoryScreen(CategoryScreen.this,categoriesName));
					layoutForChildView.removeAllViews();
					layoutForChildView.addView(view);
				}
			});
			super.onPostExecute(result);
		}
		
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
			category = category.trim();
			while (category!=null) {
				category = reader.readLine();
				if(category!=null){
					categoriesName.add(category);
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
	
	

	@Override
	public void onClick(View view) {
		String categoryName = ((ImageButton)view).getTag().toString();
		Intent intent = new Intent(this, SelectPuzzleScreen.class);
		intent.putExtra(ConstantValues.CATEGORY, categoryName);
		startActivity(intent);
	}




}
