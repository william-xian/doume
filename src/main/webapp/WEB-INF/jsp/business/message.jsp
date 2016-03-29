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
<div data-role="page" id="page">
	<div data-role="header">
		<h1>Message</h1>
	</div>
	<div data-role="content">
	<form:form modelAttribute="OM" method="POST" action="/doume/business/sendMessage">
		<div>
			Receiver：<form:input path="receiverId" type="number"></form:input>
			<form:errors path="receiverId" cssClass="errorClass"></form:errors>
			Subject：<form:input path="subject" type="text"></form:input>
			<form:errors path="subject" cssClass="errorClass"></form:errors>
			Content：<form:input path="content"></form:input>
			<form:errors path="content" cssClass="errorClass"></form:errors>
			<input type="submit" value="Submit"/>
		</div>
	</form:form>
	</div>
	<div data-role="footer">
		<h4>页面脚注</h4>
	</div>
</div>
</body>
</html>
