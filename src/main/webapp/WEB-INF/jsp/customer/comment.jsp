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
	<!-- -->
	<div data-role="dialog" data-theme="a" data-title="Comment">
		<div data-role="content" data-theme="c">
			<div>
				<form:form modelAttribute="OM" action="/doume/customer/putComment" method="POST">
					<form:input path="cmmId" class="ui-hidden-accessible"/>
					<form:input path="targetId"/>
					<label>Time:</label>
					<form:input path="cmmTime"/>
					<label>Score:</label>
					<form:input path="score" type="range" min="1" max = "5"/>
					<label>Comment</label>
					<form:textarea path="content"/>
					<input type="submit" value="submit"/>
				</form:form>
			</div>
		</div>
	</div>
</body>
</html>
