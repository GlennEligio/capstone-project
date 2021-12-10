<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
	crossorigin="anonymous">
<meta charset="ISO-8859-1">
<title>User details</title>
</head>
<body>
<div class="container-fluid p-5">
		<div>
			<strong>User name: </strong><br>
			${userSession.username} 
			<br> 
			<strong>Date registered: </strong><br>
			${userSession.creationDate} 
			<br> 
			<strong>Contact Number: </strong><br>
			${userSession.contactNumber}
			<br> 
			<strong>Number of Accounts: </strong><br>
			${userSession.numberOfAccounts} 
			<br> 
			<strong>List of Accounts: </strong><br>
			${userSession.listOfAccounts} 
			<br> 
			<strong>Total Balance: </strong><br>
			${userSession.totalBalance}
			<br> 
			<a href="update-user" class="mr-1"><button class="btn btn-success">Update User</button></a> 
			<a href="delete-user" class="mr-1"><button class="btn btn-warning">Delete User</button></a>
			<a href="home" class="mr-1"><button class="btn btn-danger">Back</button></a>
		</div></div>
</body>
</html>