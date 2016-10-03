package com.mindfire.review.web.controllers;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.mindfire.review.exceptions.AuthorExistenceException;
import com.mindfire.review.services.AuthorService;
import com.mindfire.review.services.BookService;
import com.mindfire.review.services.ReviewService;
import com.mindfire.review.web.dto.AuthorDto;
import com.mindfire.review.web.dto.BookAuthorListDto;
import com.mindfire.review.web.dto.ChoiceDto;
import com.mindfire.review.web.dto.ReviewAuthorDto;
import com.mindfire.review.web.dto.SearchDto;
import com.mindfire.review.web.models.Author;
import com.mindfire.review.web.models.Book;
import com.mindfire.review.web.models.ReviewAuthor;
import com.mindfire.review.web.models.User;

/**
 * Created by pratyasa on 10/8/16.
 */
@Controller
public class AuthorController {
    @Autowired
    private ReviewService reviewService;

    @Autowired
    private AuthorService authorService;
    @Autowired
    private BookService bookService;

    /**
     * to get the add author page by the admin or moderator
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/addauthor", method = RequestMethod.GET)
    public Object addAuthorGet(HttpSession httpSession){
        if(httpSession.getAttribute("userName") != null && (httpSession.getAttribute("role").equals("admin") || httpSession.getAttribute("role").equals("moderator"))){
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
        if(httpSession.getAttribute("userName") != null && (httpSession.getAttribute("role").equals("admin") || httpSession.getAttribute("role").equals("moderator"))){
            if(bindingResult.hasErrors()){
                return "addauthor";
            }
            try{
                authorService.addAuthor(authorDto);
                httpSession.setAttribute("authorName",authorDto.getAuthorName());
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


    @RequestMapping(value = "/authors" , method = RequestMethod.GET)
    public ModelAndView getAllAuthors(@RequestParam(value="pageno", defaultValue="1") int pageno){
    	Page<Author> authorpage = authorService.getAllAuthor(pageno, 6);
    	List<Author> authors= authorpage.getContent();
    	int totalPage = authorpage.getTotalPages();
    	ModelAndView modelAndView =new ModelAndView("authors");
    	modelAndView.addObject("totalpage",totalPage);
    	modelAndView.addObject("authors", authors);
    	modelAndView.addObject("search", new SearchDto());
        return modelAndView;
    }

    /**
     * to view the author profile separately by admin/moderator and normal user
     * @param authorId
     * @param httpSession
     * @return
     */

    @RequestMapping(value = "/authors/{authorId}", method = RequestMethod.GET)
    public Object getSingleAuthor(@RequestParam(value="pagenob",defaultValue="1") int pagenob, @RequestParam(value="pagenou",defaultValue="1") int pagenou, @RequestParam(value="pagenor",defaultValue="1") int pagenor,@PathVariable("authorId") Long authorId, HttpSession httpSession){
        String authorName = authorService.getAuthorById(authorId).getAuthorName();
        Page<Book> book = authorService.getBookByAuthor(authorName,pagenob,6);
        Page<ReviewAuthor> reviewAuthors = authorService.getAuthorReviewByAuthorName(authorName, pagenob, 6);
        Page<User> users = authorService.getUserByAuthor(authorName, pagenob, 6);
        int totalpagesr = reviewAuthors.getTotalPages();
        int totalpagesb = book.getTotalPages();
        int totalpagesu = users.getTotalPages();
        List<BookAuthorListDto> bookAuthorListDto = new ArrayList<>();
        ReviewAuthorDto reviewAuthorDto = new ReviewAuthorDto();

		for (Book b : book.getContent()) {

			BookAuthorListDto bookAuthorListDto2 = new BookAuthorListDto();
			List<Author> authors = bookService.getAuthorByBook(b.getBookName());
			bookAuthorListDto2.setAuthorList(authors);
			bookAuthorListDto2.setBook(b);
			bookAuthorListDto.add(bookAuthorListDto2);
		}
        if(httpSession.getAttribute("userName") != null && (httpSession.getAttribute("role").equals("admin") || httpSession.getAttribute("role").equals("moderator"))){
           if(httpSession.getAttribute("review") != null){
        	   reviewAuthorDto = (ReviewAuthorDto) httpSession.getAttribute("review");
           }
        	ModelAndView modelAndView = new ModelAndView("adminauthorprofile");
            modelAndView.addObject("author",authorService.getAuthorById(authorId));
            modelAndView.addObject("delete",new ChoiceDto());;
            modelAndView.addObject("authorprofile",reviewAuthorDto);
            modelAndView.addObject("reviews",reviewAuthors.getContent());
            modelAndView.addObject("books",bookAuthorListDto);
            modelAndView.addObject("delete", new ChoiceDto());
            modelAndView.addObject("authorId",authorId);
            modelAndView.addObject("users", users.getContent());
            modelAndView.addObject("totalpagesr",totalpagesr);
            modelAndView.addObject("totalpagesb", totalpagesb);
            modelAndView.addObject("totalpagesu", totalpagesu);
            System.out.println("Admin");
            return modelAndView;
        }
        if(httpSession.getAttribute("review") != null){
     	   reviewAuthorDto = (ReviewAuthorDto) httpSession.getAttribute("review");
        }
       ModelAndView modelAndView = new ModelAndView("authorprofile");
        modelAndView.addObject("author",authorService.getAuthorById(authorId));
        modelAndView.addObject("authorprofile",reviewAuthorDto);
        modelAndView.addObject("reviews",reviewAuthors.getContent());
        modelAndView.addObject("users", users.getContent());
        modelAndView.addObject("books",bookAuthorListDto);
        modelAndView.addObject("totalpagesr",totalpagesr);
        modelAndView.addObject("totalpagesb", totalpagesb);
        modelAndView.addObject("totalpagesu", totalpagesu);
        System.out.println("User");
        return modelAndView;
    }

