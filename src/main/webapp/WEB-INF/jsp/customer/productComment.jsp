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
	<div data-role="page" data-theme="a" data-title="Product">
		<div data-role="header">
			<a href="/doume/customer/private" data-ref="back" data-role="button" data-icon="left">Back</a>
			<h3>Product</h3>
		</div>ma
		<div data-role="content" data-theme="c">
			<div>
				<h3>${OM[0].name}</h3>
				<h3>${OM[0].description}</h3>
				<img alt="" src="${resourceRoot}/images/${OM[0].mediaId}"/>
			</div>
			<div>
				<form:form attributeModel="OM" action="/doume/customer/putComment" method="POST">
					<form:input path="cmmId" value="${OM[1].cmmId}"/>
					<form:input path="targetId" value="${OM[1].targetId}"/>
					<form:input path="cmmTime" value="${OM[1].cmmTime}"/>
					<label>Comment</label>
					<form:input path="score" type="range" min="1" max = "5" value="3"/>
					<form:textarea path="content"/>
				</form:form>
			</div>
			<ul data-role="listview">
				<li>
					<h1>${OM[1].userId} -- ${OM[1].cmmTime}
					<a href="/doume/admin/removeComment/${OM[1].cmmId}">Delete</a>
					</h1>
					<p>${OM[1].content}</p>
				</li>
			</ul>

		</div>
	</div>
</body>
</html>
