package com.mindfire.review.web.controllers;


import com.mindfire.review.exceptions.AuthorExistenceException;
import com.mindfire.review.services.AuthorServiceInterface;
import com.mindfire.review.services.ReviewServiceInterface;
import com.mindfire.review.web.dto.AuthorDto;
import com.mindfire.review.web.dto.ReviewAuthorDto;
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
public class AuthorController {
    @Autowired
    private ReviewServiceInterface reviewService;

    @Autowired
    private AuthorServiceInterface authorService;

    /**
     * to get the add author page by the admin or moderator
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/addauthor", method = RequestMethod.GET)
    public Object addAuthorGet(HttpSession httpSession){
        if(httpSession.getAttribute("uerName") != null && (httpSession.getAttribute("role").equals("admin") || httpSession.getAttribute("role").equals("moderator"))){
            return new ModelAndView("addauthor","addauthor",new AuthorDto());
        }
        return "redirect:/login";

    }

    /**
     * to add the author by the admin and the moderator
     * @param authorDto
     * @param bindingResult
     * @param model
     * @param httpSession
     * @return
     */

    @RequestMapping(value = "/addauthor", method = RequestMethod.POST)
    public String addAuthorPost(@Valid @ModelAttribute("addauthor") AuthorDto authorDto, BindingResult bindingResult, Model model, HttpSession httpSession){
        if(httpSession.getAttribute("uerName") != null && (httpSession.getAttribute("role").equals("admin") || httpSession.getAttribute("role").equals("moderator"))){
            if(bindingResult.hasErrors()){
                return "addauthor";
            }
            try{
                authorService.addAuthor(authorDto);
                return "authoradded";
            }
            catch (AuthorExistenceException e){
                model.addAttribute("authorExists",e);
                return "addauthor";
            }
        }
        return "redirect:/login";
    }

    /**
     * to view all authors by all
     * @return
     */


    @RequestMapping(value = "/allauthors" , method = RequestMethod.GET)
    public String getAllAuthors(){
        return "allAuthor";
    }

    /**
     * to view the author profile separately by admin/moderator and normal user
     * @param authorId
     * @param httpSession
     * @return
     */

    @RequestMapping(value = "/allauthors/{authorId}", method = RequestMethod.GET)
    public Object getSingleAuthor(@PathVariable("authorId") Long authorId, HttpSession httpSession){
        if(httpSession.getAttribute("uerName") != null && (httpSession.getAttribute("role").equals("admin") || httpSession.getAttribute("role").equals("moderator"))){
            ModelAndView modelAndView = new ModelAndView("adminauthorprofile");
            modelAndView.addObject("author",authorService.getAuthorById(authorId));
            return modelAndView;
        }
       ModelAndView modelAndView = new ModelAndView("authorprofile");
        modelAndView.addObject("author",authorService.getAuthorById(authorId));
        modelAndView.addObject("authorprofile",new ReviewAuthorDto());
        return modelAndView;
    }

    /**
     * to update the author info by the admin and the moderator
     * @param authorId
     * @param httpSession
     * @return
     */

    @RequestMapping(value = "/allauthors/{authorId}/update", method = RequestMethod.GET)
    public Object updateAuthorGet(@PathVariable("authorId") Long authorId ,HttpSession httpSession){
        if(httpSession.getAttribute("uerName") != null && (httpSession.getAttribute("role").equals("admin") || httpSession.getAttribute("role").equals("moderator"))){
            ModelAndView modelAndView = new ModelAndView("authorupdate");
            modelAndView.addObject("author", authorService.getAuthorById(authorId));
            modelAndView.addObject("authorupdatedto", new ReviewAuthorDto());
            return modelAndView;
        }
        return "redirect:/login";
    }

    /**
     * to post the updated info by the admin and the moderator
     * @param authorId
     * @param authorDto
     * @param bindingResult
     * @param model
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/allauthors/{authorId}/update", method = RequestMethod.POST)
    public String updateAuthorPost(@PathVariable("authorId") Long authorId, @Valid @ModelAttribute("authorupdatedto") AuthorDto authorDto, BindingResult bindingResult, Model model,HttpSession httpSession){
        if(bindingResult.hasErrors()){
            return "authorupdate";
        }
        try{
            authorService.updateAuthor(authorDto, authorId);
            return "allauthors";
        }
        catch (AuthorExistenceException e){
            model.addAttribute("authordoesnotexist",e);
            return "authorupdate";
        }
    }

    /**
     * to delete the author by the admin
     * @param authorId
     * @param authorDto
     * @param bindingResult
     * @param model
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/allauthors/{authorId}", method = RequestMethod.DELETE)
    public String removeAuthor(@PathVariable("authorId") Long authorId, @Valid @ModelAttribute("authorupdatedto") AuthorDto authorDto, BindingResult bindingResult, Model model, HttpSession httpSession){
        if (httpSession.getAttribute("userName") != null && (httpSession.getAttribute("role").equals("admin"))){
            try{
                authorService.removeAuthor(authorId);
                return "allauthors";
            }
            catch (AuthorExistenceException e){
                model.addAttribute("authordoesnotexist",e);
                return "adminauthorprofile";
            }
        }
        return "redirect/login";
    }
}
