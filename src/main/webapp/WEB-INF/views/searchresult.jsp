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
<%@page import="com.mindfire.review.enums.SearchType"%>
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
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/rateYo/2.2.0/jquery.rateyo.min.css">
</head>
<body>
	<%
		SearchType searchCategory = (SearchType) request.getAttribute("SEARCH_TYPE");
		String activePaneId = "Books";
		if (searchCategory != null) {
			// Set the tab active
			switch (searchCategory) {
				case AUTHORS :
					activePaneId = "Authors";
					break;

				case USERS :
					activePaneId = "Users";
					break;
			}
		} else {
			response.sendRedirect("/reviewBook/");
		}
	%>

	<%
		if (session.getAttribute("userName") != null && (session.getAttribute("role").equals("normal"))) {
	%>
	<nav class="navbar navbar-default navbackground main">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target="#myNavbar">
					<span class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="/">ReviewApp</a>
			</div>
			<div class="collapse navbar-collapse" id="myNavbar">
				<ul class="nav navbar-nav navbar-right">
					<li><a href="#">Home</a></li>
					<li class="dropdown"><a class="dropdown-toggle"
						data-toggle="dropdown" href="#">Options<span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a href="/reviewBook/userupload">Update Info</a></li>
							<li><a href="#">Delete Account</a></li>
							<li><a href="/reviewBook/profile">Profile</a></li>
						</ul></li>
					<li><img src="/reviewBook/uploads/${userImage}"
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
				<a class="navbar-brand" href="/">ReviewApp</a>
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
					<li><img src="/reviewBook/uploads/${userImage}"
						class="img-circle img-responsive" alt="book"
						style="height: 60px; width: 60px;"></li>
					<li><a href="/reviewBook/profile">Profile </a></li>
					<li><a href="/reviewBook/logout"><span
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
				<a class="navbar-brand" href="/">ReviewApp</a>
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
		<div class="col-lg-10 col-lg-offset-1">
			<ul class="nav nav-tabs">
				<li
					class="<%=((searchCategory == SearchType.BOOKS) ? "active" : "inactive")%>"
					id="BooksTab"><a data-toggle="tab" href="#Books">Books</a></li>
				<li
					class="<%=((searchCategory == SearchType.AUTHORS) ? "active" : "inactive")%>"
					id="AuthorsTab"><a data-toggle="tab" href="#Authors">Authors</a></li>
				<li
					class="<%=((searchCategory == SearchType.USERS) ? "active" : "inactive")%>"
					id="UsersTab"><a data-toggle="tab" href="#Users">Users</a></li>
			</ul>
			<div class="tab-content">
				<div id="Books" class="tab-pane fade in">
					<div class="row">
						<div class="col-xs-12" id="Books_result">
							<%
								if (searchCategory == SearchType.BOOKS) {
							%>
							<jsp:include page="bookpartial.jsp" />
							<%
								}
							%>
						</div>
					</div>
				</div>
				<div id="Authors" class="tab-pane fade in">
					<div class="row">
						<div class="col-xs-12" id="Authors_result">
							<%
								if (searchCategory == SearchType.AUTHORS) {
							%>
							<jsp:include page="authorpartial.jsp" />
							<%
								}
							%>
						</div>
					</div>
				</div>
				<div id="Users" class="tab-pane fade in">
					<div class="row">
						<div class="col-xs-12" id="Users_result">

							<%
								if (searchCategory == SearchType.USERS) {
							%>
							<jsp:include page="userpartial.jsp" />
							<%
								}
							%>
						</div>
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
	

	<script type="text/javascript">
		$(function(){
			var activePaneId = '<%=activePaneId%>';
			if (activePaneId) {
				$('#' + activePaneId).addClass('active');
			}
		});
	</script>

	<script>
	
		$('#AuthorsTab').click(function(event) {
			var searchCategory = 'AUTHORS';
			var searchParam = '<%=(String) request.getAttribute("searchParam")%>';
			$.get('/reviewBook/search', {
				searchParam :searchParam,
				category:searchCategory
			}, function(data) {
				$('#Authors_result').html(data);	
			});
		});
    
		$('#BooksTab').click(function(event) {
			var searchCategory = 'BOOKS';
			var searchParam = '<%=(String) request.getAttribute("searchParam")%>';
			$.get('/reviewBook/search', {
				searchParam : searchParam,
				category:searchCategory
			}, function(data) {
				$('#Books_result').html(data);
				
			});
		});
	
	</script>

	<script>
	
		$('#UsersTab').click(function(event) {
			var searchCategory = 'USERS';
			var searchParam = '<%=(String) request.getAttribute("searchParam")%>';
							$.get('/reviewBook/search', {
								searchParam : searchParam,
								category : searchCategory
							}, function(data) {
								$('#Users_result').html(data);

							});
						});
	</script>
	
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/rateYo/2.2.0/jquery.rateyo.min.js"></script>
<script type="text/javascript">
$(function() {
	var ratedEntities = $('div[data-rateyo1]');

	for (var i = 0; i < ratedEntities.length; i++) {
		$(ratedEntities[i]).rateYo({
			rating : $(ratedEntities[i]).attr('data-rating'),
			readOnly : true,
			starWidth : "20px"
		});
	}

});
</script>

</body>
</html>