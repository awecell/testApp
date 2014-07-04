package com.awecell.game.quiz.logo.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.awecell.game.quiz.logo.R;

public class CustomAdapter extends BaseAdapter{
	
	private Context context;
	private ArrayList<String> data;
	
	public CustomAdapter(Context context,ArrayList<String> data){
		this.context = context;
		this.data = data;
	}

	@Override
	public int getCount() {
		
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null){
			LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.adapter_layout,null,false);
		}
		
		ImageView imageView = (ImageView)convertView.findViewById(R.id.imageViewOnGrid);
		// getting resource id of image in drawable folder
		int resId = context.getResources().getIdentifier(data.get(position), "drawable", "com.awecell.game.quiz.logo");
		imageView.setBackgroundResource(resId);
		imageView.setDrawingCacheEnabled(true);
		
		return convertView;
	}

}
