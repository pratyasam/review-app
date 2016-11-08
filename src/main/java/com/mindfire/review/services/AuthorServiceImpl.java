 package com.mindfire.review.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mindfire.review.exceptions.AlreadyReviewedException;
import com.mindfire.review.exceptions.AuthorExistenceException;
import com.mindfire.review.exceptions.ReviewDoesnotExistException;
import com.mindfire.review.util.Utility;
import com.mindfire.review.web.dto.AuthorDto;
import com.mindfire.review.web.models.Author;
import com.mindfire.review.web.models.AuthorLike;
import com.mindfire.review.web.models.Book;
import com.mindfire.review.web.models.BookAuthor;
import com.mindfire.review.web.models.ReviewAuthor;
import com.mindfire.review.web.models.ReviewBook;
import com.mindfire.review.web.models.User;
import com.mindfire.review.web.repositories.AuthorLikeRepository;
import com.mindfire.review.web.repositories.AuthorRepository;
import com.mindfire.review.web.repositories.BookAuthorRepository;
import com.mindfire.review.web.repositories.ReviewAuthorRepository;
import com.mindfire.review.web.repositories.ReviewBookRepository;

/**
 * @author pratyasa
 */
@Service
public class AuthorServiceImpl implements AuthorService {
	

    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private ReviewAuthorRepository reviewAuthorRepository;
    @Autowired
    private BookAuthorRepository bookAuthorRepository;
    @Autowired
    private ReviewBookRepository reviewBookRepository;
    @Autowired
    private BookService bookService;
    @Autowired
    private UserService userService;
    @Autowired
    private AuthorLikeRepository authorLikeRepository;

    /**
     * get the list of all the authors
     * @return List<Author>
     */
    @Override
    public Page<Author> getAllAuthor(int pageno, int size) {
    	Pageable page = Utility.buildPageRequest(size, pageno);
        return authorRepository.findAll(page);
    }
    /**
     * 
     */
    @Override
    public List<Author> getAllAuthor() {
        return authorRepository.findAll();
    }
    

    /**
     * to get the author or authors with the same name
     * @param name
     * @return List<Author>
     */


    @Override
    public Page<Author> getAuthorByNameLike(String name,int page) {return authorRepository.findByAuthorNameContainsIgnoreCase(name, Utility.buildPageRequest(10, page));}

    /**
     * get the author by name
     * @param name
     * @return
     */


    @Override
    public Author getAuthorByName(String name) {
        return authorRepository.findByAuthorNameIgnoreCase(name);
    }

    /**
     * to get the list of authors by their rating
     * @param rating
     * @return
     */


    @Override
    public Page<Author> getAuthorByRating(float rating,int pageno, int size) {
    	Pageable page = Utility.buildPageRequest(size, pageno);
        return authorRepository.findByAuthorRating(rating, page);
    }
    
    /**
     * 
     * @return
     */
    @Override
    public List<Author> getAuthorByRating(float rating) {
    	 return authorRepository.findByAuthorRating(rating);
    }

    /**
     * to get the list of authors according to the genre
     * @param genre
     * @return
     */


    @Override
    public Page<Author> getAuthorByGenre(String genre, int pageno, int size) {
    	Pageable page = Utility.buildPageRequest(size, pageno);
    	return authorRepository.findByAuthorGenreContainsIgnoreCase(genre, page);}
    /**
     * 
     * @param genre
     * @param page
     * @return
     */
   
    @Override
    public List<Author> getAuthorByGenre(String genre) {
    	return authorRepository.findByAuthorGenreContainsIgnoreCase(genre);}


    /**
     * to get all the reviews on the author
     * @param name
     * @return
     */


    @Override
    public List<ReviewAuthor> getAuthorReviewByAuthorName(String name) {return reviewAuthorRepository.findByAuthor(getAuthorByName(name));}
    
    /**
     * 
     * @param name
     * @return
     */
    
