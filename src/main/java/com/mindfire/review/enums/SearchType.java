package com.mindfire.review.enums;

/**
 * Comment
 * 
 * 
 * @author pratyasa
 */
public enum SearchType {
	
	ALL("ALL"),
	BOOKS("BOOKS"),
	AUTHORS("AUTHORS"),
	GENRES("GENRES"),
	USERS("USERS");
	
	private String code;
	
	private SearchType(String code){
		this.code = code;
	}

	public String getCode() {
		return code;
	}
	
}
