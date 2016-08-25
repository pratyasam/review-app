package com.mindfire.review.web.controllers;

import com.mindfire.review.exceptions.BookDoesNotExistException;
import com.mindfire.review.exceptions.BookExistException;
import com.mindfire.review.services.BookService;
import com.mindfire.review.web.dto.BookDto;
import com.mindfire.review.web.dto.ChoiceDto;
import com.mindfire.review.web.dto.ReviewBookDto;
import com.mindfire.review.web.models.Author;
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
import java.util.List;

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
//    @RequestMapping(value = "/books/{bookId}/verifybooks", method = RequestMethod.GET)
//    public Object verifyBookGet(@PathVariable("bookId") Long bookId, HttpSession httpSession, Model model) {
//        if (httpSession.getAttribute("userName") != null && ((httpSession.getAttribute("role").equals("admin")) || httpSession.getAttribute("role").equals("moderator"))) {
//            model.addAttribute("choice", new ChoiceDto());
//            return model;
//        }
//
//        return "null";
//    }

    /**
     *
     * @param bookId
     * @param choiceDto
     * @param bindingResult
     * @param model
     * @param httpSession
     * @return
     */

    @RequestMapping(value = "/books/{bookId}/verifybooks", method = RequestMethod.POST)
    public String verifyBookPost(@PathVariable("bookId") Long bookId, @Valid @ModelAttribute("verify") ChoiceDto choiceDto, BindingResult bindingResult, Model model, HttpSession httpSession) {
        if (httpSession.getAttribute("userName") != null && ((httpSession.getAttribute("role").equals("admin")) || httpSession.getAttribute("role").equals("moderator"))) {
                bookService.verifyBook(bookId, choiceDto);
                return "redirect:/books";
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
        List<Author> author = bookService.getAuthorByBook(bookService.getBookById(bookId).getBookName());
        System.out.println(author.size());

        if(httpSession.getAttribute("userName") != null && (httpSession.getAttribute("role").equals("admin") || httpSession.getAttribute("role").equals("moderator"))){

            System.out.println("admin book profile");
            ModelAndView modelAndView = new ModelAndView("adminbookprofile");
            modelAndView.addObject("book", bookService.getBookById(bookId));
            modelAndView.addObject("authors",author);
            modelAndView.addObject("updatebook", new BookDto());
            modelAndView.addObject("delete", new ChoiceDto());
            modelAndView.addObject("verify", new ChoiceDto());
            modelAndView.addObject("bookprofile",new ReviewBookDto());
            return modelAndView;
        }
        System.out.println("normal book profile");
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
    @RequestMapping(value = "/books/{bookId}", method = RequestMethod.PUT)
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
    public Object removeBook(@PathVariable("bookId") Long bookId, @ModelAttribute("delete") ChoiceDto choiceDto, HttpSession httpSession, Model model) {
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
