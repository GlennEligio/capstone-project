<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
<title>Admin</title>
</head>
<body>
	<div class="container-fluid p-1">
		<div class="card-header text-center">Homepage</div>
		<div class="card-body">
			<div>
				<h2>Welcome, ${userSession.username }</h2>
				<a href="user-details">See Complete User details</a>
			</div>
			<div>
				<br>
				<div>
					<h3 class="text-center">Recent User Registered</h3>
				</div>
				<br>
				<table class="table table-bordered">
					<thead>
						<tr>
							<th scope="col">Username</th>
							<th scope="col">Date Registered</th>
							<th scope="col">Accounts</th>
							<th scope="col">Account List</th>
							<th scope="col">Total Balance</th>
							<th scope="col">Contact Number</th>
							<th scope="col">Active</th>
							<th scope="col">Roles</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${users}" var="user">
							<tr>
								<th scope="row">${user.username}</th>
								<td>${user.creationDate}</td>
								<td>${user.numberOfAccounts}</td>
								<td>${user.listOfAccounts}</td>
								<td>${user.totalBalance}</td>
								<td>${user.contactNumber}</td>
								<td>${user.active}</td>
								<td>${user.roles}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<div></div>
			</div>
			<div>
			<form:form method="get" action="/manage-user">
				<input type="hidden" name="query" value=""/>
				<button class="btn btn-success">Manage User</button>
			</form:form>
			</div>
			<div class="d-flex justify-content-end">
			<form:form method="post" action="/logout">
				<button class="btn btn-danger">Logout</button>
			</form:form>
			</div>
		</div>
		<div class="card-footer text-center">Bank Application</div>
	</div>
</body>
</html>