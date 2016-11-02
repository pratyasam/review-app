package com.mindfire.review.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mindfire.review.exceptions.AlreadyReviewedException;
import com.mindfire.review.exceptions.BookDoesNotExistException;
import com.mindfire.review.exceptions.BookExistException;
import com.mindfire.review.exceptions.ReviewDoesnotExistException;
import com.mindfire.review.util.Utility;
import com.mindfire.review.web.dto.BookDto;
import com.mindfire.review.web.dto.ChoiceDto;
import com.mindfire.review.web.models.Author;
import com.mindfire.review.web.models.Book;
import com.mindfire.review.web.models.BookAuthor;
import com.mindfire.review.web.models.BookLike;
import com.mindfire.review.web.models.ReviewAuthor;
import com.mindfire.review.web.models.ReviewBook;
import com.mindfire.review.web.models.User;
import com.mindfire.review.web.repositories.BookAuthorRepository;
import com.mindfire.review.web.repositories.BookLikeRepository;
import com.mindfire.review.web.repositories.BookRepository;
import com.mindfire.review.web.repositories.ReviewAuthorRepository;
import com.mindfire.review.web.repositories.ReviewBookRepository;

@Service
public class BookServiceImpl implements BookService {

	@Autowired
	private BookRepository bookRepository;
	@Autowired
	private BookAuthorRepository bookAuthorRepository;
	@Autowired
	private ReviewBookRepository reviewBookRepository;
	@Autowired
	private ReviewAuthorRepository reviewAuthorRepository;
	@Autowired
	private BookLikeRepository bookLikeRepository;
	@Autowired
	private UserService userService;
	



	/**
	 * get book by Id
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public Book getBookById(Long id) {
		return bookRepository.findOne(id);
	}

	/**
	 * find all the listed books in pages
	 * 
	 * @param page
	 * @param size
	 * @return
	 */
	@Override
	public Page<Book> getBooks(int pageno, int size) {
		Pageable page = Utility.buildPageRequest(size, pageno);
		return bookRepository.findAll(page);
	}
	/**
	 * returns the list of all books
	 */
	@Override
	public List<Book> getBooks() {
		return bookRepository.findAll();
	}

	/**
	 * get all books by genre
	 * 
	 * @param genre
	 * @return
	 */

	@Override
	public Page<Book> getBookByGenre(String genre, boolean choice,int pageno, int size) {
		Pageable page = Utility.buildPageRequest(size, pageno);
		return bookRepository.findByBookGenreContainingAndBookVerified(genre, choice, page);
	}
	/**
	 * returns book by genre for admin or moderator
	 * @param genre
	 * @param choice
	 * @param pageno
	 * @param size
	 * @return
	 */
	@Override
	public Page<Book> getBookByGenreAdmin(String genre,int pageno, int size) {
		Pageable page = Utility.buildPageRequest(size, pageno);
		return bookRepository.findByBookGenreContaining(genre, page);
	}
	/**
	 * get verified books by genre
	 * @param genre
	 * @param choice
	 * @return
	 */
	@Override
	public List<Book> getBookByGenre(String genre,boolean choice) {
		return bookRepository.findByBookGenreContainingAndBookVerified(genre, choice);
	}
	/**
	 * find books by genre name similar
	 * @param genre
	 * @param choice
	 * @return
	 */
	@Override
	public List<Book> getBookByGenreAdmin(String genre) {
		return bookRepository.findByBookGenreContaining(genre);
	}

	/**
	 * get all the books by rating
	 * 
	 * @param rating
	 * @return
	 */

	@Override
	public Page<Book> getBookByRating(float rating, int pageno, int size) {
		Pageable page = Utility.buildPageRequest(size, pageno);
		return bookRepository.findByBookRating(rating, page);
	}
	/**
	 * 
	 * @param rating
	 * @return
	 */
	@Override
	public List<Book> getBookByRating(float rating) {
		return bookRepository.findByBookRating(rating);
	}

	/**
	 * get book by Isbn
	 * 
	 * @param isbn
	 * @return
	 */
	@Override
	public Book getBookByIsbn(String isbn) {
		return bookRepository.findByBookIsbn(isbn);
	}

	/**
	 * Get book by book name
	 * 
	 * @param name
	 * @return
	 */

	@Override
	public Page<Book> getBookByNameLikeAndIsbn(String name, String isbn, int page) {
		return bookRepository.findByBookNameContainingAndBookVerifiedOrBookIsbnContaining(name, true, isbn, Utility.buildPageRequest(10, page));
	}
	/**
	 * search book by name or isbn
	 * @param name
	 * @return
	 */
	@Override
	public Page<Book> getBookByNameLikeAdminAndIsbn(String name,String isbn, int page) {
		return bookRepository.findByBookNameContainingOrBookIsbnContaining(name, isbn, Utility.buildPageRequest(10, page));
	}

	/**
	 * get the list of authors who have authored on the book
	 * 
	 * @param name
	 * @return
	 */

