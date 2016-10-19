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
					<jsp:include page="bookpartial.jsp"/>
					</div>
				</div>
				<div id="Authors" class="tab-pane fade">
					<h3>Authors</h3>
					<div class="row col-lg-8 col-lg-offset-2">
						<div class="col-xs-12">
							<jsp:include page="authorpartial.jsp"/>
							</div>
						</div>
					</div>
				</div>
				<div id="Users" class="tab-pane fade">
					<h3>Users</h3>
					<div class="row col-lg-8 col-lg-offset-2">
						<div class="col-xs-12">
							<jsp:include page="userpartial.jsp"/>
							</div>
						</div>
					</div>

				</div>

			</div>

		</div>
	</div>
	<%
		String searchParam = (String) request.getAttribute("searchparam");
	%>

	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>

	<script
		src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

	<script type="text/javascript" src="/reviewBook/assets/js/animation.js"></script>

	<script type="text/javascript">
		$(document).ready(function() {
			$('#Books').click(function(event) {
				$.get('/reviewBook/search', {
					searchParam : searchParam,
					searchCategory : 'BOOKS'
				}, function(data) {
					$('#result').html(data);
				});
			});
		});
	</script>

	<script type="text/javascript">
		$(document).ready(function() {
			$('#Authors').click(function(event) {
				$.get('/reviewBook/search', {
					searchParam : searchParam,
					searchCategory : 'AUTHORS'
				}, function(data) {
					$('#result').html(data);
				});
			});
		});
	</script>

	<script type="text/javascript">
		$(document).ready(function() {
			$('#Users').click(function(event) {
				$.get('/reviewBook/search', {
					searchParam : searchParam,
					searchCategory : 'USERS'
				}, function(data) {
					$('#result').html(data);
				});
			});
		});
	</script>

</body>
</html>