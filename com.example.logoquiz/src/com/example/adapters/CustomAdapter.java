package com.example.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.example.logoquiz.R;

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
		imageView.setBackgroundColor(Color.parseColor(data.get(position)));
		
		return convertView;
	}

}