	@Override
	public List<Author> getAuthorByBook(String name) {
		Book book = getBookByName(name);
		List<BookAuthor> list1 = bookAuthorRepository.findByBook(book);
		List<Author> list = new ArrayList<>();
		for (BookAuthor ba : list1) {
			list.add(ba.getAuthor());
		}
		return list;
	}
	/**
	 * returns the authors for a book
	 * @param name
	 * @param pageno
	 * @param size
	 * @return
	 */
	@Override
	public Page<Author> getAuthorByBook(String name, int pageno, int size) {
		Pageable page = Utility.buildPageRequest(size, pageno);
		Book book = getBookByName(name);
		List<BookAuthor> list1 = bookAuthorRepository.findByBook(book);
		List<Author> list = new ArrayList<>();
		for (BookAuthor ba : list1) {
			list.add(ba.getAuthor());
		}
		return new PageImpl<>(list, page, size);
	}

	/**
	 * return a specific book
	 * @param name
	 * @return
	 */
	@Override
	public Book getBookByName(String name) {
		return bookRepository.findByBookName(name);
	}

	/**
	 * find all the reviews on a book
	 * 
	 * @param name
	 * @return
	 */

	@Override
	public List<ReviewBook> getBookReviewByBook(String name) {
		return reviewBookRepository.findByBook(getBookByName(name));
	}
	
	/**
	 * get total number of reviews for a book
	 * @param name
	 * @return
	 */
	
	@Override
	public int getTotalBookReviewByBook(String name) {
		return reviewBookRepository.findByBook(getBookByName(name)).size();
	}
	/**
	 * get reviews for the book
	 * @param name
	 * @param pageno
	 * @param size
	 * @return
	 */
	@Override
	public Page<ReviewBook> getBookReviewByBook(String name, int pageno, int size) {
		Pageable page =Utility.buildPageRequest(size, pageno);
		List<ReviewBook> list = reviewBookRepository.findByBook(getBookByName(name));
		return new PageImpl<>(list, page, size);
	}

	/**
	 * get the list of reviews on authors of a book
	 * 
	 * @param name
	 * @return
	 */
	@Override
	public List<ReviewAuthor> getAuthorReviewByBook(String name) {
		List<Author> authorList = getAuthorByBook(name);
		List<ReviewAuthor> reviewAuthorList = new ArrayList<>();
		List<ReviewAuthor> reviewAuthorList1;
		for (Author a : authorList) {
			reviewAuthorList1 = reviewAuthorRepository.findByAuthor(a);
			reviewAuthorList.addAll(reviewAuthorList1);
		}
		return reviewAuthorList;
	}
	/**
	 * gets reviews for the author of a book
	 * @param name
	 * @param pageno
	 * @param size
	 * @return
	 */
	@Override
	public Page<ReviewAuthor> getAuthorReviewByBook(String name, int pageno, int size) {
		List<Author> authorList = getAuthorByBook(name);
		List<ReviewAuthor> reviewAuthorList = new ArrayList<>();
		List<ReviewAuthor> reviewAuthorList1;
		for (Author a : authorList) {
			reviewAuthorList1 = reviewAuthorRepository.findByAuthor(a);
			reviewAuthorList.addAll(reviewAuthorList1);
		}
		Pageable page = Utility.buildPageRequest(size, pageno);
		return new PageImpl<>(reviewAuthorList, page, size);
	}

	/**
	 * get verified books in the form of list
	 * 
	 * @param verified
	 * @return
	 */
	@Override
	public Page<Book> getVerifiedBook(boolean verified, int pageno, int size) {
		Pageable page = Utility.buildPageRequest(size, pageno);
		return bookRepository.findByBookVerified(verified, page);
	}
	
	/**
	 * get list of verified books
	 * @param verified
	 * @return
	 */
	@Override
	public List<Book> getVerifiedBook(boolean verified) {
		return bookRepository.findByBookVerified(verified);
	}

	/**
	 * get all the users who have reviewed on the book
	 * 
	 * @param name
	 * @return
	 */

	@Override
	public List<User> getUserByBookReview(String name) {
		List<User> users = new ArrayList<>();
		List<ReviewBook> reviewBooks = getBookReviewByBook(name);
		for (ReviewBook r : reviewBooks) {
			users.add(r.getUser());
		}
		return users;
	}
	/**
	 * returns the users who reviewed the book
	 * @param name
	 * @param pageno
	 * @param size
	 * @return
	 */
	@Override
	public Page<User> getUserByBookReview(String name, int pageno, int size) {
		List<User> users = new ArrayList<>();
		List<ReviewBook> reviewBooks = getBookReviewByBook(name);
		for (ReviewBook r : reviewBooks) {
			users.add(r.getUser());
		}
		Pageable page = Utility.buildPageRequest(size, pageno);
		return new PageImpl<>(users, page, size);
	}

	/**
	 * to check whether the book exists or not
	 * 
	 * @param isbn
	 * @return
	 */

	@Override
	public boolean doesBookExist(String isbn) {
		Book book = getBookByIsbn(isbn);
		if (book != null)
			return true;
		else
			return false;
	}

