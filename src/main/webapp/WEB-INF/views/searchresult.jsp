<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.mindfire.review.web.models.*"%>
<%@page import="com.mindfire.review.web.models.Author"%>
<%@page import="com.mindfire.review.web.dto.BookAuthorListDto"%>
<%@page import="com.mindfire.review.web.models.Book"%>
<%@page import="java.util.List"%>
<%@page import="com.mindfire.review.web.models.ReviewAuthor"%>
<html>
<head>
<title>${searchparam}</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<link rel="stylesheet" href="/reviewBook/assets/css/bootstrap.min.css">
<link rel="stylesheet"
	href="/reviewBook/assets/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css"
	href="/reviewBook/assets/css/animation.css">
</head>
<body>
	<%
		if (session.getAttribute("userName") != null) {
	%>
	<nav class="navbar navbar-default navbackground main">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target="#myNavbar">
					<span class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#">ReviewApp</a>
			</div>
			<div class="collapse navbar-collapse" id="myNavbar">
				<ul class="nav navbar-nav navbar-right">
					<li><a href="#">Home</a></li>
					<li class="dropdown"><a class="dropdown-toggle"
						data-toggle="dropdown" href="#">Options<span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a href="#">Update Info</a></li>
							<li><a href="#">Delete Account</a></li>
							<li><a href="/reviewBook/profile">Profile</a></li>
						</ul></li>
					<li><img src="/reviewBook/assets/img/book.jpg"
						class="img-circle img-responsive" alt="book"
						style="height: 60px; width: 60px;"></li>
					<li><a href="/reviewBook/logout"><span
							class="glyphicon glyphicon-log-in"></span> LogOut</a></li>
				</ul>
			</div>
		</div>
	</nav>
	<%
		}
	%>
	<%
		if (session.getAttribute("userName") != null && (session.getAttribute("role").equals("admin")
				|| session.getAttribute("role").equals("moderator"))) {
	%>
	<nav class="navbar navbar-default navbackground">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target="#myNavbar">
					<span class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="/reviewBook/profile">${userFirstName}
					${userLastName}</a>
			</div>
			<div class="collapse navbar-collapse" id="myNavbar">
				<ul class="nav navbar-nav navbar-right">
					<li><a href="/reviewBook/home">Home</a></li>
					<li class="dropdown"><a class="dropdown-toggle"
						data-toggle="dropdown" href="#">Add <span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a href="/reviewBook/addbook">Add Book</a></li>
							<li><a href="/reviewBook/addauthor">Add Author</a></li>
							<li><a href="/reviewBook/linkBookAndAuthor">Link Book
									and Author</a></li>
						</ul></li>
					<li class="dropdown"><a class="dropdown-toggle"
						data-toggle="dropdown" href="#">All <span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a href="/reviewBook/books">All Books</a></li>
							<li><a href="/reviewBook/authors">All Authors</a></li>
							<li><a href="/reviewBook/users">All Users</a></li>
						</ul></li>
					<li><a href="/reviewBook/profile">Profile </a></li>
					<li><a href="logout"><span
							class="glyphicon glyphicon-log-in"></span> Logout</a></li>
				</ul>
			</div>
		</div>
	</nav>
	<%
		}
	%>
	<%
		if (session.getAttribute("userName") == null) {
	%>
	<nav class="navbar navbar-default navbackground main">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target="#myNavbar">
					<span class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#">ReviewApp</a>
			</div>
			<div class="collapse navbar-collapse" id="myNavbar">
				<ul class="nav navbar-nav navbar-right">
					<li><a href="/reviewBook/home">Home</a></li>
					<li><a href="/reviewBook/login"><span
							class="glyphicon glyphicon-log-in"></span> LogIn</a></li>
					<li><a href="/reviewBook/signup"><span
							class="glyphicon glyphicon-user"></span> SignUp</a></li>
				</ul>
			</div>
		</div>
	</nav>
	<%
		}
	%>
	<div class="container-fluid">
		<div class="col-lg-6 col-lg-offset-3">
			<ul class="nav nav-tabs">
				<li class="active"><a data-toggle="tab" href="#Books">Books</a></li>
				<li><a data-toggle="tab" href="#Authors">Authors</a></li>
				<li><a data-toggle="tab" href="#Users">Users</a></li>
			</ul>
			<div class="tab-content">
				<div id="Books" class="tab-pane fade in active">
					<h3>Books</h3>
					<div class="col-lg-12 col-md-12">
					<div class="row">
					<div class="col-lg-6 col-md-6"></div>
					</div>
				</div>
				<div id="Authors" class="tab-pane fade">
					<h3>Authors</h3>
					<div class="row col-lg-8 col-lg-offset-2">
				<div class="col-xs-12">
					<%
						for (Author a : (List<Author>) request.getAttribute("authors")) {
					%>
					<div class="col-lg-6">
						<div class="row">
							<div class="col-lg-4" style="padding: 2px;">
								<img src="assets/img/book1.jpg" alt="book1" style="width: 100%" />
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
						<%
							if (((List<Author>) request.getAttribute("authors")).size() != 0) {
						%>
						<ul class="pagination">
							<%
								for (int i = 1; i <= (int) request.getAttribute("totalpage"); i++) {
							%>
							<li><a href="/reviewBook/search?pageno=<%=i%>"><%=i%></a></li>
							<%
								}
							%>
						</ul>
						<%
							}
						%>
						<%
							if (((List<Author>) request.getAttribute("authors")).size() == 0) {
						%>
						<h3>No Authors !</h3>
						<%
							}
						%>
					</div>
				</div>
			</div>
				</div>
				<div id="Users" class="tab-pane fade">
					<h3>Users</h3>
					<p>Sed ut perspiciatis unde omnis iste natus error sit
						voluptatem accusantium doloremque laudantium, totam rem aperiam.</p>
				</div>
			</div>
		</div>

	</div>
	</div>

	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
	<script
		src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="/reviewBook/assets/js/animation.js"></script>
</body>
</html>