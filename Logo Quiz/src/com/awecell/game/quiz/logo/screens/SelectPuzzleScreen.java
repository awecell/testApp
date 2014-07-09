package com.awecell.game.quiz.logo.screens;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.awecell.game.quiz.category.logo.R;
import com.awecell.game.quiz.logo.adapters.CustomAdapter;
import com.awecell.game.quiz.logo.database.DbHelper;
import com.awecell.game.quiz.logo.utils.ConstantValues;

public class SelectPuzzleScreen extends BaseScreen implements OnItemClickListener{
	
	private String categoryName; 
	private GridView gridView;
	private ArrayList<String> logoNameList = new ArrayList<String>();
	private ArrayList<String> hint1List = new ArrayList<String>();
	private ArrayList<String> hint2List = new ArrayList<String>();
	private ArrayList<Integer> answeredList = new ArrayList<Integer>(); 
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.level_detail_screen);
		LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.puzzle_screen,null,false);
		layoutForChildView.addView(view);
		Intent intent = getIntent();
		categoryName = intent.getStringExtra(ConstantValues.CATEGORY);
		((TextView)findViewById(R.id.levelName)).setText(categoryName);
		gridView = (GridView)findViewById(R.id.gridView);
		gridView.setOnItemClickListener(this);
		
	}
	
	
	
	private void getAnswerList() {
		DbHelper dbOpenHelper = new DbHelper(SelectPuzzleScreen.this);
		dbOpenHelper.open();
		Cursor cursor = dbOpenHelper.getAnswerList(categoryName);
		cursor.moveToFirst();
		answeredList.removeAll(answeredList);
		while (!cursor.isAfterLast()) {
			answeredList.add(cursor.getInt(0));
	        cursor.moveToNext();
		}
		cursor.close();
		dbOpenHelper.close();
	}

	
	
	@Override
	protected void onStart() {
		new ImageLoadingTask().execute();
		super.onStart();
	}
	
	
	
	private void getData() {
		AssetManager assetManager = this.getAssets();
		InputStream inStream = null;
		InputStreamReader file = null;
		BufferedReader reader = null;
		try {
			inStream = assetManager.open(categoryName+".txt");
			file = new InputStreamReader(inStream);
			reader = new BufferedReader(file);
			String value = "#ff0000";
			logoNameList.removeAll(logoNameList);
			while (value!=null) {
				value = reader.readLine();
				
				if(value!=null){
					String[] splitedValue = value.split("\\|\\|");
					logoNameList.add(splitedValue[0]);
					hint1List.add(splitedValue[1]);
					hint2List.add(splitedValue[2]);
				}
			}
		} catch (IOException e) {
			Log.e("",ConstantValues.IO_EXCEPTION);
			e.printStackTrace();       
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
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		
		Bitmap bitmap = ((ImageView)view.findViewById(R.id.imageViewOnGrid)).getDrawingCache();
		Intent intent = new Intent(this, GameScreen.class);
		intent.putExtra(ConstantValues.CATEGORY, categoryName);
		intent.putExtra(ConstantValues.IMAGE, bitmap);
		intent.putExtra(ConstantValues.ANSWER, logoNameList.get(position));
		intent.putExtra(ConstantValues.HINT1, hint1List.get(position));
		intent.putExtra(ConstantValues.HINT2, hint2List.get(position));
		intent.putExtra(ConstantValues.POSITION,++position);
		startActivity(intent);
		
	}
	
	
	private class ImageLoadingTask extends AsyncTask<Void, Void, Void>{
		
		private ProgressDialog progressDialog;
		
		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(SelectPuzzleScreen.this);
			progressDialog.setTitle(ConstantValues.PROGRESS_DIAOLOG_TITLE);
			progressDialog.setMessage(ConstantValues.PROGRESS_DIALOG_MESSAGE);
			progressDialog.setCanceledOnTouchOutside(false);
			progressDialog.setCancelable(false);
			progressDialog.show();
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(Void... params) {
			getData();
			getAnswerList();
			return null;
		}
		
		
		@Override
		protected void onPostExecute(Void result) {
			progressDialog.cancel();
			runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					gridView.setAdapter(new CustomAdapter(SelectPuzzleScreen.this,logoNameList,answeredList));
				}
			});
			super.onPostExecute(result);
		}
		
	}


}
