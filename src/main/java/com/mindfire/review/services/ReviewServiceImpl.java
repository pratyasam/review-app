package com.mindfire.review.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mindfire.review.exceptions.AlreadyReviewedException;
import com.mindfire.review.exceptions.ReviewDoesnotExistException;
import com.mindfire.review.web.dto.ReviewAuthorDto;
import com.mindfire.review.web.dto.ReviewBookDto;
import com.mindfire.review.web.models.Author;
import com.mindfire.review.web.models.Book;
import com.mindfire.review.web.models.ReviewAuthor;
import com.mindfire.review.web.models.ReviewAuthorLike;
import com.mindfire.review.web.models.ReviewBook;
import com.mindfire.review.web.models.ReviewBookLike;
import com.mindfire.review.web.models.User;
import com.mindfire.review.web.repositories.AuthorRepository;
import com.mindfire.review.web.repositories.ReviewAuthorLikeRepository;
import com.mindfire.review.web.repositories.ReviewAuthorRepository;
import com.mindfire.review.web.repositories.ReviewBookLikeRepository;
import com.mindfire.review.web.repositories.ReviewBookRepository;

/**
 * Created by pratyasa on 9/8/16.
 */
@Service
public class ReviewServiceImpl implements ReviewService {
	@Autowired
	private UserService userService;
	@Autowired
	private BookService bookService;
	@Autowired
	private AuthorService authorService;
	@Autowired
	private ReviewBookRepository reviewBookRepository;
	@Autowired
	private ReviewAuthorRepository reviewAuthorRepository;
	@Autowired
	private AuthorRepository authorRepository;
	@Autowired
	private ReviewAuthorLikeRepository reviewAuthorLikeRepository;
	@Autowired
	private ReviewBookLikeRepository reviewBookLikeRepository;

	/**
	 * Add Review service for Book
	 *
	 * @param reviewBookDto
	 * @param userName
	 * @param bookId
	 * @throws AlreadyReviewedException
	 */

	@Override
	public void addBookReview(ReviewBookDto reviewBookDto, String userName, Long bookId)
			throws AlreadyReviewedException {
		Book book = bookService.getBookById(bookId);
		User user = userService.getUser(userName);
		if (reviewBookDto == null) {
			throw new RuntimeException("");
		}

		if (reviewBookRepository.findByUserAndBook(user, book) != null) {
			throw new AlreadyReviewedException("You have already Reviewed this book.");
		}
		ReviewBook reviewBook = new ReviewBook();
		reviewBook.setReviewText(reviewBookDto.getReviewText());
		reviewBook.setUser(user);
		reviewBook.setBook(book);
		reviewBook = reviewBookRepository.save(reviewBook);
		System.out.println("Book review Saved");
		if (reviewBook == null) {
			throw new RuntimeException("");
		}
	}

	/**
	 * Remove Review service for Book
	 *
	 * @param reviewId
	 * @throws ReviewDoesnotExistException
	 */

	@Override
	public void removeBookReview(Long reviewId) throws ReviewDoesnotExistException {
		if (reviewBookRepository.findOne(reviewId) == null) {
			throw new ReviewDoesnotExistException("The Review doesn't exist");
		}
		reviewBookRepository.delete(reviewBookRepository.findByReviewBookId(reviewId));
	}

	/**
	 * Update Review service for Book
	 *
	 * @param reviewBookDto
	 * @param reviewId
	 * @throws ReviewDoesnotExistException
	 */
	@Override
	public void updateBookReview(ReviewBookDto reviewBookDto, Long reviewId) throws ReviewDoesnotExistException {
		ReviewBook rb = reviewBookRepository.findOne(reviewId);
		if (rb == null) {
			throw new ReviewDoesnotExistException("The Book Review doesn't exit.");
		}
		rb.setReviewText(reviewBookDto.getReviewText());
		reviewBookRepository.save(rb);

	}

	/**
	 * Add Review service for Author
	 *
	 * @param reviewAuthorDto,
	 * @param authorId
	 * @param userName
	 * @throws AlreadyReviewedException
	 */
	@Override
	public void addAuthorReview(ReviewAuthorDto reviewAuthorDto, Long authorId, String userName)
			throws AlreadyReviewedException {
		User user = userService.getUser(userName);
		Author author = authorRepository.findOne(authorId);
		if (reviewAuthorDto == null) {
			throw new RuntimeException("");
		}
		if (reviewAuthorRepository.findByUserAndAuthor(user, author) != null) {
			throw new AlreadyReviewedException("You have already reviewed this author.");
		}
		ReviewAuthor reviewAuthor = new ReviewAuthor();
		reviewAuthor.setReviewAuthorText(reviewAuthorDto.getReviewText());
		reviewAuthor.setUser(user);
		reviewAuthor.setAuthor(author);
		reviewAuthor = reviewAuthorRepository.save(reviewAuthor);
		System.out.println("Author review saved");
		if (reviewAuthor == null) {
			throw new RuntimeException("");
		}
	}

