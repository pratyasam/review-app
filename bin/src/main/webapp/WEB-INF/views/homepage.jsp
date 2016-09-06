<%--
  Created by IntelliJ IDEA.
  User: pratyasa
  Date: 25/8/16
  Time: 3:38 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">

<head>
    <title> Home Page </title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
    <link rel="stylesheet" href="/assets/css/bootstrap.min.css">
    <link rel="stylesheet" href="/assets/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="/assets/css/animation.css">
    <link rel="stylesheet" type="text/css" href="/assets/css/home-pagestyle.css">
    <link href='https://fonts.googleapis.com/css?family=Bree+Serif' rel='stylesheet' type='text/css'>
</head>
<style>
    .hero {
        position: relative;
        height: 640px;
        background-image: url(/assets/img/1.jpg);
        background-size: cover;
        background-position: bottom;
    }

    .overlay {
        position: absolute;
        top: 0;
        right: 0;
        left: 0;
        bottom: 0;
        background-color: rgba(0, 0, 0, 0.5);
    }

    .btn-default {
        border: 1px solid black;
        border-width: 2px;
        font-size: 120%;
    }

    .btn-author {
        border: 1px solid black;
        border-width: 2px;
        font-size: 120%;
    }

    .btn-default a {
        background-color: #fff;
        color: #000;
    }

    .btn-author {
        background-color: #ccc;
        color: #000;
    }

    .btn-default:hover {
        background-color: #000;
        color: #fff;
    }

    .btn-author:hover {
        background-color: #000;
        color: #fff;
    }

    a:hover {
        color: #fff;
    }

    a {
        color: black;
    }

    .section-1 {
        background-color: #ccc;
        background-size: cover;
        position: relative;
    }

    .overlay2 {
        height: 0%;
        width: 100%;
        position: fixed;
        z-index: 1;
        top: 0;
        left: 0;
        background-color: rgba(0, 0, 0, 0.9);
        overflow-y: hidden;
        transition: 0.5s;
    }

    .overlay2-content {
        position: relative;
        top: 25%;
        width: 100%;
        text-align: center;
        margin-top: 30px;
    }

    .overlay2 .closebtn {
        position: absolute;
        top: 20px;
        right: 45px;
        font-size: 60px;
    }

    @media screen and (max-height: 450px) {
        .overlay2 {
            overflow-y: auto;
        }

        .overlay2 a {
            font-size: 20px
        }

        .overlay2 .closebtn {
            font-size: 40px;
            top: 15px;
            right: 35px;
        }
    }
    .user {
        display: inline-block;
        height: 60px;
        width: 60px;
    }

    .user img {
        float: left;
        padding-top:10px;
        height: 60px;
        width: 60px;
        border-radius: 50%;
    }
</style>

