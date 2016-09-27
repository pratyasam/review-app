package com.mindfire.review.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public interface Utility {

	public static final int DEFAULT_PAGE_SIZE = 12;
	public static final int DEFAULT_PAGE = 0;
	public static final int MAXIMUM_PAGE_SIZE = 30;
	
	/**
	 * 
	 * @param limit
	 * @param page
	 * @return
	 */
	public static Pageable buildPageRequest(int limit,int page){
		int p = page - 1;
		int l = (limit < 1 || limit > MAXIMUM_PAGE_SIZE) ? DEFAULT_PAGE_SIZE : limit;
		p = (p < 1) ? DEFAULT_PAGE : p;
		
		return new PageRequest(p, l);
	}
	
}
