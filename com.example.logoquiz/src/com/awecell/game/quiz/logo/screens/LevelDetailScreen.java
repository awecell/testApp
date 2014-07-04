package com.awecell.game.quiz.logo.screens;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.awecell.game.quiz.logo.R;
import com.awecell.game.quiz.logo.adapters.CustomAdapter;

public class LevelDetailScreen extends Activity implements OnItemClickListener{
	
	private String levelName; 
	private GridView gridView;
	private ArrayList<String> data = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.level_detail_screen);
		Intent intent = getIntent();
		levelName = intent.getStringExtra("levelName");
		((TextView)findViewById(R.id.levelName)).setText(levelName);
		getData();
		gridView = (GridView)findViewById(R.id.gridView);
		gridView.setAdapter(new CustomAdapter(this,data));
		gridView.setOnItemClickListener(this);
	}
	
	private void getData() {
		AssetManager assetManager = this.getAssets();
		try {
			InputStream inStream = assetManager.open(levelName+".txt");
			InputStreamReader file = new InputStreamReader(inStream);
			BufferedReader reader = new BufferedReader(file);
			String value = "#ff0000";
			data.remove(data);
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
		intent.putExtra("levelName", levelName);
		intent.putExtra("image", bitmap);
		intent.putExtra("answer", data.get(position));
		startActivity(intent);
		
	}


}
