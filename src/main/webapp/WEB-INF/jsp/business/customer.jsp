<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Administrator</title>
<meta content="width=device-width,initial-scale=1" name="viewport">
<link href="${resourceRoot}/css/jquery.mobile.min.css" rel="stylesheet" type="text/css"/>
<script src="${resourceRoot}/js/jquery.min.js" type="text/javascript"></script>
<script src="${resourceRoot}/js/business.js" type="text/javascript"></script>
<script src="${resourceRoot}/js/jquery.mobile.min.js" type="text/javascript"></script>
<link href="${resourceRoot}/css/style.css" rel="stylesheet"/>
</head>
<body>
	<!--  -->
	<div data-role="page" id="business-customer">
		<div data-role="header">
			<a href="#business-customer" data-role="button" data-icon="back" data-rel="back">Back</a>
			<h3>SendMessage</h3>
		</div>
		<div data-role="content">
			<fieldset>
				<legend>Member Info</legend>
				<h3>UserName:Tome</h3>
				<h3>Phone:123123123</h3>
				<h3>Birthday:123123123</h3>
			</fieldset>
			<a href="#sendMssage" data-role="button" data-icon="info" data-inline="true">SendMessage</a>
			<a href="#Credit" data-role="button" data-icon="info" data-inline="true">addCredit</a>
			<a href="#Credit" data-role="button" data-icon="info" data-inline="true">subCredit</a>
		
		</div>
	</div>
</body>
</html>


