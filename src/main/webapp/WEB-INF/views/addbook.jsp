<%@ page import="com.mindfire.review.exceptions.BookExistException" %><%--
  Created by IntelliJ IDEA.
  User: pratyasa
  Date: 4/8/16
  Time: 11:01 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<% BookExistException bookExistException = (BookExistException) request.getAttribute("bookExist");%>

<html>

<head>
    <title>Add Book</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
    <link rel="stylesheet" href="/reviewBook/assets/css/bootstrap.min.css">
    <link rel="stylesheet" href="/reviewBook/assets/css/font-awesome.min.css">
    <link href="/reviewBook/assets/css/style.css" rel="stylesheet">
    <link href='https://fonts.googleapis.com/css?family=Bree+Serif' rel='stylesheet' type='text/css'>
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
				<a class="navbar-brand" href="/reviewBook/profile">${userFirstName} ${userLastName}</a>
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
    <div class="col-lg-10 col-lg-offset-1">
        <div class="row">
            <div class="col-lg-6 col-lg-offset-3">
                <div class="panel panel-primary">
                    <div class="panel panel-heading">
                        <h3>Add Book</h3></div>
                    <div class="panel-body">
                        <form:form method="post" id="book" action="addbook" cssClass="form-horizontal" name="book"
                                   modelAttribute="book">
                            <form:errors path="*" element="div" cssClass="alert alert-danger"></form:errors>

                            <% if (bookExistException != null) {%>
                            <div class="alert alert-danger"><%bookExistException.getMessage();%></div>
                            <% } %>

                            <fieldset class="pad-tb-20">
                                <div class="form-group">
                                    <div class="col-lg-12">
                                        <div class="input-group"> <span class="input-group-addon">
                                                    <span class="fa fa-star fa-2x"></span> </span>
                                            <form:input path="bookName" type="text" cssClass="form-control"
                                                        id="inputBookName" placeholder="Book Name" required="required"/>
                                        </div>
                                    </div>
                                </div>
                                <br>
                                <div class="form-group">
                                    <div class="col-lg-12">
                                        <div class="input-group"> <span class="input-group-addon">
                                                <span class="fa fa-bookmark fa-2x"></span> </span>
                                            <form:textarea path="bookDescription" cssClass="form-control"
                                                           id="inputBookDescription"
                                                           placeholder="Insert Book Description here"
                                                           required="required" rows="3" cols="10"
                                                           maxlength="4005"></form:textarea>
                                        </div>
                                    </div>
                                </div>
                                <br>
                                <div class="form-group">
                                    <div class="col-lg-12">
                                        <div class="input-group"> <span class="input-group-addon">
                                                <span class="fa fa-star fa-2x"></span> </span>
                                            <form:input path="bookGenre" type="text" cssClass="form-control"
                                                        id="inputBookGenre" placeholder="BookGenre"
                                                        required="required"/></div>
                                    </div>
                                </div>
                                <br>
                                <div class="form-group">
                                    <div class="col-lg-12">
                                        <div class="input-group"> <span class="input-group-addon">
                                                <span class="fa fa-book fa-2x"></span> </span>
                                            <form:input path="bookIsbn" type="text" cssClass="form-control"
                                                        id="inputBookIsbn" placeholder="Book ISBN" required="required"/>
                                        </div>
                                    </div>
                                </div>
                                <br>
                                <div class="form-group">
                                    <div class="col-lg-12">
                                        <div class="input-group"> <span class="input-group-addon">
                                                <span class="fa fa-book fa-2x"></span> </span>
                                            <form:input path="bookRating" type="number" cssClass="form-control"
                                                        id="inputBookRating" placeholder="Book Rating between 1-5"
                                                        max="5" min="1" step="0.1" required="required"/></div>
                                    </div>
                                </div>
                                <br>
                                <div class="form-group">
                                    <div class="col-lg-12">
                                        <div class="input-group"> <span class="input-group-addon">
                                                <span class="fa fa-book fa-2x"></span> </span>
                                            <form:input path="bookCost" type="number" cssClass="form-control"
                                                        id="inputBookCost" placeholder="Book Cost" step="0.01"
                                                        required="required" min="1"/></div>
                                    </div>
                                </div>
                                <br>
                                <div class="form-group">
                                    <div class="col-lg-12">
                                        <div class="input-group"> <span class="input-group-addon">
                                                <span class="fa fa-bookmark fa-2x"></span> </span>
                                            <form:input path="bookLink" type="text" cssClass="form-control"
                                                        id="inputBookLink" placeholder="Book Link"/></div>
                                    </div>
                                </div>
                                <br>
                                <div class="form-group">
                                    <div class="col-lg-12">
                                        <div class="input-group"> <span class="input-group-addon">
                                                <span class="fa fa-bookmark fa-2x"></span> </span>
                                            <form:textarea path="bookReview" cssClass="form-control"
                                                           id="inputBookReview" placeholder="Insert Book Review here"
                                                           required="required" rows="3" cols="10"
                                                           maxlength="4005"></form:textarea></div>
                                    </div>
                                </div>
                                <br>
                                <div class="form-group">
                                    <div class="col-lg-10 col-lg-offset-2">
                                        <input type="reset" class="btn btn-default" value="cancel"/>
                                        <button type="submit" class="btn btn-primary">submit <span
                                                class="fa fa-book fa-2x"></span></button>
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
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</body>

</html>