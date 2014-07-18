package com.awecell.game.quiz.logo.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;

import com.awecell.game.quiz.category.logo.R;
import com.awecell.game.quiz.logo.utils.ConstantValues;

public class CustomAdapterForCategoryScreen extends BaseAdapter{
	
	private ArrayList<String> categoriesName ;
	private Context context;
	
	public CustomAdapterForCategoryScreen(Context context,ArrayList<String> categoriesName){
		this.context = context;
		this.categoriesName = categoriesName;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return categoriesName.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return categoriesName.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.adapter_layout_category_screen, null,false);
		}
		String category = categoriesName.get(position);
		ImageButton categoryButton = ((ImageButton)convertView.findViewById(R.id.categoryButton));
		categoryButton.setTag(category);
		int id = context.getResources().getIdentifier(category+ConstantValues.XML,ConstantValues.DRAWABLE, ConstantValues.PACKAGE_NAME);
		categoryButton.setBackgroundResource(id);
		categoryButton.setOnClickListener((OnClickListener)context);
		return convertView;
	}

}
