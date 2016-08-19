package com.mindfire.review.web.controllers;

import com.mindfire.review.services.SearchService;
import com.mindfire.review.services.SearchServiceImpl;
import com.mindfire.review.web.dto.SearchDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 * ${}
 * Created by pratyasa on 18/8/16.
 */
@SessionAttributes("search")
@Controller
public class SearchController {
    @Autowired
    private SearchService searchService;
    @RequestMapping(value = "/search",method = RequestMethod.GET)
    public String searchGet(@ModelAttribute("search")SearchDto searchDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "null";
        }
        searchService.search(searchDto);
        return "searchresult";

    }
}
