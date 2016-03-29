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
	<div data-role="page" id="news-upload">
		<div data-role="header">
			<a href="/doume/admin//doume/admin/getNews" data-role="button" data-icon="arrow-l">Back</a>
			<h3>News</h3>
		</div>
		<div data-role="content">
			<form:form modelAttribute="OM" action="/doume/admin/putNews" method="POST" data-ajax="false" enctype="multipart/form-data">
				<label for="newsType">NewsType:</label>
				<form:select path="newsType" id="newsType" data-native-menu="false" multiple="multiple">
					<form:option value="1">All</form:option>
					<form:option value="2">Drink</form:option>
					<form:option value="4">Play</form:option>
				</form:select>
				<label for="title">Title:</label>
				<form:input type="text" path="title" id="title" />
				<label for="newsDate">Date:</label>
				<form:input type="date" path="newsDate" id="newsDate" />
				<label for="content">Content:</label>
				<form:textarea rows="" cols="" path="content" id="content"></form:textarea>
				<label for="file">Picture:</label>
				<form:input type="file" path="file" id="file" />
				<img alt="" src="${resourcesRoot}/images/${OM.mediaId}"/>
				<input type="submit" value="Send"/>
			</form:form>
		</div>
	</div>
</body>
</html>
