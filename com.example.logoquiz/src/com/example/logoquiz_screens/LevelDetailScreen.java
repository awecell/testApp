package com.example.logoquiz_screens;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.TextView;

import com.example.adapters.CustomAdapter;
import com.example.logoquiz.R;

public class LevelDetailScreen extends Activity {
	
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


}
