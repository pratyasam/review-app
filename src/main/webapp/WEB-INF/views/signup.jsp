<%@ page import="com.mindfire.review.exceptions.UserExistException"%><%--
  Created by IntelliJ IDEA.
  User: pratyasa
  Date: 1/8/16
  Time: 5:53 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%
	UserExistException ex = (UserExistException) request.getAttribute("userExist");
%>
<html lang="en">

<head>
<title>Singup Page</title>
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
				<div
					class="social-wrapper card col-lg-10 col-lg-offset-1 bg-primary">
					<div class="social-icons col-lg-6 col-lg-offset-3">
						<div class="icon-container col-lg-6 col-md-6 col-sm-6 col-xs-6">
							<span class="fa fa-facebook-official"></span>
						</div>
						<div class="icon-container col-lg-6 col-md-6 col-sm-6 col-xs-6">
							<span class="fa fa-google-plus-official"></span>
						</div>
					</div>
				</div>
				<div class="heading col-lg-12">
					<h4>It's Free !</h4>
				</div>
				<div class="panel-body">
					<div class="col-lg-12 ">
						<form:form name="signupFrom" id="signupForm"
							cssClass="form-horizontal" method="post" modelAttribute="signUp"
							action="signup">
							<form:errors path="*" cssClass="alert alert-danger" element="div" />
							<%
								if (ex != null) {
							%>
							<div class="alert alert-danger"><%=ex.getMessage()%>
							</div>
							<%
								}
							%>
							<fieldset class="pad-tb-20">
								<div class="form-group">
									<div class="col-lg-12">
										<div class="input-group">
											<span class="input-group-addon"> <span
												class="fa fa-user fa-2x"></span>
											</span>
											<form:input path="firstName" type="text"
												cssClass="form-control" id="inputFirstName"
												placeholder="First Name" required="required" />
										</div>
									</div>
								</div>
								<br>
								<div class="form-group">
									<div class="col-lg-12">
										<div class="input-group">
											<span class="input-group-addon"> <span
												class="fa fa-user fa-2x"></span>
											</span>
											<form:input path="lastName" type="text"
												cssClass="form-control" id="inputLatName"
												placeholder="Last Name" required="required" />
										</div>
									</div>
								</div>
								<br>
								<div class="form-group">
									<div class="col-lg-12">
										<div class="input-group">
											<span class="input-group-addon"> <span
												class="fa fa-user-plus fa-2x"></span>
											</span>
											<form:input path="userName" type="text"
												cssClass="form-control" id="inputUsername"
												placeholder="Username" required="required" />
										</div>
									</div>
								</div>
								<br>
								<div class="form-group">
									<div class="col-lg-12">
										<div class="input-group">
											<span class="input-group-addon"> <span
												class="fa fa-key fa-2x"></span>
											</span>
											<form:input path="password" type="password"
												cssClass="form-control" id="inputPassword"
												placeholder="Password" required="required" />

										</div>
									</div>
								</div>
								<br>
								<div class="form-group">
									<label class="col-lg-2 control-label"><b>Gender</b></label>
									<div class="col-lg-10">
										<div class="radio">
											<label> <form:radiobutton path="gender"
													name="optionsRadios" id="optionsRadios1" value="Female"
													required="required" /> Female
											</label>
										</div>
										<div class="radio">
											<label> <form:radiobutton path="gender"
													name="optionsRadios" id="optionsRadios1" value="Male"
													required="required" /> Male
											</label>
										</div>
										<div class="radio">
											<label> <form:radiobutton path="gender"
													name="optionsRadios" id="optionsRadios2" value="Others"
													required="required" /> Others
											</label>
										</div>
									</div>
								</div>
								<br>
								<div class="form-group">
									<div class="col-lg-10 col-lg-offset-2">
										<input type="reset" class="btn btn-default" value="cancel" />
										<button type="submit" class="btn btn-primary">Sign-Up</button>
									</div>
								</div>

							</fieldset>
						</form:form>
						<br>
						<div class="col-lg-12">
							<p>
								Already Have an Account ? <a href="login"><b>LogIn</b></a> here.
							</p>
						</div>
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
