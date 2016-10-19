<%@page import="org.springframework.data.domain.Page"%>
<%@page import="com.mindfire.review.enums.SearchType"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.mindfire.review.web.models.*"%>
<%@page import="com.mindfire.review.web.models.Author"%>
<%@page import="com.mindfire.review.web.dto.BookAuthorListDto"%>
<%@page import="com.mindfire.review.web.models.Book"%>
<%@page import="java.util.List"%>
<%@page import="com.mindfire.review.web.models.ReviewAuthor"%>

<%
    Page<Book> booksPage = (Page<Book>) request.getAttribute("books");
    int totalpagesb = booksPage.getTotalPages();
    List<Book> books = booksPage.getContent();
						if (books.size() != 0) {
					%>
					<%
						for (Book book : books) {
					%>
					<div class="col-lg-6">
						<div class="row">
							<div class="col-lg-4" style="padding: 2px;">
								<img src="assets/img/book1.jpg" alt="book1" style="width: 100%" />
							</div>
							<div class="col-lg-8">
								<div class="panel panel-default">
									<div class="panel-body">
										<a href="/reviewBook/books/<%=book.getBookId()%>">
											<div
												style="font-size: 120%; border-bottom: 2px #CCC solid; overflow: hidden; text-overflow: ellipsis;"><%=book.getBookName()%></div>
										</a>
															
										<span class="glyphicon glyphicon-star"></span><span
											class="glyphicon glyphicon-star"></span><span
											class="glyphicon glyphicon-star"></span><span
											class="glyphicon glyphicon-star"></span><span
											class="glyphicon glyphicon-star"></span> <br>
										<div class="sidebar-box">
											<p class="read-more"></p>
											<p><%=book.getBookDescription()%></p>

										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<%
						}
					%>
					<br> <br>
					<div
						class="col-lg-6 col-lg-offset-3 col--md-6 col-md-offset-3 col-sm-8 col-sm-offset-2 col-xs-12 text-center"">
						<% if(totalpagesb > 1){ %>

						<ul class="pagination">
							<%
								for (int i = 1; i <= totalpagesb; i++) {
							%>
							<li><a
								href="/reviewBook/search?page=<%=i%>&searchCategory=Books"><%=i%></a></li>
							<%
								}
							%>
						</ul>
						<%
						}
							}
						%>
						<%
							if (books.size() == 0) {
						%>
						<h3>No Books !</h3>
						<%
							}
						%>
						</div>
