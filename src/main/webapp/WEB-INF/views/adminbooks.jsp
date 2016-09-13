<%@page import="com.mindfire.review.web.dto.BookAuthorListDto"%>
<%@page import="com.mindfire.review.web.models.*"%>
<%@ page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">

<head>
<title>All books</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<link rel="stylesheet" href="assets/css/bootstrap.min.css">
<link rel="stylesheet" href="assets/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="assets/css/animation.css">
<link rel="stylesheet" type="text/css"
	href="assets/css/home-pagestyle.css">
<link href='https://fonts.googleapis.com/css?family=Bree+Serif'
	rel='stylesheet' type='text/css'>
</head>
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

.sidenav {
	height: 100%;
	width: 200px;
	position: fixed;
	z-index: 1;
	top: 0;
	left: 0;
	background-color: #111;
	overflow-x: hidden;
	padding-top: 60px;
}

.sidenav a {
	padding: 8px 8px 8px 32px;
	text-decoration: none;
	font-size: 15px;
	color: #818181;
	display: block;
	transition: 0.3s;
	cursor: pointer;
}

.sidenav a:hover, .offcanvas a:focus {
	color: #f1f1f1;
}

.main {
	margin-left: 200px;
}

ul {
	list-style-type: none;
}

@media screen and (max-height: 450px) {
	.sidenav {
		padding-top: 15px;
	}
	.sidenav a {
		font-size: 18px;
	}
}
</style>

<body>

	<nav class="navbar navbackground">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target="#myNavbar">
					<span class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="/reviewBook/profile">${userFirstName} ${userLastName}</a>
			</div>
			<div class="collapse navbar-collapse" id="myNavbar">
				<ul class="nav navbar-nav navbar-right">
					<li><a href="/reviewBook/home">Home</a></li>
					<li class="dropdown"><a class="dropdown-toggle"
						data-toggle="dropdown" href="#">Add <span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a href="/reviewBook/addbook">Add Book</a></li>
							<li><a href="/reviewBook/addauthor">Add Author</a></li>
							<li><a href="/reviewBook/linkBookAndAuthor">Link Book and Author</a></li>
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


	<div id="mySidenav" class="sidenav" style="padding: 10px;">
		<h3>Genres :</h3>

		<h4 class="text-center">Fiction:</h4>
		<ul style="list-style-type: none; padding: 0px;">
			<li><a href="#">Action and Adventure</a></li>
			<li><a href="#">Children's</a></li>
			<li><a href="#">Anthologies</a></li>
			<li><a href="#">Classics</a></li>
			<li><a href="#">Horror</a></li>
			<li><a href="#">Poetry</a></li>
			<li><a href="#">Romance</a></li>
			<li><a href="#">Young Adult</a></li>
			<li><a href="#">Science Fiction</a></li>
		</ul>

		<h4 class="text-center">Non Fiction:</h4>
		<ul style="padding: 0px;">
			<li><a href="#">Art, Architecture and Photography</a></li>
			<li><a href="#">Biographies</a></li>
			<li><a href="#">Children's</a></li>
			<li><a href="#">Food and Drinks</a></li>
			<li><a href="#">Humour</a></li>
			<li><a href="#"> Health and Lifestyle</a></li>
			<li><a href="#">History</a></li>
			<li><a href="#">Politics and Philosophy</a></li>
			<li><a href="#"> Science and Nature</a></li>
			<li><a href="#">Travel</a></li>
		</ul>
	</div>
	<div class="container-fluid">
		<div>
			<img src="/reviewBook/assets/img/imageedit__7035736840.jpg"
				alt="book" style="height: 540px; margin-left: 150px; width: 1668px;" /> <br> <br>
		</div>
		<div class="row">
			<div class="col-lg-6 col-lg-offset-3 col-md-6 col-md-offset-3">
				<%
					for (BookAuthorListDto bal : (List<BookAuthorListDto>) request.getAttribute("bookauthorlist")) {
						Book book = bal.getBook();
						List<Author> authors = bal.getAuthorList();
						
				%>
				<div class="col-lg-6">
					<div class="row">
						<div class="col-lg-4" style="padding: 2px;">
							<img src="assets/img/book1.jpg" alt="book1" style="width: 100%" />
						</div>

						<div class="col-lg-8">
							<div class="panel panel-default">
								<div class="panel-body">
									<a href="/reviewBook/books/<%= book.getBookId()%>">
										<p style="font-size: 150%; border-bottom: 2px #CCC solid;"><%=book.getBookName()%></p>
									</a>
									<%
										for (Author a : authors) {
									%>
									<h6><%=a.getAuthorName()%></h6>
									<%
										}
									%>
									<span class="glyphicon glyphicon-star"></span><span
										class="glyphicon glyphicon-star"></span><span
										class="glyphicon glyphicon-star"></span><span
										class="glyphicon glyphicon-star"></span><span
										class="glyphicon glyphicon-star"></span> <br>
									<div class="sidebar-box">
										<p><%=book.getBookDescription()%></p>
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
			</div>
		</div>
		<br>
		<br>
		<jsp:include page='contact.jsp' />
		<div class="col-lg-12">
			<div class="footer text-center">
				<br> <br> <small>Copyright &copy; Pratyasha</small> <br>
				<br>
			</div>
		</div>
	</div>


	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
	<script
		src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</body>

</html>