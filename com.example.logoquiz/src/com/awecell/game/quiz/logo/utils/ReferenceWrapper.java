package com.awecell.game.quiz.logo.utils;

import com.awecell.game.quiz.logo.advertisement.CustomAd;

public class ReferenceWrapper {

	private static ReferenceWrapper referenceWrapper;
	private CustomAd customAd;
	
	private ReferenceWrapper(){ }

	public static synchronized ReferenceWrapper getReferenceWrapper(){
		if(referenceWrapper==null){
			referenceWrapper = new ReferenceWrapper();
		}
		return referenceWrapper;
	}
	
	public CustomAd getMyAdd(){
		if(customAd==null){
			customAd = new CustomAd();
		}
		return customAd;
	}
	
}
