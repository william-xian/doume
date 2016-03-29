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
<link href="${resourceRoot}/css/style.css" rel="stylesheet"/>
</head>
<body>
	<!-- product -->
	<div data-role="page" id="product-update">
		<div data-role="header">
			<a href="/doume/admin//doume/admin/getProduct" data-role="button" data-icon="arrow-l">Back</a>
			<h3>Comment</h3>
		</div>
		<div data-role="content">
			<ul data-role="listview">
				<c:forEach var="cmm" items="${OM}">
					<li>
						<h1>${cmm.userId} -- ${cmm.cmmTime} 
							<a href="/doume/admin/removeComment/${cmm.cmmId}">Delete</a>
						</h1>
						<p>${cmm.content}</p>
					</li>
				</c:forEach>
			</ul>
		</div>
	</div>
</body>
</html>
