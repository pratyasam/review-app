<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<title>Link Book and Author Page</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<link rel="stylesheet" href="assets/css/bootstrap.min.css">
<link rel="stylesheet" href="assets/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="assets/css/animation.css">
<link href='https://fonts.googleapis.com/css?family=Bree+Serif'
	rel='stylesheet' type='text/css'>
</head>
<style>
select, select.form-control {
	padding: 12px;
	padding-right: 20px;
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
			<a class="navbar-brand" href="/reviewBook/profile">${userFirstName}
				${userLastName}</a>
		</div>
		<div class="collapse navbar-collapse" id="myNavbar">
			<ul class="nav navbar-nav navbar-right">
				<li><a href="home">Home</a></li>
				<li class="dropdown"><a class="dropdown-toggle"
					data-toggle="dropdown" href="#">Add <span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a href="/reviewBookaddbook">Add Book</a></li>
						<li><a href="/reviewBookaddauthor">Add Author</a></li>
					</ul></li>
				<li><a href="profile">Profile </a></li>
				<li><a href="logout"><span
						class="glyphicon glyphicon-log-in"></span> Logout</a></li>
			</ul>
		</div>
	</div>
	</nav>
	<div class="container-fluid">
		<div class="row">
			<div class="col-lg-6 col-lg-offset-3 col-md-6 col-md-offset-3">
				<div class="panel panel-primary">
					<div class="panel panel-heading">
						<h3>Link Book and Author</h3>
					</div>
					<div class="panel-body">
						<form:form method="post" modelAttribute="bookauthorlink"
							name="linkForm" action="linkBookAndAuthor"
							cssClass="form-horizontal">
							<fieldset>
								<div class="form-group">
									<div class="col-lg-12 col-md-12 col-sm-12">
										<b>Select Book : </b>
										<form:select path="bookName" items="${booklist}"></form:select>
									</div>
								</div>
								<div class="form-group">
									<div class="col-lg-12 col-md-12 col-sm-12">
										<b>Select Author : </b>
										<form:select path="authorName" items="${authorlist}"></form:select>
									</div>
								</div>
								<div class="form-group">
									<div class="col-lg-10 col-lg-offset-2">
										<input type="reset" class="btn btn-default" value="cancel" />
										<button type="submit" class="btn btn-primary">Submit</button>
									</div>
								</div>
							</fieldset>
						</form:form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
	<script
		src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="assets/js/animation.js"></script>
</body>
</html>