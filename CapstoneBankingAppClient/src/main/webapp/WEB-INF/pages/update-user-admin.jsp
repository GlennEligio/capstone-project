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
<title>Update User</title>
</head>
<div class="container" style="width: 100vw">
	<div class="m-5">
		<div class="card-header text-center">Update User information</div>
		<div class="card-body">
			<form:form method="post" modelAttribute="updateUser" action="/update-user-admin">
				<form:hidden path="username"  value ="${updateUser.username }" class="form-control" />
				<br>
				<form:label path="password">Password</form:label>
				<form:password path="password" class="form-control" />
				<form:errors path="password" class="text-danger"/>
				<br>
				<form:label path="contactNumber">Contact Number</form:label>
				<form:input path="contactNumber" class="form-control" />
				<form:errors path="contactNumber" class="text-danger"/>
				<br>
				<form:label path="roles">ROLE</form:label>
				<form:select path="roles">
					<form:option value="ROLE_USER">USER</form:option>
					<form:option value="ROLE_ADMIN">ADMIN</form:option>
				</form:select>
				<br>
				<form:label path="active">ACTIVE</form:label>
				<form:radiobutton path="active" value="true"/>YES
				<form:radiobutton path="active" value="false"/>NO
				<br>
				<form:hidden path="creationDate" value="${updateUser.creationDate }"/>
				<form:hidden path="numberOfAccounts" value="${updateUser.numberOfAccounts }"/>
				<form:hidden path="listOfAccounts" value="${updateUser.listOfAccounts }"/>
				<form:hidden path="totalBalance" value="${updateUser.totalBalance }"/>
				<button class="btn btn-success">Update</button>
			</form:form>
			<br>
			<br>
			<form action="home" method="get">
				<button class="btn btn-danger">Back</button>
			</form>
		</div>
		<div class="card-footer text-center">Bank Application</div>
	</div>
</div>
</body>
</html>