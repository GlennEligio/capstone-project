<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
	crossorigin="anonymous">
<meta charset="ISO-8859-1">
<title>Login</title>
</head>
<body>
	<div class="container-fluid">
		<div class="m-5">
			<div class="card-header text-center">Welcome to Login page</div>
			<div class="card-body">
				<p class="text-danger">${loginValid }</p>
				<br>
				<form:form method="post" action="/authenticateTheUser">
					<div>
						<c:if test="${param.error != null }">
							<div class="alert alert-danger col-xs-offset-1 col-xs-10">
								Invalid username and password.</div>
						</c:if>
					</div>
					<div>
						<p>Username</p>
						<input class="form-control" type="text" name="username"
							placeholder="Username" />
					</div>
					<div>
						<p>Password</p>
						<input class="form-control" type="text" name="password"
							placeholder="Password" />
					</div>
					<div>
						<button class="btn btn-success">Add account</button>
					</div>
				</form:form>
				<div>
					<a href="register-user">Don't have user yet? Register here</a>
				</div>
			</div>
			<div class="card-footer text-center">Bank Application</div>
		</div>
	</div>
</body>
</html>