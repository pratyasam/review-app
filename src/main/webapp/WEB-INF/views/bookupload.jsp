<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<title>Book Profile Picture Upload</title>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" />
<link rel="stylesheet" href="/reviewBook/assets/css/bootstrap.min.css" />
<link rel="stylesheet"
	href="/reviewBook/assets/css/font-awesome.min.css" />
<link href='https://fonts.googleapis.com/css?family=Bree+Serif'
	rel='stylesheet' type='text/css' />
<link rel="stylesheet" type="text/css" href="assets/css/animation.css">
</head>
<body>

	<div class="container-fluid">
		<div
			class="col-lg-6 col-lg-offset-3 col-md-6 col-md-offset-3 col-sm-10 col-sm-offest-1">

			<div class="panel panel-primary" style="margin-top:100px;">
				<div class="panel-heading text-center">
					<h3>Book Profile Picture Upload</h3>
				</div>
				<div class="panel-body">
					
					 <form action="/reviewBook/books/${bookId}/bookupload" method="post" enctype="multipart/form-data">
						<fieldset>
							<div class="form-group">
								<div class="col-lg-12">
									<img id="photo" src="#"
										style="height: 30%; width: 40%; display: none" /> <br> <br>
									<label> <b>Select a file.</b></label> <input type="file" name="file"  onchange="loadFile(event)"/>

								</div>
							</div>

							<div class="form-group">
								<div>

									<button type="submit" class="btn btn-primary pull-right">Submit</button>
								</div>
							</div>
						</fieldset>
					</form>
					<a class="btn btn-default pull-right" href="/reviewBook/profile">profile</a>

				</div>
			</div>
		</div>
	</div>

	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
	<script
		src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="/reviewBook/assets/js/animation.js"></script>
	<script>
		var loadFile = function(event) {
			var output = document.getElementById('photo');
			output.src = URL.createObjectURL(event.target.files[0]);
			document.getElementById('photo').style.display = 'block';
		};
	</script>
</body>
</html>