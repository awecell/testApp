package com.awecell.game.quiz.logo.advertisement;

public class StoreType 
{
	public static boolean GOOGLE = true;
	public static boolean SAMSUNG = false;
	public static boolean AMAZON = false;
	public static boolean OTHERS = false;
	
	public static String admob_id = " ";
	public static String mInterstitialAdId = "";
	public static String review_url ="";
	
	
	public StoreType() {
		if(SAMSUNG){
			admob_id ="";
		} else if(AMAZON) {
			admob_id ="";
		} 
		else if(OTHERS){
			admob_id ="";
		}
	}
	
	public static String getTrueValue(){
		if(SAMSUNG){
			return "Samsung";
		}else if(AMAZON){
			return "Amazon";
		}
		return "GOOGLE";
	}

}
