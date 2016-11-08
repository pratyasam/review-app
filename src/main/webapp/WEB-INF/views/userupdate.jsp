<%@ page
	import="com.mindfire.review.exceptions.AuthorExistenceException"%>
<%@ page import="com.mindfire.review.web.models.Author"%><%--
  Created by IntelliJ IDEA.
  User: pratyasa
  Date: 9/9/16
  Time: 2:35 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html lang="en">

<head>
<title>User Update Page</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<link rel="stylesheet" href="/reviewBook/assets/css/bootstrap.min.css">
<link rel="stylesheet"
	href="/reviewBook/assets/css/font-awesome.min.css">
<link href="/reviewBook/assets/css/style.css" rel="stylesheet">
<link href='https://fonts.googleapis.com/css?family=Bree+Serif'
	rel='stylesheet' type='text/css'>
</head>
<body>
	<div class="container-fluid">
		<div class="row">
			<div
				class="panel panel-default login-form-wrapper card col-lg-4 col-lg-offset-4 col-md-8 col-md-offset-2 col-sm-8 col-sm-offset-2 col-xs-12">
				<div class="panel-body">
					<div class="col-lg-12 ">
						<form:form name="signupFrom" id="signupForm"
							cssClass="form-horizontal" method="put"
							modelAttribute="userupdate" action="/reviewBook/users/${user.userId}">
							<fieldset class="pad-tb-20">


								<div class="form-group">
									<div class="col-lg-12">
										<div class="input-group">
											<span class="input-group-addon"> <span
												class="fa fa-key fa-2x"></span>
											</span>
											<form:input path="password" type="password"
												cssClass="form-control" id="inputPassword"
												placeholder="Enter new Password" />

										</div>
									</div>
								</div>
								<br>
								<div class="form-group">
									<div class="col-lg-12">
										<div class="input-group">
											<span class="input-group-addon"> <span
												class="fa fa-pencil fa-2x"></span>
											</span>
											<form:input type="email" path="email"
												cssClass="form-control" id="recipient"
												placeholder="new Email Address"></form:input>
										</div>
									</div>
								</div>
								<br>

								<div class="form-group">
									<div class="col-lg-10 col-lg-offset-2">
										<input type="reset" class="btn btn-default" value="cancel" />
										<button type="submit" class="btn btn-primary">Submit</button>
									</div>
								</div>

								<div class="footer text-center ">
									<small>Copyright &copy; Pratyasha</small>
								</div>
							</fieldset>
						</form:form>
						<br>
					</div>
				</div>

			</div>
			<div class="col-lg-12">
				<div class="footer text-center">
					<br> <br> <small>Copyright &copy; Pratyasha</small> <br>
					<br>
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