<body>
<div id="myNav" class="overlay2"><a href="javascript:void(0)" class="closebtn" onclick="closeNav()">&times;</a>
    <div class="overlay2-content">
        <div class="form">
            <form action="#">
                <fieldset>
                    <div class="col-lg-6 col-lg-offset-3">
                        <div class="form-group">
                            <input type="text" class="form-control" id="search" placeholder="Search Authors or Books">
                            <br>
                            <br>
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
<div class="container-fluid" style="padding:0px;">
    <div class="hero row">
        <div class="container">
            <% if (session.getAttribute("userName") == null) {%>
              <nav class="navbar navbar-default" style="z-index:100;margin-top:40px;">
                <div class="container-fluid">
                    <div class="navbar-header">
                        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar"><span
                                class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span>
                        </button>
                        <a class="navbar-brand" href="#">ReviewApp</a></div>
                    <div class="collapse navbar-collapse" id="myNavbar1">
                        <ul class="nav navbar-nav navbar-right">
                            <li><a href="#"><i class="fa fa-search fa-2x" onclick="openNav()"></i> </a></li>
                            <li><a href="/home">Home</a></li>
                            <li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown" href="#">Option <span
                                    class="caret"></span></a>
                                <ul class="dropdown-menu">
                                    <li><a href="/book">Page 1-1</a></li>
                                    <li><a href="#">Page 1-2</a></li>
                                    <li><a href="#">Page 1-3</a></li>
                                </ul>
                            </li>
                            <li><a href="/login"><span class="glyphicon glyphicon-log-in"></span> LogIn</a></li>
                            <li><a href="/signup"><span class="glyphicon glyphicon-user"></span> SignUp</a></li>
                        </ul>
                    </div>
                </div>
            </nav>
            <%}%>
            <%if (session.getAttribute("userName") != null) {%>
            <nav class="navbar navbar-default" style="z-index:100;margin-top:40px;">
                <div class="container-fluid">
                    <div class="navbar-header">
                        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar"><span
                                class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span>
                        </button>
                        <a class="navbar-brand" href="#">ReviewApp</a></div>
                    <div class="collapse navbar-collapse" id="myNavbar">
                        <ul class="nav navbar-nav navbar-right">
                            <li><a href="#"><i class="fa fa-search fa-2x" onclick="openNav()"></i> </a></li>
                            <li><a href="/home">Home</a></li>
                            <li><a href="/profile">Profile</a>

                            </li>
                            <li><a href="/profile">Hello User</a></li>
                            <li>
                                <div class="user"><a><img src="/assets/img/user.png" class="img-circle" alt="book" /></a></div>
                            </li>
                            <li><a href="/login"><span class="glyphicon glyphicon-log-in"></span> LogOut</a></li>

                        </ul>
                    </div>
                </div>
            </nav>
            <%}%>

        </div>
        <div class="overlay">
            <div class="container">
                <div class="hero-quote" style="margin-top: 300px; text-align:center;z-index:100;color: #FFF;">
                    <blockquote> We don’t need a list of rights and wrongs, tables of dos and don’ts: we need books,
                        time, and silence. Thou shalt not is soon forgotten, but Once upon a time lasts forever. <cite>
                            Philip Pullman
                        </cite></blockquote>
                </div>
            </div>
        </div>
    </div>
    <br>
    <div class="col-lg-12" style="padding:0px;">
        <div class="row" style="padding:50px;">
            <div class="text-center">
                <h3><b>New Books</b></h3></div>
            <div class="book-background" style="padding:50px;">
                <div class="col-lg-2" style="height:30%">
                    <div style="border: 2px solid #ccc;"><img src="assets/img/book.jpg" alt="book"
                                                              style="width:100%; height:100%;"></div>
                </div>
                <div class="col-lg-2" style="height:30%">
                    <div style="border: 2px solid #ccc;"><img src="assets/img/book.jpg" alt="book"
                                                              style="width:100%; height:100%;"></div>
                </div>
                <div class="col-lg-2" style="height:30%">
                    <div style="border: 2px solid #ccc;"><img src="assets/img/book.jpg" alt="book"
                                                              style="width:100%;height:100%;"></div>
                </div>
                <div class="col-lg-2" style="height:30%">
                    <div style="border: 2px solid #ccc;"><img src="assets/img/book.jpg" alt="book"
                                                              style="width:100%;height:100%;"></div>
                </div>
                <div class="col-lg-2" style="height:30%">
                    <div style="border: 2px solid #ccc;"><img src="assets/img/book.jpg" alt="book"
                                                              style="width:100%;height:100%;"></div>
                </div>
                <div class="col-lg-2" style="height:30%">
                    <div style="border: 2px solid #ccc;"><img src="assets/img/book.jpg" alt="book"
                                                              style="width:100%;height:100%;"></div>
                </div>
            </div>
        </div>
        <div class="col-lg-2 col-lg-offset-5"><a href="#" class="btn btn-default">+More Books</a></div>
        <br>
        <br>
        <br>
        <br>
        <br>
        <br></div>
    <div class="col-lg-12 section-1">
        <div class="row">
            <div class="text-center">
                <h3><b>Meet the Authors !</b></h3></div>
            <div class="content" style="padding:50px;">
                <div class="col-lg-2">
                    <div style="border: 2px solid #ccc; background-color:white;">
                        <div style="height:40%;padding:10px;">
                            <div style="padding:30px;"><img src="assets/img/book.jpg" class="img-circle" alt="book"
                                                            style="width:100%;"></div>
                            <div class="text-center" style="font-size:150%;"><b>Author Name</b></div>
                        </div>
                    </div>
                </div>
                <div class="col-lg-2">
                    <div style="border: 2px solid #ccc; background-color:white;">
                        <div style="height:40%;padding:10px;">
                            <div style="padding:30px;"><img src="assets/img/book.jpg" class="img-circle" alt="book"
                                                            style="width:100%;"></div>
                            <div class="text-center" style="font-size:150%;"><b>Author Name</b></div>
                        </div>
                    </div>
                </div>
                <div class="col-lg-2">
                    <div style="border: 2px solid #ccc; background-color:white;">
                        <div style="height:40%;padding:10px;">
                            <div style="padding:30px;"><img src="assets/img/book.jpg" class="img-circle" alt="book"
                                                            style="width:100%;"></div>
                            <div class="text-center" style="font-size:150%;"><b>Author Name</b></div>
                        </div>
                    </div>
                </div>
                <div class="col-lg-2">
                    <div style="border: 2px solid #ccc; background-color:white;">
                        <div style="height:40%;padding:10px;">
                            <div style="padding:30px;"><img src="assets/img/book.jpg" class="img-circle" alt="book"
                                                            style="width:100%;"></div>
                            <div class="text-center" style="font-size:150%;"><b>Author Name</b></div>
                        </div>
                    </div>
                </div>
                <div class="col-lg-2">
                    <div style="border: 2px solid #ccc; background-color:white;">
                        <div style="height:40%;padding:10px;">
                            <div style="padding:30px;"><img src="assets/img/book.jpg" class="img-circle" alt="book"
                                                            style="width:100%;"></div>
                            <div class="text-center" style="font-size:150%;"><b>Author Name</b></div>
                        </div>
                    </div>
                </div>
                <div class="col-lg-2">
                    <div style="border: 2px solid #ccc; background-color:white;">
                        <div style="height:40%;padding:10px;">
                            <div style="padding:30px;"><img src="assets/img/book.jpg" class="img-circle" alt="book"
                                                            style="width:100%;"></div>
                            <div class="text-center" style="font-size:150%;"><b>Author Name</b></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <br>
        <br>
        <div class="col-lg-2 col-lg-offset-5"><a href="#" class="btn btn-author">+More Authors</a></div>
        <br>
        <br>
        <br>
        <br>
        <br>
        <br></div>
    <div class="col-lg-12">
        <div class="footer text-center">
            <small>Copyright &copy; Pratyasha</small>
        </div>
    </div>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
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

