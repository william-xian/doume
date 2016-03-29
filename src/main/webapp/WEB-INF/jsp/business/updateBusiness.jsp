<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form:form modelAttribute="OM" action="/doume/business/updateBusiness"
		method="post" data-ajax="false" enctype="multipart/form-data">
		<div data-role="fieldcontain">
			<label for="bName">ShopName:</label>
			<form:input type="text" path="bName" id="bName" />
			<label for="phoneno">tel:</label>
			<form:input type="tel" path="phoneno" id="phoneno" />
			<label for="information">info:</label>
			<form:textarea rows="" cols="" path="information" id="information"></form:textarea>
			<div data-role="collapsible">
				<h1>HomePage</h1>
				<input type="file" id="bhomeImg" name="home.media"/>
				<img src="${resourceRoot}/images/${OM.home.mediaId}">
			</div>
			<div data-role="collapsible">
				<h1>Location</h1>
				<label for="lng">longitude:</label>
				<form:input type="number" id="lng" path="location.lng"/>
				<label for="lat">latitude:</label>
				<form:input type="number" id="lat" path="location.lat"/>
				<button id="getGeoLocation">AutoGet</button>
			</div>
			<input type="submit" value="Update">
		</div>
	</form:form>
</body>
</html>