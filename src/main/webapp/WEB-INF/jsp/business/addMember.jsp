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
	<div data-role="dialog" id="addMember">
		<div data-role="header"><h1>Add Member</h1></div>
		<div data-role="content">
			<form action="/doume/business/addMember" method="post">
				<label for="userName">UserName:</label>
				<input type="text" name="userName" id="userName">
				<input type="submit" value="Add">
			</form>
		</div>
	</div>
</body>
</html>


