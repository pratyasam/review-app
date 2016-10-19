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

<style>
.sidebar-box {
	max-height: 120px;
	position: relative;
	overflow: hidden;
}

.sidebar-box .read-more {
	position: absolute;
	bottom: 0;
	left: 0;
	width: 100%;
	text-align: center;
	margin: 0;
	padding: 30px 0;
	/* "transparent" only works here because == rgba(0,0,0,0) */
	background-image: linear-gradient(to bottom, transparent, grey);
}
</style>



<%
								Page<User> userPage = (Page<User>) request.getAttribute("users");
								int totalpagesu = userPage.getTotalPages();
								List<User> users = userPage.getContent();

								if (users.size() != 0) {
							%>
<%=users.size()%>
<%
								for (User u : users) {
							%>
<div class="col-lg-6 col-md-6 col-sm-6">
	<div class="col-lg-4" style="padding: 2px;">
		<img src="assets/img/book1.jpg" alt="book1"
			style="width: 100%; height: 200px;" />
	</div>

	<div class="col-lg-8">
		<div class="panel panel-default">
			<div class="panel-body">
				<a href="/reviewBook/users/<%=u.getUserId()%>">
					<p style="font-size: 150%; border-bottom: 2px #CCC solid;"><%=u.getUserName()%></p>
				</a> <br>
				<p>
					Name:
					<%=u.getFirstName()%>
					<%=u.getLastName()%></p>
				<p>
					Gender:
					<%=u.getUserGender()%>
				</p>
				<p>
					Role:
					<%=u.getRole()%></p>
			</div>
		</div>
	</div>
</div>
<%
								}
							%>
<br>
<br>

<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 text-center">

	<ul class="pagination">
		<%
										for (int i = 1; i <= totalpagesu; i++) {
									%>
		<li><a href="/reviewBook/search?page=<%=i%>&category=Users&searchParam=${searchParam}"><%=i%></a></li>
		<%
										}
									%>
	</ul>
	<%
									}
								%>
	<%
									if (users.size() == 0) {
								%>
	<h3>No Users !</h3>
	<%
									}
								%>
</div>