    @Override
    public int getTotalAuthorReviewByAuthorName(String name) {return reviewAuthorRepository.findByAuthor(getAuthorByName(name)).size();}
    /**
     * 
     * @param name
     * @param pageno
     * @param size
     * @return
     */
    @Override
    public Page<ReviewAuthor> getAuthorReviewByAuthorName(String name, int pageno, int size) {
    	Pageable page = Utility.buildPageRequest(size, pageno);
    	List<ReviewAuthor> reviewAuthors = reviewAuthorRepository.findByAuthor(getAuthorByName(name));
     
    	return new PageImpl<>(reviewAuthors,page, size);
    	}

    /**
     * to get the author by the Id
     * @param authorId
     * @return
     */

    @Override
    public Author getAuthorById(Long authorId){ return authorRepository.findOne(authorId);}

    /**
     * get the book reviews on all the books authored by the author
     * @param name
     * @return
     */
    @Override
    public List<ReviewBook> getBookReviewByAuthorName(String name) {
        List<Book> bookList = getBookByAuthor(name);
        List<ReviewBook> reviewBookList = new ArrayList<>();
        List<ReviewBook> reviewBookList1;
        for (Book b : bookList) {
            reviewBookList1 = reviewBookRepository.findByBook(b);
            reviewBookList.addAll(reviewBookList1);
        }
        return reviewBookList;
    }
    @Override
    public Page<ReviewBook> getBookReviewByAuthorName(String name, int pageno, int size) {
        List<Book> bookList = getBookByAuthor(name);
        List<ReviewBook> reviewBookList = new ArrayList<>();
        List<ReviewBook> reviewBookList1;
        for (Book b : bookList) {
            reviewBookList1 = reviewBookRepository.findByBook(b);
            reviewBookList.addAll(reviewBookList1);
        }
        Pageable page = Utility.buildPageRequest(size, pageno);
        return new PageImpl<>(reviewBookList,page, size);
    }

    /**
     * to get the list of books authored by the author
     * @param name
     * @return
     */

    @Override
    public List<Book> getBookByAuthor(String name) {
       List<BookAuthor> list = bookAuthorRepository.findByAuthor(authorRepository.findByAuthorNameIgnoreCase(name));
        List<Book> book = new ArrayList<>();
        for(BookAuthor ba : list){
        	book.add(ba.getBook());
        }
        return book;
    }
   /**
    * 
    */
    @Override
    public Page<Book> getBookByAuthor(String name, int pageno, int size) {
       List<BookAuthor> list = bookAuthorRepository.findByAuthor(authorRepository.findByAuthorNameIgnoreCase(name));
        List<Book> book = new ArrayList<>();
        for(BookAuthor ba : list){
        	book.add(ba.getBook());
        }
        Pageable page = Utility.buildPageRequest(size, pageno);
        return new PageImpl<>(book,page,size);
    }


    /**
     * to get all the users who have commented on the books by the author
     * @param name
     * @return
     */


    @Override
    public List<User> getUserByAuthor(String name) {
        List<User> users = new ArrayList<>();
        List<Book> books = getBookByAuthor(name);
        for (Book b : books) {
            users.addAll(bookService.getUserByBookReview(b.getBookName()));
        }
        return users;
    }
    /**
     * 
     */
    @Override
    public Page<User> getUserByAuthor(String name, int pageno, int size) {
        List<User> users = new ArrayList<>();
        List<Book> books = getBookByAuthor(name);
        for (Book b : books) {
            users.addAll(bookService.getUserByBookReview(b.getBookName()));
        }
        Pageable page = Utility.buildPageRequest(size, pageno);
        return new PageImpl<>(users,page,size);
    }

    /**
     * method to add authors
     * @param authorDto
     * @throws AuthorExistenceException
     */

