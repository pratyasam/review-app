<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>NewsLetter Form</title>
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<link rel="stylesheet" href="/reviewBook/assets/css/bootstrap.min.css">
<link rel="stylesheet"
	href="/reviewBook/assets/css/font-awesome.min.css">
</head>
<body>

	<div class="container-fluid">
		<div class="col-lg-10 col-lg-offset-1">
			<div class="row">
				<div class="col-lg-6 col-lg-offset-3">
					<div class="panel panel-primary">
						<div class="panel panel-heading">
							<h1>Sending e-mail with Spring MVC</h1>
						</div>
						<div class="panel-body">


							<form:form method="post" action="sendEmail.do"
								modelAttribute="email">
								<fieldset>

									<div class="form-group">
										<div class="col-lg-12">
											<div class="input-group">
												<span class="input-group-addon"> <span
													class="fa fa-pencil fa-2x"></span>
												</span>
												<form:input type="text" path="subject"
													cssClass="form-control" id="subject"
													placeholder="Insert subject " required="required" rows="3"
													cols="10" maxlength="4005"></form:input>
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
												<form:textarea path="message" cssClass="form-control"
													id="message" placeholder="Insert message "
													required="required" rows="3" cols="10" maxlength="4005"></form:textarea>

											</div>
										</div>
									</div>

									<div class="form-group">
										<div class="col-lg-10 col-lg-offset-2">
											<br> <input type="reset" class="btn btn-default"
												value="cancel" />
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


</body>
</html>