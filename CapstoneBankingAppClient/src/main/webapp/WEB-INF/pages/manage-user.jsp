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
<title>User History</title>
</head>
<body>
	<div class="container-fluid m-1">
		<div class="card-header text-center">Users list</div>
		<div class="card-body">
			<div>
				<br>
				<div>
					<h3 class="text-center">Users</h3>
				</div>
				<div>
					<form:form method="get">
						<br>
						<label for="queryId">Search</label>	
						<br>
						<input type="text" name="query" class="form-control" id="queryId" />
						<br>
						<button class="btn btn-success">Search</button>
						
					</form:form>
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
						<c:forEach items="${searchResult}" var="user">
							<tr>
								<th scope="row">${user.username}</th>
								<td>${user.creationDate}</td>
								<td>${user.numberOfAccounts}</td>
								<td>${user.listOfAccounts}</td>
								<td>${user.totalBalance}</td>
								<td>${user.contactNumber}</td>
								<td>${user.active}</td>
								<td>${user.roles}</td>
								<td><form action="/update-user-admin" method="get">
										<input type="hidden" name="username" value="${user.username}">
										<button class="btn btn-info">Update</button>
									</form></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<br> <a href="home" class="mr-1"><button
					class="btn btn-danger">Back</button></a>
		</div>
		<div class="card-footer text-center">Bank Application</div>
	</div>
</body>
</html>