    @Override
	public void addAuthor(AuthorDto authorDto) throws AuthorExistenceException{
    	
        if(authorDto == null){
            throw new NullPointerException("Author Dto sjould not be null.");
        }
        
        if(authorRepository.findByAuthorNameIgnoreCase(authorDto.getAuthorName()) != null){
        	
            throw new AuthorExistenceException("Author already exists.");
        }
        
        Author author = new Author();
        author.setAuthorDescription(authorDto.getAuthorDescription());
        author.setAuthorGenre(authorDto.getAuthorGenre());
        author.setAuthorName(authorDto.getAuthorName());
        author.setAuthorRating(authorDto.getAuthorRating());
        authorRepository.save(author);
    }

    /**
     * method to update author
     * @param authorDto
     * @param authorId
     * @throws AuthorExistenceException
     */
    @Override
	public void updateAuthor(AuthorDto authorDto, Long authorId) throws AuthorExistenceException{
    	
        Author author = authorRepository.findOne(authorId);
        
        if(authorDto == null){
            throw new NullPointerException("Sorry for the internal error caused.");
        }
        
        if (author == null){
            throw new AuthorExistenceException("Author does not esist");
        }
        
        author.setAuthorRating(authorDto.getAuthorRating());
        author.setAuthorName(authorDto.getAuthorName());
        author.setAuthorGenre(authorDto.getAuthorGenre());
        author.setAuthorDescription(authorDto.getAuthorDescription());
        authorRepository.save(author);
    }

    /**
     * method to remove authors
     * @param authorId
     * @throws AuthorExistenceException
     */
    @Override
	public void removeAuthor(Long authorId) throws AuthorExistenceException{
    	
        Author author = authorRepository.findOne(authorId);
        
        if(author == null){
        	
            throw new AuthorExistenceException("Author does not exist");
        }
        
            authorRepository.delete(author);
       
    }
     /**
      * method to add likes for author
      * @param userName
      * @param authorId
      * @throws AlreadyReviewedException
      */
    
    @Override
	public void addAuthorLikeByUser(String userName, Long authorId) throws AlreadyReviewedException{
    	
    	User user = userService.getUser(userName);
    	Author author = getAuthorById(authorId);
    	
    	 if(authorLikeRepository.findByAuthorAndUser(author, user) != null){
    		 
    		 throw new AlreadyReviewedException("already liked the author");
    	 }
    	 
    	 AuthorLike authorLike = new AuthorLike();
    	 authorLike.setAuthor(author);
    	 authorLike.setUser(user);
    	 authorLike = authorLikeRepository.save(authorLike);
    	 
    	 author.setAuthorLikes(getNumberOfLikesByUser(authorId));
    	 authorRepository.save(author);
    	 
    	 if(authorLike == null){
    		 
    		 throw new NullPointerException("Unable to persist into the database. "+author.getAuthorName());
    	 }
    }
    
    /**
     *  method to dislike author
     * @param authorLikeId
     * @throws ReviewDoesnotExistException
     */
    
    @Override
	public void removeAuthorLikeByUser(String userName, Long authorId) throws ReviewDoesnotExistException{
    	
    	User user = userService.getUser(userName);
    	Author author = getAuthorById(authorId);
    	AuthorLike authorLike = authorLikeRepository.findByAuthorAndUser(author, user);
    	
    	if(authorLike == null){
    		throw new ReviewDoesnotExistException("the author like doesnot exist.");
    	}
    	
    	authorLikeRepository.delete(authorLike);
    }
    
    /**
     * returns the total likes
     * @param authorId
     * @return
     */
    
    @Override
	public int getNumberOfLikesByUser(Long authorId){
    	Author author = getAuthorById(authorId);
    	int likes = authorLikeRepository.findByAuthor(author).size();
    	author.setAuthorLikes(likes);
    	authorRepository.save(author);
    	return likes;
    }
    
    /**
     * returns the top 10 authors
     */
    @Override
    public List<Author> getTop10Authors() {
    	
    	return authorRepository.findTop10ByAuthorLikesGreaterThanOrderByAuthorLikesDesc(0);
    }

}