	/**
	 * method to add book
	 * 
	 * @param bookDto
	 * @throws BookExistException
	 */

	@Override
	public void addBook(BookDto bookDto) throws BookExistException {
		if (bookDto == null)
			throw new RuntimeException("");
		if (doesBookExist(bookDto.getBookIsbn())) {
			throw new BookExistException("Book Already Exists");
		}
		Book book = new Book();
		book.setBookCost(bookDto.getBookCost());
		book.setBookDescription(bookDto.getBookDescription());
		book.setBookGenre(bookDto.getBookGenre());
		book.setBookIsbn(bookDto.getBookIsbn());
		book.setBookName(bookDto.getBookName());
		book.setBookLink(bookDto.getBookLink());
		book.setBookReview(bookDto.getBookReview());

		book = bookRepository.save(book);
		if (book == null) {
			throw new RuntimeException("");
		}

	}

	/**
	 * method to remove book
	 * 
	 * @param id
	 * @throws BookDoesNotExistException
	 */

	@Override
	public void removeBook(Long id) throws BookDoesNotExistException {
		Book book = getBookById(id);
		if (book == null) {
			throw new RuntimeException("");
		}
		if (!doesBookExist(book.getBookIsbn())) {
			throw new BookDoesNotExistException("Book does not exist");
		}
		bookRepository.delete(book);

	}

	/**
	 * method to update book
	 * 
	 * @param bookId,
	 *            bookDto
	 * @param bookDto
	 * @throws BookDoesNotExistException
	 */

	@Override
	public void updateBook(Long bookId, BookDto bookDto) throws BookDoesNotExistException {
		Book bk = getBookById(bookId);
		if (bookDto == null) {
			throw new RuntimeException("");
		}
		if (bk == null) {
			throw new BookDoesNotExistException("Book does not exist");
		}
		bk.setBookIsbn(bookDto.getBookIsbn());
		bk.setBookCost(bookDto.getBookCost());
		bk.setBookGenre(bookDto.getBookGenre());
		bk.setBookDescription(bookDto.getBookDescription());
		bk.setBookLink(bookDto.getBookLink());
		bk.setBookRating(bookDto.getBookRating());
		bk.setBookReview(bookDto.getBookReview());
		bookRepository.saveAndFlush(bk);
	}

	/**
	 * method to verify the book
	 * 
	 * @param id
	 */
	@Override
	public void verifyBook(Long id, ChoiceDto choiceDto) {
		Book book = getBookById(id);
			book.setBookVerified(true);
			bookRepository.save(book);
		
	}
	
	/**
	 * like the book
	 * @param userName
	 * @param bookId
	 * @throws AlreadyReviewedException
	 */
	
	@Override
	public void addBookLikeByUser(String userName, Long bookId)throws AlreadyReviewedException{
		User user = userService.getUser(userName);
		Book book = getBookById(bookId);
		
		if(bookLikeRepository.findByBookAndUser(book, user) != null){
			throw new AlreadyReviewedException("Already liked the book");
		}
		
		BookLike bookLike = new BookLike();
	    bookLike.setBook(book);
		bookLike.setUser(user);
		bookLike = bookLikeRepository.save(bookLike);
		
		book.setBookLikes(getNumberOfBookLikesByUsers(bookId));
		bookRepository.save(book);
	    
	    if(bookLike == null){
	    	throw new RuntimeException("");
	    }
		
	}
	
	/**
	 * dislike the book
	 * @param bookLikeId
	 * @throws ReviewDoesnotExistException
	 */
	
	@Override
	public void removeBookLikeByUser(String userName, Long bookId) throws ReviewDoesnotExistException{
		
		User user = userService.getUser(userName);
		Book book = getBookById(bookId);
		BookLike bookLike = bookLikeRepository.findByBookAndUser(book, user);
		
		if(bookLike == null){
			throw new ReviewDoesnotExistException("the book like doesnot exist.");
		}
		
		bookLikeRepository.delete(bookLike);
	}
	
	/**
	 * Returns number of total likes by book id.
	 * @param bookId
	 * @return
	 */
	
	@Override
	public int getNumberOfBookLikesByUsers(Long bookId){
		Book book = getBookById(bookId);
		int likes = bookLikeRepository.findByBook(book).size();
		
		book.setBookLikes(likes);
		bookRepository.save(book);
		
		return likes;
	}

	/**
	 * search books by name or isbn or genre
	 * @param name
	 * @param genre
	 * @param isbn
	 * @param page
	 */
	@Override
	public Page<Book> searchForBooks(String name, String genre, String isbn, int page) {

		return bookRepository.findByBookNameContainingOrBookGenreContainingOrBookIsbnContainingAndBookVerified(name, genre, isbn, true, Utility.buildPageRequest(10, page)); 
	}
	/**
	 * returns top 10 books
	 */
	
	@Override
	public List<Book> getTop10Books(){
		return bookRepository.findTop10ByBookLikesGreaterThanOrderByBookLikesDesc(0);
	}
}