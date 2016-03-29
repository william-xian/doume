<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<title>Customer</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta content="width=device-width,initial-scale=1" name="viewport">
<link href="${resourceRoot}/css/jquery.mobile.min.css" rel="stylesheet" type="text/css"/>
<script src="${resourceRoot}/js/jquery.min.js" type="text/javascript"></script>
<script src="${resourceRoot}/js/customer.js" type="text/javascript"></script>
<script src="${resourceRoot}/js/jquery.mobile.min.js" type="text/javascript"></script>
<link href="${resourceRoot}/css/style.css" rel="stylesheet"/>
</head>
<body>
	<form:form modelAttribute="OM" action="/doume/customer/updateCustomer" method="POST">
		<div>
			<form:hidden path="userId"/>
		</div>
		<div>
			<label>UserName:</label>
			<form:input path="name" type="text"/>
		</div>
		<div>
			<label>Sex:</label>
			<form:radiobutton path="sex" value="0"/>Male
			<form:radiobutton path="sex" value="1"/>Female
		</div>
		<div>
			<label>Birthday:</label>
			<form:input path="birthday" id="birthday" type="date"></form:input>
			<form:errors path="birthday" cssClass="errorClass"></form:errors>
		</div>
		<div>
			<label>Address:</label>
			<form:input path="addr" type="text"></form:input>
			<form:errors path="addr" cssClass="errorClass"></form:errors>
		</div>
		<div>
			<label>Contact:</label>
			<form:input path="contact" type="date"></form:input>
			<form:errors path="contact" cssClass="errorClass"></form:errors>
		</div>
		<div>
			<input type="submit" value="Update">
		</div>
	</form:form>
</body>
</html>