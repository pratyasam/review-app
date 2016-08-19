/**
 * ${}
 * Created by pratyasa on 16/8/16.
 */
package com.mindfire.review.services;

import com.mindfire.review.web.dto.SearchDto;
import com.mindfire.review.web.models.Author;
import com.mindfire.review.web.models.Book;
import com.mindfire.review.web.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SearchServiceImpl implements SearchService {
    @Autowired
    private AuthorService authorService;
    @Autowired
    private BookService bookService;
    @Autowired
    private UserService userService;

    @Override
    public Map<String, Object> search(SearchDto searchDto) {
        String searchParam = searchDto.getSearchParam();

        List<Author> authorByName = authorService.getAuthorByNameLike(searchParam);

        Book bookByIsbn = bookService.getBookByIsbn(searchParam);
        Book books = bookService.getBookByName(searchParam);
        List<User> users = userService.getUserFirstName(searchParam);
        List<Author> authors = new ArrayList<>();
        authors.addAll(authorByName);
        List<Book> books1 = new ArrayList<>();
        books1.add(bookByIsbn);
        books1.add(books);
        Map<String, Object> map = new HashMap();
        map.put("authors", authors);
        map.put("books", books1);
        map.put("users", users);
        return map;
    }

    @Override
    public Map<String, Object> searchByGenre(SearchDto searchDto) {
        String genre = searchDto.getSearchParam();
        List<Author> authors = authorService.getAuthorByGenre(genre);
        List<Book> books = bookService.getBookByGenre(genre);
        Map<String, Object> map = new HashMap<>();
        map.put("authors", authors);
        map.put("books", books);
        return map;
    }

    @Override
    public Map<String, Object> searchByRating(int rating) {
        List<Author> authors = authorService.getAuthorByRating(rating);
        List<Book> books = bookService.getBookByRating(rating);
        Map<String, Object> map = new HashMap<>();
        map.put("authors", authors);
        map.put("book", books);
        return map;
    }
}
