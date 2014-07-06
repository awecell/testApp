package com.awecell.game.quiz.logo.screens;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.awecell.game.quiz.logo.R;
import com.awecell.game.quiz.logo.adapters.CustomAdapter;
import com.awecell.game.quiz.logo.database.DbOpenHelper;
import com.awecell.game.quiz.logo.utils.ConstantClass;
import com.awecell.game.quiz.logo.utils.SingletonClass;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

public class LevelDetailScreen extends Activity implements OnItemClickListener{
	
	private String levelName; 
	private GridView gridView;
	private AdView adView;
	private ArrayList<String> data = new ArrayList<String>();
	private ArrayList<Integer> answeredList = new ArrayList<Integer>(); 
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.level_detail_screen);
		Intent intent = getIntent();
		levelName = intent.getStringExtra("levelName");
		((TextView)findViewById(R.id.levelName)).setText(levelName);
		gridView = (GridView)findViewById(R.id.gridView);
		gridView.setOnItemClickListener(this);
		adsLoad();
	}
	
	
	
	private void getAnswerList() {
		DbOpenHelper dbOpenHelper = new DbOpenHelper(LevelDetailScreen.this);
		dbOpenHelper.open();
		Cursor cursor = dbOpenHelper.getAnswerList(levelName);
		cursor.moveToFirst();
		answeredList.removeAll(answeredList);
		while (!cursor.isAfterLast()) {
			answeredList.add(cursor.getInt(0));
	        cursor.moveToNext();
		}
		cursor.close();
		dbOpenHelper.close();
	}

	private void adsLoad() {
		adView = new AdView(LevelDetailScreen.this);
		LinearLayout adslayout = ((LinearLayout)(findViewById(R.id.addOnlevelDetailScreen)));
		SingletonClass.getSingletonObject().getMyAdd().androidGmsAdsLoad(adView, adslayout, AdSize.BANNER);
	}
	
	@Override
	protected void onStart() {
		new ImageLoadingTask().execute();
		super.onStart();
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
	
	
	
	private void getData() {
		AssetManager assetManager = this.getAssets();
		try {
			InputStream inStream = assetManager.open(levelName+".txt");
			InputStreamReader file = new InputStreamReader(inStream);
			BufferedReader reader = new BufferedReader(file);
			String value = "#ff0000";
			data.removeAll(data);
			while (value!=null) {
				value = reader.readLine();
				if(value!=null){
					data.add(value);
				}
			}
			inStream.close();
			file.close();
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();       
		}
	}
	

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		
		Bitmap bitmap = ((ImageView)view.findViewById(R.id.imageViewOnGrid)).getDrawingCache();
		Intent intent = new Intent(this, GameScreen.class);
		intent.putExtra(ConstantClass.LEVEL_NAME, levelName);
		intent.putExtra(ConstantClass.IMAGE, bitmap);
		intent.putExtra(ConstantClass.ANSWER, data.get(position));
		intent.putExtra(ConstantClass.POSITION,++position);
		startActivity(intent);
		
	}
	
	
	private class ImageLoadingTask extends AsyncTask<Void, Void, Void>{
		
		private ProgressDialog progressDialog;
		
		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(LevelDetailScreen.this);
			progressDialog.setTitle(ConstantClass.PROGRESS_DIAOLOG_TITLE);
			progressDialog.setMessage(ConstantClass.PROGRESS_DIALOG_MESSAGE);
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
					gridView.setAdapter(new CustomAdapter(LevelDetailScreen.this,data,answeredList));
				}
			});
			super.onPostExecute(result);
		}
		
	}


}
