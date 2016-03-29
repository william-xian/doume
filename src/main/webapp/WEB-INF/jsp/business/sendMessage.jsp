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
<script src="${resourceRoot}/js/jquery.mobile.min.js" type="text/javascript"></script>
<script src="${resourceRoot}/js/admin.js" type="text/javascript"></script>
<link href="${resourceRoot}/css/style.css" rel="stylesheet"/>
</head>
<body>
	<!--  -->
	<div data-role="page" id="sendMessage">
		<div data-role="header">
		<a href="/doume/business/getMembers" data-role="button" data-icon="arrow-l">Back</a>
			<h3>SendMessage</h3>
		</div>
		<div data-role="content">
			<form action="/doume/business/sendMessage">
				<label for="receivers">Receivers:</label>
				<input type="text" name="receivers" id="receivers"/>
				<label for="subject">Subject:</label>
				<input type="text" name="subject" id="subject"/>
				<label for="content">Content:</label>
				<textarea rows="" cols="" name="content" id="content"></textarea>
				<input type="submit" value="Send">
			</form>
		</div>
	</div>
</body>
</html>
