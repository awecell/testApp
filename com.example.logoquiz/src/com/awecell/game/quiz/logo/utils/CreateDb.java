package com.awecell.game.quiz.logo.utils;

import android.content.Context;
import android.os.Handler;

import com.awecell.game.quiz.logo.database.DbOpenHelper;

public class CreateDb implements Runnable{
	
	private Context context ;
	
	public CreateDb(Context context){
		this.context = context;
	}
	
	public void start(){
		Handler handler = new Handler();
		handler.post(this);
	}

	@Override
	public void run() {
		DbOpenHelper dbOpenHelper = new DbOpenHelper(context);
		dbOpenHelper.createDatabase();
		dbOpenHelper.close();
	}

}
