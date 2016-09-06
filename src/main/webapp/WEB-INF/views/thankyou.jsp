<%--
  Created by IntelliJ IDEA.
  User: pratyasa
  Date: 2/8/16
  Time: 5:36 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html lang="en">

<head>
    <title>Successfully Registered </title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
    <link rel="stylesheet" href="/reviewBook/assets/css/bootstrap.min.css">
    <link rel="stylesheet" href="/reviewBook/assets/css/font-awesome.min.css">
    <link href="/reviewBook/assets/css/thankyou-style.css" rel="stylesheet">
    <link href='https://fonts.googleapis.com/css?family=Bree+Serif' rel='stylesheet' type='text/css'>
</head>

<body>
<div class="container-fluid">
    <div class="row">
        <div class=" col-lg-6 col-lg-offset-3 ">
            <div class="panel panel-default thankyou-wrapper">
                <div class="panel-body">
                    <h1 class="text-center text-uppercase"> Welcome Aboard ${userName}!</h1>
                    <p> Thank you for registering with us. <a href="login"><b>Click Here</b></a> to continue. </p>
                </div>
            </div>
            <div class="footer text-center text-white">
                <small>Copyright &copy; Pratyasha</small>
            </div>
        </div>
    </div>
</div>
</body>

</html>