	/**
	 * Remove Review service for Author
	 *
	 * @param reviewAuthorId
	 * @throws ReviewDoesnotExistException
	 */
	@Override
	public void removeAuthorReview(Long reviewAuthorId) throws ReviewDoesnotExistException {
		
		// Fetch the author from the DB
		ReviewAuthor reviewAuthor = reviewAuthorRepository.findOne(reviewAuthorId);

		if (reviewAuthor == null) {
			throw new ReviewDoesnotExistException("The Author Review doesn't exist.");
		}

		reviewAuthorRepository.delete(reviewAuthor);
	}

	/**
	 * Update Review service for Author
	 *
	 * @param reviewAuthorDto
	 * @param reviewAuthorId
	 * @throws ReviewDoesnotExistException
	 */

	@Override
	public void updateAuthorReview(ReviewAuthorDto reviewAuthorDto, Long reviewAuthorId)
			throws ReviewDoesnotExistException {
		ReviewAuthor reviewAuthor = reviewAuthorRepository.findOne(reviewAuthorId);
		if (reviewAuthor == null) {
			throw new ReviewDoesnotExistException("The Author Review doesn't not exist.");
		}
		reviewAuthor.setReviewAuthorText(reviewAuthorDto.getReviewText());
		reviewAuthorRepository.save(reviewAuthor);
	}

	/**
	 * find review for author by review author Id
	 * 
	 * @param reviewAuthorId
	 * @return
	 */

	@Override
	public ReviewAuthor getReviewAuthorById(Long reviewAuthorId) {
		return reviewAuthorRepository.findOne(reviewAuthorId);
	}

	/**
	 * find review for book by review book Id
	 * 
	 * @param reviewBookId
	 * @return
	 */
	@Override
	public ReviewBook getReviewBookById(Long reviewBookId) {
		return reviewBookRepository.findOne(reviewBookId);
	}

	/**
	 * find the number of author reviews made by the user.
	 * 
	 * @param user
	 * @return int
	 */
	@Override
	public int getNumberOfAuthorReviewsLikedByTheUser(User user) {
		return reviewAuthorLikeRepository.findByUser(user).size();
	}
	
	/**
	 * 
	 */

	@Override
	public int getNumberOfBookReviewsLikedByTheUser(User user) {
		return reviewBookLikeRepository.findByUser(user).size();
	}
	
	/**
	 * 
	 */

	@Override
	public void addLikeForAuthorReview(String userName, Long reviewAuthorId) throws AlreadyReviewedException {

		User user = userService.getUser(userName);
		ReviewAuthor reviewAuthor = getReviewAuthorById(reviewAuthorId);
		ReviewAuthorLike reviewAuthorLike2 = reviewAuthorLikeRepository.findByReviewAuthorAndUser(reviewAuthor, user);

		if (reviewAuthorLike2 != null && reviewAuthorLike2.getReviewAuthorLikeFlag() == 1) {
			throw new AlreadyReviewedException("You have already liked this author review.");
		}
        
		if(reviewAuthorLike2 == null){
		ReviewAuthorLike reviewAuthorLike = new ReviewAuthorLike();
		reviewAuthorLike.setReviewAuthor(reviewAuthor);
		reviewAuthorLike.setUser(user);
		reviewAuthorLike.setReviewAuthorLikeFlag(1);
		reviewAuthorLike = reviewAuthorLikeRepository.save(reviewAuthorLike);
		if (reviewAuthorLike == null) {
			throw new RuntimeException("");
		}
		}
		
		if(reviewAuthorLike2 != null && reviewAuthorLike2.getReviewAuthorLikeFlag() == -1){
			reviewAuthorLike2.setReviewAuthorLikeFlag(1);
			reviewAuthorLikeRepository.saveAndFlush(reviewAuthorLike2);
		}

	}
	
	/**
	 * 
	 */
	@Override
	public void removeLikeForAuthorReview(String userName, Long reviewAuthorId) throws ReviewDoesnotExistException {

		User user = userService.getUser(userName);
		ReviewAuthor reviewAuthor = getReviewAuthorById(reviewAuthorId);
		ReviewAuthorLike reviewAuthorLike = reviewAuthorLikeRepository.findByReviewAuthorAndUser(reviewAuthor, user);
		
		if (reviewAuthorLike != null && reviewAuthorLike.getReviewAuthorLikeFlag() == -1) {
			throw new ReviewDoesnotExistException("The Author Review dislike exists.");
		}
		if (reviewAuthorLike == null ){
			ReviewAuthorLike reviewAuthorLike2 = new ReviewAuthorLike();
			reviewAuthorLike2.setReviewAuthorLikeFlag(-1);
			reviewAuthorLike2.setUser(user);
			reviewAuthorLike2.setReviewAuthor(reviewAuthor);
		}
		if(reviewAuthorLike != null && reviewAuthorLike.getReviewAuthorLikeFlag() == 1){
			reviewAuthorLike.setReviewAuthorLikeFlag(-1);
			reviewAuthorLikeRepository.saveAndFlush(reviewAuthorLike);
		}
		
	}
	
