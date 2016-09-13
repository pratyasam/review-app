package com.mindfire.review.web.controllers;

import com.mindfire.review.exceptions.AlreadyReviewedException;
import com.mindfire.review.exceptions.ReviewDoesnotExistException;
import com.mindfire.review.services.ReviewService;
import com.mindfire.review.web.dto.ReviewAuthorDto;
import com.mindfire.review.web.dto.ReviewBookDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * Created by pratyasa on 10/8/16.
 */
@Controller
public class ReviewController {
    @Autowired
    private ReviewService reviewService;


    @RequestMapping(value = "/books/{bookId}", method = RequestMethod.POST)
    public String addBookReviewByUser(@PathVariable("bookId") Long bookId, @Valid @ModelAttribute("bookProfile") ReviewBookDto reviewBookDto, BindingResult bindingResult, Model model, HttpSession httpSession) {
       System.out.println("entered");
    	if (httpSession.getAttribute("userName") == null) {
            return "redirect:/login";
        }
        if (bindingResult.hasErrors()) {
            return "redirect:/books/{bookId}";
        }
        try {
            reviewService.addBookReview(reviewBookDto, (String) httpSession.getAttribute("userName"), bookId);
            System.out.println("review saved.");
            return "redirect:/books/{bookId}";
        } catch (AlreadyReviewedException e) {
            model.addAttribute("alreadyReviewedException", e);
            return "redirect:/books/{bookId}";
        }
    }

    @RequestMapping(value = "/authors/{authorId}/review", method = RequestMethod.POST)
    public String addAuthorReviewByUser(@PathVariable("authorId") Long authorId, @Valid @ModelAttribute("authorprofile")ReviewAuthorDto reviewAuthorDto, BindingResult bindingResult, Model model, HttpSession httpSession){
        if(httpSession.getAttribute("userName") == null){
            return "redirect:/login";
        }
        if(bindingResult.hasErrors()){
            return "redirect:/authors/{authorId}";
        }
        try {
            reviewService.addAuthorReview(reviewAuthorDto, authorId, (String) httpSession.getAttribute("userName"));
            return "authorprofile";
        }
        catch (AlreadyReviewedException e){
            model.addAttribute("alreadyreviewedexception", e);
            return "redirect:/authors/{authorId}";
        }
    }
    @RequestMapping(value = "/authors/{authorId}/{reviewAuthorId}", method = RequestMethod.DELETE)
    public String removeAuthorReviewByUser(@PathVariable("authorId") Long authorId, @PathVariable("reviewAuthorId") Long reviewAuthorId, HttpSession httpSession, Model model){
        if(httpSession.getAttribute("userName") == null){
            return "redirect:/login";
        }
        if(httpSession.getAttribute("userName") == reviewService.getReviewAuthorById(reviewAuthorId).getUser().getUserName() || httpSession.getAttribute("role").equals("admin")) {
            try {
                reviewService.removeAuthorReview(reviewAuthorId);
                return "redirect:/authors/{authorId}";
            } catch (ReviewDoesnotExistException e) {
                model.addAttribute("reviewdoesnotexistexception", e);
                return "redirect:/authors/{authorId}";
            }
        }
        return "redirect:/authors/{authorId}";
    }
    @RequestMapping(value = "/books/{bookId}/{reviewBookId}", method = RequestMethod.DELETE)
    public String removeBookReviewByUser(@PathVariable("bookId") Long bookId, @PathVariable("reviewBookId") Long reviewBookId, HttpSession httpSession, Model model){
        if(httpSession.getAttribute("userName") == null){
            return "redirect:/login";
        }
        if(httpSession.getAttribute("userName") == reviewService.getReviewAuthorById(reviewBookId).getUser().getUserName() || httpSession.getAttribute("role").equals("admin")){
            try{
                reviewService.removeBookReview(reviewBookId);
                return "redirect:/books/{bookId}";
            } catch (ReviewDoesnotExistException e){
                model.addAttribute("reviewdoesnotexist",e);
                return "redirect:/books/{bookId}";
            }
        }
        return "redirect:/books/{bookId}";

    }
    @RequestMapping(value = "/authors/{authorId}/{reviewAuthorId}/update", method = RequestMethod.GET)
    public Object updateAuthorReviewByUser(@PathVariable("authorId") Long authorId, @PathVariable("reviewAuthorId") Long reviewAuthorId, HttpSession httpSession){
        if(httpSession.getAttribute("userName") == null){
            return "redirect:/login";
        }
        if( httpSession.getAttribute("userName") == reviewService.getReviewAuthorById(reviewAuthorId).getUser().getUserName()){
            ModelAndView modelAndView = new ModelAndView("authorprofileupdate");
            modelAndView.addObject("authorprofile", new ReviewAuthorDto());
            modelAndView.addObject("reviewauthor", reviewService.getReviewAuthorById(reviewAuthorId));
            return modelAndView;
        }
        return "redirect:/authors/{authorId}";

    }
    @RequestMapping(value = "/authors/{authorId}/{reviewAuthorId}", method = RequestMethod.PUT)
    public String updateAuthorReviewPost(@PathVariable("authorId") Long authorId, @PathVariable("reviewAuthorId") Long reviewAuthorId,@Valid @ModelAttribute("authorprofile") ReviewAuthorDto reviewAuthorDto, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "redirect:/authors/{authorId}/{reviewAuthorId}/update";
        }
        try{
            reviewService.updateAuthorReview(reviewAuthorDto,reviewAuthorId);
            return "redirect:/authors/{authorId}";
        }
        catch (ReviewDoesnotExistException e){
            model.addAttribute("reviewdoesnotexist",e);
            return "redirect:/authors/{authorId}/update";
        }
    }
    @RequestMapping(value = "/books/{bookId}/{reviewBookId}/update", method = RequestMethod.GET)
    public Object updateBookReviewByUser(@PathVariable("bookId") Long bookId, @PathVariable("reviewBookId") Long reviewBookId, HttpSession httpSession){
        if(httpSession.getAttribute("userName") == null){
            return "redirect:/login";
        }
        if( httpSession.getAttribute("userName") == reviewService.getReviewBookById(reviewBookId).getUser().getUserName()){
            ModelAndView modelAndView = new ModelAndView("bookprofileupdate");
            modelAndView.addObject("reviewbook", reviewService.getReviewBookById(reviewBookId));
            modelAndView.addObject("bookprofileupdate",new ReviewBookDto());
            return modelAndView;
        }
        return "redirect:/books/{bookId}";
    }
    @RequestMapping(value = "/allauthors/{bookId}/{reviewBookId}", method = RequestMethod.PUT)
    public String updateBookReviewByUserPost(@PathVariable("bookId") Long bookId, @PathVariable("reviewBookId") Long reviewBookId, @Valid @ModelAttribute("bookprofile") ReviewBookDto reviewBookDto, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "redirect:/allauthors/{bookId}/{reviewBookId}/update";
        }
        try{
            reviewService.updateBookReview(reviewBookDto,reviewBookId);
            return "redirect:/books/{bookId}";
        }
        catch(ReviewDoesnotExistException e){
            model.addAttribute("reviewdoesnotexist",e);
            return "redirect:/books/{bookId}/{reviewBookId}/update";
        }
    }


}
