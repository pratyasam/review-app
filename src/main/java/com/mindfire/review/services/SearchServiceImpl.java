/**
 * ${}
 * Created by pratyasa on 16/8/16.
 */
package com.mindfire.review.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mindfire.review.util.Utility;
import com.mindfire.review.web.dto.SearchDto;
import com.mindfire.review.web.models.Author;
import com.mindfire.review.web.models.Book;
import com.mindfire.review.web.models.User;

@Service
public class SearchServiceImpl implements SearchService {
    @Autowired
    private AuthorService authorService;
    @Autowired
    private BookService bookService;
    @Autowired
    private UserService userService;

    @Override
    public Map<String, Object> search(SearchDto searchDto, String role,int pagenoa,int pagenob, int pagenou) {
        String searchParam = searchDto.getSearchParam();
        List<Author> authorByName = authorService.getAuthorByNameLike(searchParam);
        Book bookByIsbn = bookService.getBookByIsbn(searchParam);
        List<Book> books = new ArrayList();
        if(role.equalsIgnoreCase("admin") || role.equalsIgnoreCase("moderator")){
        	books = bookService.getBookByNameLikeAdmin(searchParam);
        }
        else{
        	books = bookService.getBookByNameLike(searchParam);
        }
        List<User> users = userService.getUserFirstName(searchParam);
        List<Author> authors = new ArrayList<>();
        authors.addAll(authorByName);
        books.add(bookByIsbn);
        Pageable pagea = Utility.buildPageRequest(6, pagenoa);
        Pageable pageb = Utility.buildPageRequest(6, pagenob);
        Pageable pageu = Utility.buildPageRequest(6, pagenou);
        PageImpl<Author> pageImpla = new PageImpl<>(authors,pagea,6);
        PageImpl<Book> pageImplb = new PageImpl<>(books,pageb,6);
        PageImpl<User> pageImplu = new PageImpl<>(users,pageu,6);
        Map<String, Object> map = new HashMap();
        map.put("authors", pageImpla);
        map.put("books", pageImplb);
        map.put("users", pageImplu);
        return map;
    }

    @Override
    public Map<String, Object> searchByGenre(SearchDto searchDto) {
        String genre = searchDto.getSearchParam();
        List<Author> authors = authorService.getAuthorByGenre(genre);
        List<Book> books = bookService.getBookByGenre(genre,true);
        Map<String, Object> map = new HashMap<>();
        map.put("authors", authors);
        map.put("books", books);
        return map;
    }

    @Override
    public Map<String, Object> searchByRating(float rating) {
        List<Author> authors = authorService.getAuthorByRating(rating);
        List<Book> books = bookService.getBookByRating(rating);
        Map<String, Object> map = new HashMap<>();
        map.put("authors", authors);
        map.put("book", books);
        return map;
    }
}
