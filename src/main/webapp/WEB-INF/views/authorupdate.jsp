<%@ page
	import="com.mindfire.review.exceptions.AuthorExistenceException"%>
<%@ page import="com.mindfire.review.web.models.Author"%><%--
  Created by IntelliJ IDEA.
  User: pratyasa
  Date: 18/8/16
  Time: 2:35 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%
	AuthorExistenceException ex = (AuthorExistenceException) request.getAttribute("authorexists");
%>
<
<html>

<head>
<title>Update Author</title>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" />
<link rel="stylesheet" href="/reviewBook/assets/css/bootstrap.min.css" />
<link rel="stylesheet"
	href="/reviewBook/assets/css/font-awesome.min.css" />
<link href="/reviewBook/assets/css/style.css" rel="stylesheet" />
<link href='https://fonts.googleapis.com/css?family=Bree+Serif'
	rel='stylesheet' type='text/css' />
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
					<li><a href="home">Home</a></li>
					<li class="dropdown"><a class="dropdown-toggle"
						data-toggle="dropdown" href="#">Add <span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a href="/reviewBook/addbook">Add Book</a></li>
							<li><a href="/reviewBook/addauthor">Add Author</a></li>
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
		<div class="col-lg-10 col-lg-offset-1">
			<div class="row">
				<div class="col-lg-6 col-lg-offset-3">
					<div class="panel panel-primary">
						<div class="panel panel-heading">
							<h3>Update Author</h3>
						</div>
						<div class="panel-body">
							<form:form method="put" id="author"
								action="/reviewBook/authors/${author.authorId}" Class="form-horizontal"
								name="author" modelAttribute="authorupdatedto">
								<form:errors path="*" element="div"
									cssClass="alert alert-danger"></form:errors>
								<%
									if (ex != null) {
								%>
								<div class="alert alert-danger">
									<%
										ex.getMessage();
									%>
								</div>
								<%
									}
								%>
								<fieldset class="pad-tb-20">
									<div class="form-group">
										<div class="col-lg-12">
											<div class="input-group">
												<span class="input-group-addon"> <span
													class="fa fa-star fa-2x"></span>
												</span>
												<form:input type="text" path="authorName"
													cssClass="form-control" id="inputAuthorName"
													 required="required" />
											</div>
										</div>
									</div>
									<br>
									<div class="form-group">
										<div class="col-lg-12">
											<div class="input-group">
												<span class="input-group-addon"> <span
													class="fa fa-bookmark fa-2x"></span>
												</span>
												<form:textarea cssClass="form-control"
													path="authorDescription" id="inputAuthorDescription" value="xyx xyx xyx yxy yxyx"
													required="required" rows="3" cols="10" maxlength="4005"></form:textarea>
											</div>
										</div>
									</div>
									<br>
									<div class="form-group">
										<div class="col-lg-12">
											<div class="input-group">
												<span class="input-group-addon"> <span
													class="fa fa-star fa-2x"></span>
												</span>
												<form:input type="text" cssClass="form-control"
													path="authorGenre" id="inputAuthorGenre"
													 required="required" />
											</div>
										</div>
									</div>
									<br>
									<div class="form-group">
										<div class="col-lg-12">
											<div class="input-group">
												<span class="input-group-addon"> <span
													class="fa fa-book fa-2x"></span>
												</span>
												<form:input type="number" path="authorRating"
													cssClass="form-control" id="inputAuthorRating"
													placeholder="Author Rating between 1-5" max="5" min="1"
													required="required" step="0.1" />
											</div>
										</div>
									</div>
									<br>
									<div class="form-group">
										<div class="col-lg-10 col-lg-offset-2">
											<input type="reset" class="btn btn-default" value="cancel" />
											<button type="submit" class="btn btn-primary">
												submit <span class="fa fa-book fa-2x"></span>
											</button>
										</div>
									</div>
									<br>
									<div class="footer text-center ">
										<small>Copyright &copy; Pratyasha</small>
									</div>
								</fieldset>
							</form:form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
	<script
		src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</body>

</html>