	/**
	 * 
	 */
	@Override
	public void addLikeForBookReview(String userName, Long reviewBookId) throws AlreadyReviewedException {
		
		User user = userService.getUser(userName);
		ReviewBook reviewBook = getReviewBookById(reviewBookId);
		ReviewBookLike reviewBookLike = reviewBookLikeRepository.findByReviewBookAndUser(reviewBook, user);
		
		if(reviewBookLike != null && reviewBookLike.getReviewBookLikeFlag() == 1){
			throw new AlreadyReviewedException("You have already liked this book review.");
		}
		
		if(reviewBookLike == null){
		ReviewBookLike reviewBookLike2 = new ReviewBookLike();
		reviewBookLike2.setReviewBook(reviewBook);
		reviewBookLike2.setUser(user);
		reviewBookLike2.setReviewBookLikeFlag(1);
		reviewBookLike2 = reviewBookLikeRepository.save(reviewBookLike2);
		
		if(reviewBookLike2 == null){
			throw new RuntimeException("");
		}
		}
		
		if(reviewBookLike != null && reviewBookLike.getReviewBookLikeFlag() == -1){
			reviewBookLike.setReviewBookLikeFlag(1);
			reviewBookLikeRepository.saveAndFlush(reviewBookLike);
		}
		
	}
	/**
	 * 
	 */
	
	
	@Override
	public void removeLikeForBookReview(String userName, Long reviewBookId) throws ReviewDoesnotExistException {
		User user = userService.getUser(userName);
		ReviewBook reviewBook = getReviewBookById(reviewBookId);
		
		ReviewBookLike reviewBookLike = reviewBookLikeRepository.findByReviewBookAndUser(reviewBook, user);
		
		if(reviewBookLike != null && reviewBookLike.getReviewBookLikeFlag() == -1){
			throw new ReviewDoesnotExistException("The Author Review dislike exists."); 
		}
		
		if(reviewBookLike == null){
			ReviewBookLike reviewBookLike2 = new ReviewBookLike();
			reviewBookLike2.setReviewBook(reviewBook);
			reviewBookLike2.setUser(user);
			reviewBookLike2.setReviewBookLikeFlag(-1);
			reviewBookLikeRepository.save(reviewBookLike2);
		}
		
		if(reviewBookLike != null && reviewBookLike.getReviewBookLikeFlag() == 1){
			reviewBookLike.setReviewBookLikeFlag(-1);
			reviewBookLikeRepository.save(reviewBookLike);
		}
		
	}
	
	@Override
	public int getNumberOfReviewLikesByBook(ReviewBook reviewBook){
		
		List<ReviewBookLike> reviewBookLikes = reviewBookLikeRepository.findByReviewBook(reviewBook);
		int x=0;
		
		for(ReviewBookLike rb : reviewBookLikes){
			int y = rb.getReviewBookLikeFlag();
		    if(y == 1)
			x = x+ y;
		}
		return x;
		
		
	}
	
	/**
	 * 
	 */
	@Override
	public int getNumberOfReviewLikesByAuthor(ReviewAuthor reviewAuthor){
		List<ReviewAuthorLike> reviewAuthorLikes = reviewAuthorLikeRepository.findByReviewAuthor(reviewAuthor);
		int x = 0;
		
		for(ReviewAuthorLike ra : reviewAuthorLikes){
			int y =ra.getReviewAuthorLikeFlag();
			if(y == 1)
			x = x + y;
		}
		
		return x;
	}
	
	/**
	 * 
	 */
	@Override
	public int getNumberOfReviewDislikesByBook(ReviewBook reviewBook){
		List<ReviewBookLike> reviewBookLikes = reviewBookLikeRepository.findByReviewBook(reviewBook);
		int x=0;
		
		for(ReviewBookLike rb : reviewBookLikes){
			int y = rb.getReviewBookLikeFlag();
		    if(y == -1)
			x = x+ y;
		}
		
		return -1 * x;
		
	}
	
	@Override
	public int getNumberOfReviewDislikesByAuthor(ReviewAuthor reviewAuthor){
		int x = 0;
		List<ReviewAuthorLike> reviewAuthorLikes = reviewAuthorLikeRepository.findByReviewAuthor(reviewAuthor);
		
		for(ReviewAuthorLike ra : reviewAuthorLikes){
			int y = ra.getReviewAuthorLikeFlag();
			if(y == -1)
				x= x+y;
		}
		return -1 * x;
		
	}
	
	@Override
	public int getNumberOfReviewLikesByUser(User user){
		 int authorReviewLike = reviewAuthorLikeRepository.findByUser(user).size();
		 int bookReviewLike = reviewBookLikeRepository.findByUser(user).size();
		 return authorReviewLike + bookReviewLike;
		
	}
	

}
