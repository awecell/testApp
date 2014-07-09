package com.awecell.game.quiz.logo.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.awecell.game.quiz.category.logo.R;
import com.awecell.game.quiz.logo.utils.ConstantValues;

public class CustomAdapter extends BaseAdapter{

	private Context context;
	private ArrayList<String> data;
	private ArrayList<Integer> answeredList;

	public CustomAdapter(Context context,ArrayList<String> data,ArrayList<Integer> answeredList){
		this.context = context;
		this.data = data;
		this.answeredList = answeredList;
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
	public boolean isEnabled(int position) {
		if(position==0){
			return true;
		}else if(answeredList.get(position-1)==ConstantValues.ANSWERED){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null){
			LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.adapter_layout,null,false);
		}

		ImageView imageView = (ImageView)convertView.findViewById(R.id.imageViewOnGrid);
		// getting resource id of image in drawable folder
		int resId = context.getResources().getIdentifier(data.get(position),ConstantValues.DRAWABLE, ConstantValues.PACKAGE_NAME);
		imageView.setBackgroundResource(resId);
		imageView.setDrawingCacheEnabled(true);
		
		//initial puzzle will be unlocked
              if(position == 0){
            	  ((ImageView)convertView.findViewById(R.id.lockImage)).setVisibility(View.GONE);
              }else if(answeredList.get(position-1)==ConstantValues.ANSWERED){
            	  ((ImageView)convertView.findViewById(R.id.lockImage)).setVisibility(View.GONE);
              }
		
		
		//checking whether this logo is answered or not 
			if(answeredList.get(position)==ConstantValues.ANSWERED){
				((ImageView)convertView.findViewById(R.id.tickImage)).setVisibility(View.VISIBLE);
			}
			
		return convertView;
	}

}
