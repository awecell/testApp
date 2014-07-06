package com.awecell.game.quiz.logo.database;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.awecell.game.quiz.logo.utils.ConstantClass;

public class DbOpenHelper extends SQLiteOpenHelper{


	private static final String DATABASE_PATH = "//data/data/com.awecell.game.quiz.logo/databases/";
	private static final String DATABASE_NAME = "LogoQuiz.db";
	
	private static final String KEY_ROWID = "_id";
	private static final String KEY_ISANSWERED = "isAnswered";

	private static final int VERSION = 1;
	
	private final Context context;
	private SQLiteDatabase db;
	
	

	public DbOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, VERSION);
		this.context = context;
	}

	
	public void createDatabase(){
		boolean dbExist = checkDatabase();
		if(dbExist){
			//do nothing
		}else{
			//creating empty database
			this.getReadableDatabase();
			//copying database from assets
			copyDatabase();
		}
	}
	
	

	private boolean checkDatabase(){
		SQLiteDatabase checkDb = null;

		try{
			String absolutePath = DATABASE_PATH+DATABASE_NAME;
			checkDb = SQLiteDatabase.openDatabase(absolutePath, null, SQLiteDatabase.OPEN_READWRITE);
		}catch(SQLiteException e){
			Log.e("",ConstantClass.SQLITE_EXCEPTION);
		}
		
		if(checkDb!=null){
			checkDb.close();
		}

		return checkDb != null ? true : false;
	}
	
	
	private void copyDatabase(){
		try {
			InputStream inputStream = context.getAssets().open(DATABASE_NAME);
			
			String outputFileName = DATABASE_PATH+DATABASE_NAME;
			OutputStream outputStream = new FileOutputStream(outputFileName);
			byte[] buffer = new byte[1024];
			int length;
			while((length=inputStream.read(buffer))>0){
				outputStream.write(buffer,0,length);
				
			}
			inputStream.close();
			outputStream.close();
		} catch (IOException e) {
			Log.e("",ConstantClass.IO_EXCEPTION);
		}
	}
	
	public void open(){
		db = getWritableDatabase();
	}
	
	@Override
	public synchronized void close() {
		if(db!=null){
			db.close();
		}
		super.close();
	}
	
	public void updateAnswer(String tableName,int isAnswered ,int rowId){
		String querry = "UPDATE "+tableName+" SET "+KEY_ISANSWERED+" = "+isAnswered+" WHERE "+KEY_ROWID+" = "+rowId;
		db.execSQL(querry);
	}
	
	
	public boolean isAnswered(String tableName,int rowId){
		String querry = "Select "+KEY_ISANSWERED+" FROM "+tableName+" where "+KEY_ROWID+" = "+rowId;
		Cursor cursor = db.rawQuery(querry, null);
		cursor.moveToFirst();
		int isAnswered = cursor.getInt(0);
		cursor.close();
		return isAnswered != 0? true : false;
	}
	
	public Cursor getAnswerList(String tableName){
		String querry = "SELECT "+KEY_ISANSWERED+" FROM "+tableName;
		Cursor cursor = db.rawQuery(querry, null);
		return cursor; 
	}

	
	@Override
	public void onCreate(SQLiteDatabase db) {

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}