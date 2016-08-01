<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html lang="en">

<head>
    <title>LogIn Page</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
    <link rel="stylesheet" href="assets/css/bootstrap.min.css">
    <link rel="stylesheet" href="assets/css/font-awesome.min.css">
    <link href="assets/css/loginstyle.css" rel="stylesheet"> </head>

<body>
    <div class="container-fluid ">
        <div class="row">
            <div class="col-lg-4 col-lg-offset-4 col-md-8 col-md-offset-2 col-sm-8 col-sm-offset-2 col-xs-12">
                <div class="panel login-wrapper card panel-primary  ">
                    <div class="panel-heading text-center">
                        <h3 class="text-white">Log-In</h3> </div>
                    <div class="panel-body">
                        <form class="form-horizontal">
                            <fieldset class="pad-tb-20">
                                <div class="form-group">
                                    <div class="col-lg-12">
                                        <div class="input-group"> <span class="input-group-addon">
                                           <span class="fa fa-user fa-2x"></span> </span>
                                            <input type="text" class="form-control" id="inputEmail" placeholder="Email"> </div>
                                    </div>
                                    <br>
                                    <div class="form-group">
                                        <div class="col-lg-12">
                                            <div class="input-group"> <span class="input-group-addon">
                                           <span class="fa fa-key fa-2x pad" ></span> </span>
                                                <input type="password" class="form-control" id="inputPassword" placeholder="Password"> </div>
                                        </div>
                                    </div>
                                    <br>
                                    <div class="form-group">
                                        <div class="col-lg-10 col-lg-offset-2">
                                            <button type="reset" class="btn btn-default">Cancel</button>
                                            <button type="submit" class="btn btn-primary">Submit</button>
                                        </div>
                                    </div>
                            </fieldset>
                        </form>
                        </div>
                        <br>
                        <div class="footer text-center text-white"><small>Copyright &copy; Pratyasha</small></div>
                    </div>
                </div>
            </div>
        </div>
</body>

</html>