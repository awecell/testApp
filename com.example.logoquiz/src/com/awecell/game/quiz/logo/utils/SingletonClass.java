package com.awecell.game.quiz.logo.utils;

import com.awecell.game.quiz.logo.advertisement.MyAdd;

public class SingletonClass {

	private static SingletonClass singleton;
	private MyAdd myAdd;

	public static SingletonClass getSingletonObject(){
		if(singleton==null){
			singleton = new SingletonClass();
		}
		return singleton;
	}
	
	public com.awecell.game.quiz.logo.advertisement.MyAdd getMyAdd(){
		if(myAdd==null){
			myAdd = new MyAdd();
		}
		return myAdd;
	}
}
