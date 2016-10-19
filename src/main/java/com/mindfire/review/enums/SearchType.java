package com.mindfire.review.enums;

/**
 * Comment
 * 
 * 
 * @author pratyasa
 */
public enum SearchType {
	
	BOOKS("BOOKS"),
	AUTHORS("AUTHORS"),
	USERS("USERS");
	
	private String code;
	
	private SearchType(String code){
		this.code = code;
	}

	public String getCode() {
		return code;
	}
	
}
