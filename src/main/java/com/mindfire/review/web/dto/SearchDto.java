package com.mindfire.review.web.dto;

import javax.validation.constraints.NotNull;

/**
 * ${}
 * Created by pratyasa on 18/8/16.
 */
public class SearchDto {
    public String getSearchParam() {
        return searchParam;
    }

    public void setSearchParam(String searchParam) {
        this.searchParam = searchParam;
    }

    @NotNull(message = "Field cannot be null.")
    private String searchParam;
}
