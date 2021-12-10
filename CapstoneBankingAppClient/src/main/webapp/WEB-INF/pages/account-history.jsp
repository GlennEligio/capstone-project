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
<title>Account History</title>
</head>
<body>
	<div class="container-fluid">
		<div class="card-header text-center">Account Transaction History</div>
		<div class="card-body">
			<div class="row">
				<div class="col-sm">
					<h4>Send Transaction History</h4>
					<table class="table table-bordered">
						<thead>
							<tr>
								<th scope="col">Transaction Id</th>
								<th scope="col">Received by</th>
								<th scope="col">Amount</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${fromTransactions}" var="transaction">
								<tr>
									<th>${transaction.transactionId}</th>
									<c:choose>
										<c:when test="${transaction.toAccount == null }">
											<th>WITHDRAW</th>
										</c:when>
										<c:otherwise>
											<td>${transaction.toAccount.accountNumber}</td>
										</c:otherwise>
									</c:choose>
									<td>${transaction.amount}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
				<div class="col-sm">
					<h4>Receive Transaction History</h4>
					<table class="table table-bordered">
						<thead>
							<tr>
								<th scope="col">Transaction Id</th>
								<th scope="col">Sent by</th>
								<th scope="col">Amount</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${toTransactions}" var="transaction">
								<tr>
									<th>${transaction.transactionId}</th>
									<c:choose>
										<c:when test="${transaction.fromAccount == null }">
											<th>DEPOSIT</th>
										</c:when>
										<c:otherwise>
											<td>${transaction.fromAccount.accountNumber}</td>
										</c:otherwise>
									</c:choose>
									<td>${transaction.amount}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
			<br> <br>
			<form action="home" method="get">
				<button class="btn btn-danger">Back</button>
			</form>
		</div>
		<div class="card-footer text-center">Bank Application</div>
	</div>
</body>
</html>