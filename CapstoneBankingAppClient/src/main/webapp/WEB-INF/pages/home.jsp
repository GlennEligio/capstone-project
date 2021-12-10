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
<title>Home</title>
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
					<h3 class="text-center">Accounts</h3>
				</div>
				<br>
				<table class="table table-bordered">
					<thead>
						<tr>
							<th scope="col">Account Number</th>
							<th scope="col">Account Balance</th>
							<th scope="col" colspan="4" style="text-align: center">Action</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${accountSession}" var="account">
							<tr>
								<th scope="row">${account.accountNumber}</th>
								<td>${account.accountBalance}</td>
								<td><form action="deposit-account" method="get">
										<input type="hidden" name="accountNumber"
											value="${account.accountNumber}">
										<button class="btn btn-success">Deposit</button>
									</form></td>
								<td><form action="withdraw-account" method="get">
										<input type="hidden" name="accountNumber"
											value="${account.accountNumber}">
										<button class="btn btn-warning">Withdraw</button>
									</form></td>
								<td><form action="delete-account" method="post">
										<input type="hidden" name="accountNumber"
											value="${account.accountNumber}">
										<button class="btn btn-danger">Delete</button>
									</form></td>
								<td><form action="account-history" method="get">
										<input type="hidden" name="accountNumber"
											value="${account.accountNumber}">
										<button class="btn btn-info">History</button>
									</form></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<div></div>
			</div>
			<div class="d-flex justify-content-evenly">
				<a href="add-account"><button class="btn btn-success">Add
						Account</button></a> <a href="transfer"><button class="btn btn-success">Transfer</button></a>
			</div>
			<div>
				<br>
				<div>
					<h3 class="text-center">Recent Transactions</h3>
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
						<c:forEach items="${recentTransactions}" var="transaction">
							<tr>
								<th scope="row">${transaction.transactionId}</th>
								<th scope="row">${ transaction.transactionType}</th>
								<td>${transaction.accountUsed }</td>
								<td>${transaction.accountTarget }</td>
								<td>${transaction.amount }</td>
							</tr>
						</c:forEach>
						<tr>
							<th colspan="5" class="text-center"><a href="user-history">See
									All Transaction</a></th>
						</tr>
					</tbody>

				</table>
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