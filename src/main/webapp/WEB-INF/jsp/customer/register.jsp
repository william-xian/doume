<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<title>Administrator</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta content="width=device-width,initial-scale=1" name="viewport">
<link href="${resourceRoot}/css/jquery.mobile.min.css" rel="stylesheet" type="text/css"/>
<script src="${resourceRoot}/js/jquery.min.js" type="text/javascript"></script>
<script src="${resourceRoot}/js/customer.js" type="text/javascript"></script>
<script src="${resourceRoot}/js/jquery.mobile.min.js" type="text/javascript"></script>
<link href="${resourceRoot}/css/style.css" rel="stylesheet"/>
</head>
<body>
	<!--  -->
	<div data-role="page" id="customer-register">
		<div data-role="header">
			<h2>Customer Register</h2>
		</div>
		<div data-role="content">
			<form:form modelAttribute="OM" action="/doume/customer/register" method="POST">
				<label for="userName">UserName:</label>
				<form:input type="text" path="userName" id="userName" />
				<label for="password">Password:</label>
				<form:input type="password" path="password" id="password" />
				<label for="password">Confirm:</label> 
				<input type="password" name="password2" id="password2" />
				<input type="submit" value="Register"/>
				<a href="/doume/customer/home" data-role="button" data-rel="back" data-inline="true">Cancel</a>
			</form:form>
		</div>
		<div data-role="footer"></div>
	</div>
</body>
</html>