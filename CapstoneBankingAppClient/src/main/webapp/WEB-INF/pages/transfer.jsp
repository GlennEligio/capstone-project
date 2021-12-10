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
<title>Transfer</title>
</head>
<body>
	<div class="container" style="width: 100vw">
		<div class="m-5">
			<div class="card-header text-center">Transfer money</div>
			<div class="card-body">
				<form:form method="post" modelAttribute="transaction">
					<form:hidden path="fromAccount.accountBalance" value="1"/>
					<form:hidden path="toAccount.accountBalance" value="1"/>
					<form:label path="fromAccount.accountNumber">Please select of one your account numbers</form:label>
					<form:select path="fromAccount.accountNumber" class="form-control">
						<c:forEach items="${accounts}" var="account">
							<form:option value="${account.accountNumber }" />
						</c:forEach>
					</form:select>
					<form:errors path="fromAccount.accountNumber" class="text-danger"/>
					<br>
					<form:label path="toAccount.accountNumber">Account Number to receive money</form:label>
					<form:input path="toAccount.accountNumber" class="form-control" />
					<form:errors path="toAccount.accountNumber" class="text-danger"/>
					<br>
					<form:label path="amount">Amount to send</form:label>
					<form:input path="amount" class="form-control" />
					<form:errors path="amount" class="text-danger"/>
					<br>
					<button class="btn btn-success">Send</button>
					<br>
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