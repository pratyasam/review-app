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

    Page<Author> authorPage = (Page<Author>) request.getAttribute("authors");
    int totalpagesa = authorPage.getTotalPages();
    List<Author> authors = authorPage.getContent();

								if (authors.size() != 0) {
							%>
							<%
								for (Author a : authors) {
							%>
							<div class="col-lg-6">
								<div class="row">
									<div class="col-lg-4" style="padding: 2px;">
										<img src="assets/img/book1.jpg" alt="book1"
											style="width: 100%" />
									</div>

									<div class="col-lg-8">
										<div class="panel panel-default">
											<div class="panel-body">
												<a href="/reviewBook/authors/<%=a.getAuthorId()%>">
													<p style="font-size: 150%; border-bottom: 2px #CCC solid;"><%=a.getAuthorName()%></p>
												</a> <span class="glyphicon glyphicon-star"></span><span
													class="glyphicon glyphicon-star"></span><span
													class="glyphicon glyphicon-star"></span><span
													class="glyphicon glyphicon-star"></span><span
													class="glyphicon glyphicon-star"></span> <br>
												<div class="sidebar-box">
													<p><%=a.getAuthorDescription()%></p>
													<p class="read-more"></p>
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
							<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 text-center">
							
							
								<ul class="pagination">
									<%
										for (int i = 1; i <= totalpagesa; i++) {
									%>
									<li><a
										href="/reviewBook/search?page=<%=i%>&searchCategory=Authors"><%=i%></a></li>
									<%
										}
									%>
								</ul>
								<%
									}
								%>
								<%
									if (authors.size() == 0) {
								%>
								<h3>No Authors !</h3>
								<%
									}
								%>
								</div>
