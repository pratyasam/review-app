<%@ page import="com.mindfire.review.exceptions.LoginFailException" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%
    LoginFailException lex = (LoginFailException) request.getAttribute("exception");
%>
<!DOCTYPE html>
<html>

<head>
    <title>LogIn Page</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="/reviewBook/assets/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="/reviewBook/assets/css/style.css">
    <link rel="stylesheet" href="/reviewBook/assets/css/font-awesome.min.css">
</head>

<body>
<div class="container-fluid">
    <div id="loginbox"
         class="mainbox col-lg-4 col-lg-offset-4 col-md-6 col-md-offset-3 col-sm-6 col-sm-offset-3 pad-tb-20 ">

        <div class="panel panel-primary wrapperbox">
            <div class="panel-heading">
                <div class="panel-title text-center">
                    <h4 class="text-white">Log In</h4></div>
            </div>
            <div class="panel-body">
                <form:form name="loginForm" id="loginForm" cssClass="form-horizontal" method="POST" action="login"
                           modelAttribute="login">
                    <form:errors path="*" cssClass="alert alert-danger" element="div"/>
                    <% if (lex != null) { %>
                    <div class="alert alert-danger"><%= lex.getMessage() %>
                    </div>
                    <% } %>
                    <div class="input-group"><span class="input-group-addon"><i class="fa fa-user fa-2x"></i></span>
                        <form:input id="user" path="userName" type="text" cssClass="form-control" placeholder="User"
                                    required="required"/></div>
                    <br>
                    <div class="input-group"><span class="input-group-addon"><i class="fa fa-lock fa-2x"></i></span>
                        <form:input path="password" id="password" type="password" cssClass="form-control"
                                    placeholder="Password" required="required"/></div>
                    <br>
                    <div class="form-group">
                        <!-- Button -->
                        <div class="col-sm-12 controls">
                            <button type="submit" class="btn btn-primary pull-right"><i
                                    class="glyphicon glyphicon-log-in"></i> Sign In
                            </button>

                        </div>
                        <br>
                    </div>
                </form:form>
            </div>
            <div class="panel-footer">
                <p>New Here ? <a href="signup"><b>Sign Up</b></a> now.</p>
            </div>
        </div>
        <div class="footer text-center text-white ">
            <small>Copyright &copy; Pratyasha</small>
        </div>
    </div>
</div>

<script type="text/javascript" src="http://code.jquery.com/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>

</body>

</html>