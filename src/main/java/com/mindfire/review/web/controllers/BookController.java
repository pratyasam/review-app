package com.mindfire.review.web.controllers;

import com.mindfire.review.exceptions.BookDoesNotExistException;
import com.mindfire.review.exceptions.BookExistException;
import com.mindfire.review.services.BookService;
import com.mindfire.review.web.dto.BookDto;
import com.mindfire.review.web.dto.DeleteDto;
import com.mindfire.review.web.dto.ReviewBookDto;
import com.mindfire.review.web.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * Created by pratyasa on 3/8/16.
 */
@Controller
public class BookController {
    @Autowired
    private BookService bookService;

    /**
     * get book add page for moderator and Admin
     *
     * @param httpSession
     * @return
     */

    @RequestMapping(value = "/addbook", method = RequestMethod.GET)
    public Object bookController(HttpSession httpSession) {
        if (httpSession.getAttribute("userName") != null && (httpSession.getAttribute("role").equals("admin") || httpSession.getAttribute("role").equals("moderator"))) {

            return new ModelAndView("addbook", "book", new BookDto());
        } else {

            return "redirect:/login";
        }
    }

    /**
     * the book add page info to be persisted by admin or moderator
     *
     * @param bookDto
     * @param bindingResult
     * @param model
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/addbook", method = RequestMethod.POST)
    public String addBook(@Valid @ModelAttribute("book") BookDto bookDto, BindingResult bindingResult, Model model, HttpSession httpSession) {
        if (bindingResult.hasErrors()) {
            return "addbook";
        }
        try {

            bookService.addBook(bookDto);
            model.addAttribute("bookName", bookDto.getBookName());
            return "bookadded";

        } catch (BookExistException bex) {
            model.addAttribute("bookExist", bex);
            return "addbook";
        }
    }

    /**
     *
     * @param bookId
     * @param bookDto
     * @param bindingResult
     * @param model
     * @param httpSession
     * @return
     */

    @RequestMapping(value = "books/{bookId}/verifybooks", method = RequestMethod.POST)
    public String verifyBook(@PathVariable("bookId") Long bookId, @Valid @ModelAttribute("allbooks") BookDto bookDto, BindingResult bindingResult, Model model, HttpSession httpSession) {
        if (httpSession.getAttribute("userName") != null && ((httpSession.getAttribute("role").equals("admin")) || httpSession.getAttribute("role").equals("moderator"))) {
            if (httpSession.getAttribute("userName") != null && (httpSession.getAttribute("role").equals("admin") || httpSession.getAttribute("role").equals("moderator"))) {
                bookService.verifyBook(bookId);
                return "redirect:/books";
            }
        }

        return "null";
    }


    /**
     * to get all verified books
     * @return
     */
    @RequestMapping(value = "/books", method = RequestMethod.GET)
    public Object getAllBooks(HttpSession httpSession){
        if (httpSession.getAttribute("userName") != null && (httpSession.getAttribute("role").equals("admin") || httpSession.getAttribute("role").equals("moderator"))) {
            Page<Book> bookList = bookService.getBooks(1, 10); // TODO Add pagination
            ModelAndView modelAndView = new ModelAndView("adminallbook");
            modelAndView.addObject("bookList", bookList.getContent());
            modelAndView.addObject("pages", bookList.getTotalPages());

            return modelAndView;
        }
        ModelAndView modelAndView = new ModelAndView("allbooks");
        modelAndView.addObject("booklist", bookService.getVerifiedBook(true));
        return modelAndView;
    }

    /**
     * returns single book page for all
     *
     * @param bookId
     * @return
     */
    @RequestMapping(value = "/books/{bookId}", method = RequestMethod.GET)
    public Object getBook(@PathVariable("bookId") Long bookId, HttpSession httpSession) {

        if(httpSession.getAttribute("uerName") != null && (httpSession.getAttribute("role").equals("admin") || httpSession.getAttribute("role").equals("moderator"))){
            ModelAndView modelAndView = new ModelAndView("adminbookprofile");
            modelAndView.addObject("book", bookService.getBookById(bookId));
            modelAndView.addObject("updatebook", new BookDto());
            modelAndView.addObject("delete", new DeleteDto());
            modelAndView.addObject("bookprofile",new ReviewBookDto());
            return modelAndView;
        }
        ModelAndView modelAndView = new ModelAndView("bookprofile");
        modelAndView.addObject("book", bookService.getBookById(bookId));
        modelAndView.addObject("bookprofile",new ReviewBookDto());
        return modelAndView;
    }



    /**
     * the book update page can be requested by the admin or the moderator
     *
     * @param bookId
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/books/{bookId}/update", method = RequestMethod.GET)
    public Object updateBookGetView(@PathVariable("bookId") Long bookId, HttpSession httpSession) {
        if (httpSession.getAttribute("userName") != null && (httpSession.getAttribute("role").equals("admin") || httpSession.getAttribute("role").equals("moderator"))) {
            ModelAndView modelAndView = new ModelAndView("bookupdate");
            modelAndView.addObject("bookupdate", bookService.getBookById(bookId));
            modelAndView.addObject("bookupdatedto", new BookDto());
            return modelAndView;
        } else {
            return "redirect:/login";
        }

    }

    /**
     * the updated info can be persisted by the admin or the moderator
     *
     * @param bookId
     * @param bookDto
     * @param bindingResult
     * @param model
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "admin/allbooks/{bookId}", method = RequestMethod.PUT)
    public String updateBook(@PathVariable("bookId") Long bookId, @Valid @ModelAttribute("bookupdatedto") BookDto bookDto, BindingResult bindingResult, Model model, HttpSession httpSession) {
        if (bindingResult.hasErrors()) {
            return "authorupdate";
        }
        try {
            bookService.updateBook(bookId, bookDto);
            return "redirect:/books";
        } catch (BookDoesNotExistException b) {
            model.addAttribute("BookDoesNotExist", b);
            return "authorupdate";
        }

    }

    /**
     * Book can be deleted by the admin
     * @param bookId
     * @param httpSession
     * @param model
     * @return
     */
    @RequestMapping(value = "/books/{bookId}", method = RequestMethod.DELETE)
    public Object removeBook(@PathVariable("bookId") Long bookId,@ModelAttribute("delete") DeleteDto deleteDto, HttpSession httpSession, Model model) {
        if (httpSession.getAttribute("userName") != null && (httpSession.getAttribute("role").equals("admin"))) {
            try {
                bookService.removeBook(bookId);
                return "redirect:/books";
            } catch (BookDoesNotExistException b) {
                model.addAttribute("BookDoesNotExist", b);
                return "redirect:/books/{bookId}";
            }
        }
        return "null";

    }


}
