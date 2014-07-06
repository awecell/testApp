package com.awecell.game.quiz.logo.utils;

import android.content.Context;
import android.os.Handler;

import com.awecell.game.quiz.logo.database.DbOpenHelper;

public class UpdateDb implements Runnable{
	
	private Context context;
	private String tableName;
	private int rowId;
	private int answer;
	
	public UpdateDb(Context context,String tableName,int answer,int rowId){
		this.context = context;
		this.tableName = tableName;
		this.answer = answer;
		this.rowId = rowId;
	}
	
	public void start(){
		Handler handler = new Handler();
		handler.post(this);
	}

	@Override
	public void run() {
		DbOpenHelper dbOpenHelper = new DbOpenHelper(context);
		dbOpenHelper.open();
		dbOpenHelper.updateAnswer(tableName, answer, rowId);
		dbOpenHelper.close();
	}

}
