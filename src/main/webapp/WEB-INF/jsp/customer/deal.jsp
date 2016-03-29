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
	<h1>Deal</h1>
	<label for="months">Months:</label>
	<input id="months" name="months" type="date">
	<div>
		<ol data-role="listview" data-filter="true">
		<c:forEach var="deal" items="OM">
			<li>
				<h1>Time|seller|buyer|dealMsg|value</h1>
			</li>
		</c:forEach>
		</ol>
	</div>
</body>
</html>
