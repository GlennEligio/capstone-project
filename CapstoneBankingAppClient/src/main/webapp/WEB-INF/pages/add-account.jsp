<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
	crossorigin="anonymous">
<meta charset="ISO-8859-1">
<title>Add account</title>
</head>
<body>
	<div class="container-fluid">
		<div class="m-5">
			<div class="card-header text-center">Add Account</div>
			<div class="card-body">
				<form:form method="post" modelAttribute="add-account">
					<form:hidden path="accountNumber" value="69420"/>
					<form:label path="accountBalance">Initial balance</form:label>
					<form:input path="accountBalance" class="form-control" />
					<form:errors path="accountBalance" class="text-danger" />
					<br>
					<button class="btn btn-success">Add account</button>
				</form:form>
				<br> <br>
				<form action="home" method="get">
					<button class="btn btn-danger">Back</button>
				</form>
			</div>

			<div class="card-footer text-center">Bank Application</div>
		</div>
	</div>
</body>
</html>