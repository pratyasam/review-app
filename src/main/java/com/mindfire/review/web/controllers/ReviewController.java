package com.mindfire.review.web.controllers;

import com.mindfire.review.exceptions.AlreadyReviewedException;
import com.mindfire.review.exceptions.AuthorExistenceException;
import com.mindfire.review.exceptions.ReviewDoesnotExistException;
import com.mindfire.review.services.AuthorService;
import com.mindfire.review.services.AuthorServiceInterface;
import com.mindfire.review.services.ReviewServiceInterface;
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
    private ReviewServiceInterface reviewService;


    @RequestMapping(value = "/books/{bookId}/review", method = RequestMethod.POST)
    public String addBookReviewByUser(@PathVariable("bookId") Long bookId, @Valid @ModelAttribute("bookProfile") ReviewBookDto reviewBookDto, BindingResult bindingResult, Model model, HttpSession httpSession) {
        if (httpSession.getAttribute("userName") == null) {
            return "redirect:/login";
        }
        if (bindingResult.hasErrors()) {
            return "bookprofile";
        }
        try {
            reviewService.addBookReview(reviewBookDto, (String) httpSession.getAttribute("userName"), bookId);
            return "bookprofile";
        } catch (AlreadyReviewedException e) {
            model.addAttribute("alreadyReviewedException", e);
            return "bookprofile";
        }
    }

    @RequestMapping(value = "/allauthors/{authorId}/review", method = RequestMethod.POST)
    public String addAuthorReviewByUser(@PathVariable("authorId") Long authorId, @Valid @ModelAttribute("authorprofile")ReviewAuthorDto reviewAuthorDto, BindingResult bindingResult, Model model, HttpSession httpSession){
        if(httpSession.getAttribute("userName") == null){
            return "redirect:/login";
        }
        if(bindingResult.hasErrors()){
            return "authorprofile";
        }
        try {
            reviewService.addAuthorReview(reviewAuthorDto, authorId, (String) httpSession.getAttribute("userName"));
            return "authorprofile";
        }
        catch (AlreadyReviewedException e){
            model.addAttribute("alreadyreviewedexception", e);
            return "authorprofile";
        }
    }
    @RequestMapping(value = "/allauthors/{authorId}/{reviewAuthorId}/delete", method = RequestMethod.POST)
    public String removeAuthorReviewByUser(@PathVariable("authorId") Long authorId, @PathVariable("reviewAuthorId") Long reviewAuthorId, HttpSession httpSession, Model model){
        if(httpSession.getAttribute("userName") == null){
            return "redirect:/login";
        }
        if(httpSession.getAttribute("userName") == reviewService.getReviewAuthorById(reviewAuthorId).getUser().getUserName() || httpSession.getAttribute("role").equals("admin")) {
            try {
                reviewService.removeAuthorReview(reviewAuthorId);
                return "authorprofile";
            } catch (ReviewDoesnotExistException e) {
                model.addAttribute("reviewdoesnotexistexception", e);
                return "bookprofile";
            }
        }
        return "authorprofile";
    }
    @RequestMapping(value = "/books/{bookId}/{reviewBookId}/delete", method = RequestMethod.POST)
    public String removeBookReviewByUser(@PathVariable("bookId") Long bookId, @PathVariable("reviewBookId") Long reviewBookId, HttpSession httpSession, Model model){
        if(httpSession.getAttribute("userName") == null){
            return "redirect:/login";
        }
        if(httpSession.getAttribute("userName") == reviewService.getReviewAuthorById(reviewBookId).getUser().getUserName() || httpSession.getAttribute("role").equals("admin")){
            try{
                reviewService.removeBookReview(reviewBookId);
                return "bookprofile";
            } catch (ReviewDoesnotExistException e){
                model.addAttribute("reviewdoesnotexist",e);
                return "bookprofile";
            }
        }
        return "bookprofile";

    }
    @RequestMapping(value = "/allauthors/{authorId}/{reviewAuthorId}/update", method = RequestMethod.GET)
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
        return "authorprofile";

    }
    @RequestMapping(value = "/allauthors/{authorId}/{reviewAuthorId}/update", method = RequestMethod.POST)
    public String updateAuthorReviewPost(@PathVariable("authorId") Long authorId, @PathVariable("reviewAuthorId") Long reviewAuthorId,@Valid @ModelAttribute("authorprofile") ReviewAuthorDto reviewAuthorDto, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "authorprofileupdate";
        }
        try{
            reviewService.updateAuthorReview(reviewAuthorDto,reviewAuthorId);
            return "authorprofile";
        }
        catch (ReviewDoesnotExistException e){
            model.addAttribute("reviewdoesnotexist",e);
            return "authorprofile";
        }
    }
    @RequestMapping(value = "/allauthors/{bookId}/{reviewBookId}/update", method = RequestMethod.GET)
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
        return "bookprofile";
    }
    @RequestMapping(value = "/allauthors/{bookId}/{reviewBookId}/update", method = RequestMethod.POST)
    public String updateBookReviewByUserPost(@PathVariable("bookId") Long bookId, @PathVariable("reviewBookId") Long reviewBookId, @Valid @ModelAttribute("bookprofile") ReviewBookDto reviewBookDto, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "bookprofileupdate";
        }
        try{
            reviewService.updateBookReview(reviewBookDto,reviewBookId);
            return "bookprofile";
        }
        catch(ReviewDoesnotExistException e){
            model.addAttribute("reviewdoesnotexist",e);
            return "bookprofileupdate";
        }
    }


}
