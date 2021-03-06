<%--
  Created by IntelliJ IDEA.
  User: pratyasa
  Date: 25/8/16
  Time: 3:38 PM
  To change this template use File | Settings | File Templates.
--%>
<%@page import="com.mindfire.review.web.models.Author"%>
<%@page import="java.util.List"%>
<%@page import="com.mindfire.review.web.models.Book"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html lang="en">

<head>
<title>Home Page</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<link rel="stylesheet" href="/reviewBook/assets/css/bootstrap.min.css">
<link rel="stylesheet" href="/reviewBook/assets/css/font-awesome.min.css">
<link rel="stylesheet" href="/reviewBook/assets/css/homepage-style.css">

</head>


<body>
	<div id="myNav" class="overlay2">
		<a href="javascript:void(0)" class="closebtn" onclick="closeNav()">&times;</a>
		<div class="overlay2-content">
			<div class="form">
				<form action="/reviewBook/search" method="get">
					<fieldset>
						<div class="col-lg-6 col-lg-offset-3">
							<div class="form-group">
								<input name="searchParam" type="text" class="form-control" id="search" placeholder="Search Authors or Books" />
								<input name="page" value="1" type="hidden" />
								<br> <br>
								<div class="col-lg-10 col-lg-offset-2">
									<button type="submit" class="btn btn-primary">Submit</button>
								</div>
							</div>
						</div>
					</fieldset>
					</form>
			</div>
		</div>
	</div>
	<div class="container-fluid">
		<div class="hero row">
			<div class="container">
				<%
					if (session.getAttribute("userName") == null ) {
				%>
				<nav class="navbar navbar-default"
					style="z-index: 100; margin-top: 40px;">
					<div class="container-fluid">
						<div class="navbar-header">
							<button type="button" class="navbar-toggle"
								data-toggle="collapse" data-target="#myNavbar">
								<span class="icon-bar"></span> <span class="icon-bar"></span> <span
									class="icon-bar"></span>
							</button>
							<a class="navbar-brand" href="/">ReviewApp</a>
						</div>
						<div class="collapse navbar-collapse" id="myNavbar">
							<ul class="nav navbar-nav navbar-right">
								<li><a href="#"><i class="fa fa-search fa-2x"
										onclick="openNav()"></i> </a></li>
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
				<%
					if (session.getAttribute("userName") != null ) {
				%>
				<nav class="navbar navbar-default"
					style="z-index: 100; margin-top: 40px;">
					<div class="container-fluid">
						<div class="navbar-header">
							<button type="button" class="navbar-toggle"
								data-toggle="collapse" data-target="#myNavbar">
								<span class="icon-bar"></span> <span class="icon-bar"></span> <span
									class="icon-bar"></span>
							</button>
							<a class="navbar-brand" href="/">ReviewApp</a>
						</div>
						<div class="collapse navbar-collapse" id="myNavbar">
							<ul class="nav navbar-nav navbar-right">
								<li><a href="#"><i class="fa fa-search fa-2x"
										onclick="openNav()"></i> </a></li>
								<li><a href="/reviewBook/home">Home</a></li>
								<li><a href="/reviewBook/profile">Profile</a></li>
								<li><a href="/reviewBook/profile">Hello ${userName}</a></li>
								<li>
									<div class="user">
										<a><img src="/reviewBook/uploads/${userImage}"
											class="img-circle" alt="user" /></a>
									</div>
								</li>
								<li><a href="/reviewBook/logout"><span
										class="glyphicon glyphicon-log-in"></span> LogOut</a></li>

							</ul>
						</div>
					</div>
				</nav>
				<%
					}
				%>

			</div>
			<div class="overlay">
				<div class="container">
					<div class="hero-quote"
						style="margin-top: 300px; text-align: center; z-index: 100; color: #FFF;">
						<blockquote>
							We don’t need a list of rights and wrongs, tables of dos and
							don’ts: we need books, time, and silence. Thou shalt not is soon
							forgotten, but Once upon a time lasts forever. <cite>
								Philip Pullman </cite>
						</blockquote>
					</div>
				</div>
			</div>
		</div>
		<br>
		<div class="col-lg-12" style="padding: 0px;">
			<div class="row">
				<div class="text-center">
					<h3>
						<b>New Books !</b>
					</h3>
				</div>
				<div class="book-background" style="padding: 50px;">
				
				<%for (Book b : (List<Book>) request.getAttribute("booklist")){ %>
					<div class="col-lg-2 col-md-3 col-sm-6 col-xs-12">
						<div style="border: 1px solid #ccc;">
							<a href="/reviewBook/books/<%= b.getBookId()%>"><img src="/reviewBook/uploads/<%= b.getBookImage() %>" alt="book"
								style="width: 100%; height: 220px;"></a></a>
						</div>
					</div>
				<%} %>
					
				</div>
			</div>
			<br> <br> <br>
			<div class="col-lg-2 col-lg-offset-5">
				<a href="/reviewBook/books" class="btn btn-default">+More Books</a>
			</div>
			<br> <br> <br> <br> <br> <br>
		</div>
		<div class="col-lg-12 section-1">
			<div class="row">
				<div class="text-center">
					<h3>
						<b>Meet the Authors !</b>
					</h3>
				</div>
				<div class="content" style="padding: 50px;">
				    <% for(Author a : (List<Author>) request.getAttribute("authorlist")) { %>
						<div class="col-lg-2 col-md-3 col-sm-6 col-xs-12">
							<div style="border: 1px solid #ccc; background-color: white;">
								<div style="padding: 10px;">
									<div>
										<a href="reviewBook/authors/<%= a.getAuthorId() %>"><img src="/reviewBook/uploads/<%= a.getAuthorImage() %>" class="img-circle"
											alt="author" style="width: 100%; height: 200px;"></a>
									</div>
									<div class="text-center"><%= a.getAuthorName() %></div>
								</div>
							</div>
						</div>
					<%} %>
				</div>
			</div>
			<br> <br>
			<div class="col-lg-2 col-lg-offset-5">
				<a href="/reviewBook/authors" class="btn btn-author">+More
					Authors</a>
			</div>
			<br> <br> <br> <br> <br> <br>
		</div>

		<div class="col-lg-12 col-md-12 col-sm-12" style="padding: 0px;">
			<br> <br> <br>
			<div class="col-lg-12 ">
				<iframe width="100%" height="415"
					src="https://www.youtube.com/embed/OVMsoDBO1jA?list=PLnkWWmhVvhc00tAQ4J7a1dpPlwI78JJxq"
					frameborder="0" allowfullscreen></iframe>
			</div>
			<div class="col-lg-6">
				<ul>
					<li></li>
					<li></li>
					<li></li>
					<li></li>
					<li></li>
				</ul>
			</div>
		</div>
		<br> <br> <br>
		<div class="col-lg-12 col-md-12 col-sm-12">
			<br>
			<div id="myCarousel" class="carousel" data-ride="carousel">
				<!-- Indicators -->
				<ol class="carousel-indicators">
					<li data-target="#myCarousel" data-slide-to="0" class="active"></li>
					<li data-target="#myCarousel" data-slide-to="1"></li>
					<li data-target="#myCarousel" data-slide-to="2"></li>
					<li data-target="#myCarousel" data-slide-to="3"></li>
					<li data-target="#myCarousel" data-slide-to="4"></li>
					<li data-target="#myCarousel" data-slide-to="5">
					<li>
				</ol>

				<!-- Wrapper for slides -->
				<div class="carousel-inner" role="listbox">

					<div class="item active">
						<img src="/reviewBook/assets/img/abg9.jpg" alt="Chania"
							width="460" height="345">
						<div class="carousel-caption">
							<h3>Chania</h3>
							<p>The atmosphere in Chania has a touch of Florence and
								Venice.</p>
						</div>
					</div>

					<div class="item">
						<img src="/reviewBook/assets/img/abg9.jpg" alt="Chania"
							width="460" height="345">
						<div class="carousel-caption">
							<h3>Chania</h3>
							<p>The atmosphere in Chania has a touch of Florence and
								Venice.</p>
						</div>
					</div>

					<div class="item">
						<img src="/reviewBook/assets/img/cover3.jpg" alt="Flower"
							width="460" height="345">
						<div class="carousel-caption">
							<h3>Flowers</h3>
							<p>Beatiful flowers in Kolymbari, Crete.</p>
						</div>
					</div>

					<div class="item">
						<img src="/reviewBook/assets/img/abg9.jpg" alt="Flower"
							width="460" height="345">
						<div class="carousel-caption">
							<h3>Flowers</h3>
							<p>Beatiful flowers in Kolymbari, Crete.</p>
						</div>
					</div>

					<div class="item">
						<img src="/reviewBook/assets/img/cover3.jpg" alt="Flower"
							width="460" height="345">
						<div class="carousel-caption">
							<h3>Flowers</h3>
							<p>Beatiful flowers in Kolymbari, Crete.</p>
						</div>
					</div>

					<div class="item">
						<img src="/reviewBook/assets/img/abg9.jpg" alt="Flower"
							width="460" height="345">
						<div class="carousel-caption">
							<h3>Flowers</h3>
							<p>Beatiful flowers in Kolymbari, Crete.</p>
						</div>
					</div>

				</div>

				<!-- Left and right controls -->
				<a class="left carousel-control" href="#myCarousel" role="button"
					data-slide="prev"> <span
					class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
					<span class="sr-only">Previous</span>
				</a> <a class="right carousel-control" href="#myCarousel" role="button"
					data-slide="next"> <span
					class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
					<span class="sr-only">Next</span>
				</a>
			</div>
		</div>

	</div>
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


	<script>
		function openNav() {
			document.getElementById("myNav").style.height = "100%";
		}

		function closeNav() {
			document.getElementById("myNav").style.height = "0%";
		}
	</script>
</body>

</html>

