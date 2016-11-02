<%@page import="com.mindfire.review.web.models.User"%>
<%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>All Users Page</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<link rel="stylesheet" href="/reviewBook/assets/css/bootstrap.min.css">
<link rel="stylesheet"
	href="/reviewBook/assets/css/font-awesome.min.css">
</head>
<body>
	<nav class="navbar navbar-default navbackground">
	<div class="container-fluid">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target="#myNavbar">
				<span class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="/reviewBook/home">ReviewApp</a>
		</div>
		<div class="collapse navbar-collapse" id="myNavbar">
			<ul class="nav navbar-nav navbar-right">
				<li><a href="/reviewBook/home">Home</a></li>
				<li class="dropdown"><a class="dropdown-toggle"
					data-toggle="dropdown" href="#">Add <span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a href="/reviewBook/addbook">Add Book</a></li>
						<li><a href="/reviewBook/addauthor">Add Author</a></li>
						<li><a href="/reviewBook/linkBookAndAuthor">Link Book and
								Author</a></li>
					</ul></li>
				<li class="dropdown"><a class="dropdown-toggle"
					data-toggle="dropdown" href="#">All <span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a href="/reviewBook/books">All Books</a></li>
						<li><a href="/reviewBook/authors">All Authors</a></li>
						<li><a href="/reviewBook/users">All Users</a></li>
					</ul></li>
					<li><img src="/reviewBook/uploads/${userImage}"
						class="img-circle img-responsive" alt="user"
						style="height: 60px; width: 60px;"></li>
				<li><a href="/reviewBook/profile">Profile </a></li>
				<li><a href="/reviewBook/logout"><span
						class="glyphicon glyphicon-log-in"></span> Logout</a></li>
			</ul>
		</div>
	</div>
	</nav>
	<div class="container-fluid">
		<div class="row">
			<div
				class="col-lg-6 col-lg-offset-3 col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">
				
				<%
					for (User u : (List<User>) request.getAttribute("userslist")) {
				%>
				<div class="col-lg-6 col-md-6 col-sm-6">
					<div class="col-lg-4" style="padding: 2px;">
						<img src="/reviewBook/uploads/<%= u.getUserImage() %>" alt="book1"
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
				<div class="col-lg-6 col-lg-offset-3 col-md-8 col-md-offset-2 col-sm-10 col-sm-offset-1">
				<ul class="pagination text-center">
						<%
							for (int i = 1; i <= (int) request.getAttribute("totalpages"); i++) {
						%>
						<li><a
							href="/reviewBook/users?pageno=<%= i%>"><%=i%></a></li>
						<%
							}
						%>
					</ul>
					</div>
					
			</div>
		</div>
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