    /**
     * to update the author info by the admin and the moderator
     * @param authorId
     * @param httpSession
     * @return
     */

    @RequestMapping(value = "/authors/{authorId}/update", method = RequestMethod.GET)
    public Object updateAuthorGet(@PathVariable("authorId") Long authorId ,HttpSession httpSession){
        if(httpSession.getAttribute("userName") != null && (httpSession.getAttribute("role").equals("admin") || httpSession.getAttribute("role").equals("moderator"))){
            ModelAndView modelAndView = new ModelAndView("authorupdate");
            modelAndView.addObject("author",authorService.getAuthorById(authorId));
            Author author = authorService.getAuthorById(authorId);
            AuthorDto authorDto =  new AuthorDto();
            authorDto.setAuthorDescription(author.getAuthorDescription());
            authorDto.setAuthorGenre(author.getAuthorGenre());
            authorDto.setAuthorName(author.getAuthorName());
            authorDto.setAuthorRating(author.getAuthorRating());
            modelAndView.addObject("authorupdatedto",authorDto);
            return modelAndView;
        }
        return "redirect:/authors/{authorId}";
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
    @RequestMapping(value = "/authors/{authorId}", method = RequestMethod.PUT)
    public String updateAuthorPost(@PathVariable("authorId") Long authorId, @Valid @ModelAttribute("authorupdatedto") AuthorDto authorDto, BindingResult bindingResult, Model model,HttpSession httpSession){
        if(bindingResult.hasErrors()){
            return "authorupdate";
        }
        try{
            authorService.updateAuthor(authorDto, authorId);
            return "redirect:/authors/{authorId}";
        }
        catch (AuthorExistenceException e){
            model.addAttribute("authordoesnotexist",e);
            return "authorupdate";
        }
    }

    /**
     * to delete the author by the admin
     * @param authorId
     * @param choiceDto
     * @param model
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/authors/{authorId}", method = RequestMethod.DELETE)
    public String removeAuthor(@PathVariable("authorId") Long authorId, @Valid @ModelAttribute("delete")ChoiceDto choiceDto, Model model, HttpSession httpSession){
        if (httpSession.getAttribute("userName") != null && (httpSession.getAttribute("role").equals("admin"))){
            try{
                authorService.removeAuthor(authorId);
                return "redirect:/authors";
            }
            catch (AuthorExistenceException e){
                model.addAttribute("authordoesnotexist",e);
                return "redirect:/authors/{authorId}";
            }
        }
        return "redirect/login";
    }
}
