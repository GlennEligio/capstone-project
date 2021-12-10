<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
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
<title>User History</title>
</head>
<body>
	<div class="container-fluid m-1">
		<div class="card-header text-center">User Transaction History</div>
		<div class="card-body">
			<div>
				<br>
				<div>
					<h3 class="text-center">User Transactions</h3>
				</div>
				<br>
				<table class="table table-bordered">
					<thead>
						<tr>
							<th scope="col">Transaction ID</th>
							<th scope="col">Type</th>
							<th scope="col">Account used</th>
							<th scope="col">Received/Sent By</th>
							<th scope="col">Amount</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${userHistory}" var="transaction">
							<tr>
								<th scope="row">${transaction.transactionId}</th>
								<th scope="row">${ transaction.transactionType}</th>
								<td>${transaction.accountUsed }</td>
								<td>${transaction.accountTarget }</td>
								<td>${transaction.amount }</td>
							</tr>
						</c:forEach>
					</tbody>

				</table>
			</div>
			<br>
			<a href="home" class="mr-1"><button class="btn btn-danger">Back</button></a>
		</div>
		<div class="card-footer text-center">Bank Application</div>
	</div>
</body>
</html>