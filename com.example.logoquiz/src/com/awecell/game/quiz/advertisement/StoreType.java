package com.awecell.game.quiz.advertisement;

public class StoreType 
{
	public static boolean GOOGLE = true;
	public static boolean SAMSUNG = false;
	public static boolean AMAZON = false;
	public static boolean OTHERS = false;
	
	public static String admob_id_small = " ";
	public static String admob_id_large = "";
	public static String mInterstitialAdId = "";
	public static String review_url ="";
	
	
	public StoreType() {
		if(SAMSUNG){
			admob_id_small ="";
			admob_id_large = "";
		} else if(AMAZON) {
			admob_id_small ="";
			admob_id_large = "";
		} 
		else if(OTHERS){
			admob_id_small ="";
			admob_id_large = "";
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
