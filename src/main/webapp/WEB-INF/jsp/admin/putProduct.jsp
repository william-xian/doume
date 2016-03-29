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
			<h3>Product</h3>
		</div>
		<div data-role="content">
			<form:form modelAttribute="OM" action="/doume/admin/putProduct" method="post" data-ajax="false" enctype="multipart/form-data">
				<label for="productId">ProductId:</label>
				<form:errors path="productId"></form:errors>
				<form:input path="productId" type="text" name="productId" id="productId" placeholder="Null stand for Create" />
				<label for="name">Product Name:</label>
				<form:errors path="name"></form:errors>
				<form:input path="name" type="text" name="name" id="name" />
				<label for="sellerId">SellerId:</label>
				<form:errors path="sellerId"></form:errors>
				<form:input path="sellerId" type="number" name="sellerId" id="sellerId" />
				<label for="productType">ProductType:</label>
				<form:errors path="productType"></form:errors>
				<form:select path="productType" name="productType" id="productType" data-role="none">
					<form:option value="1">Eat</form:option>
					<form:option value="2">Drink</form:option>
					<form:option value="4">Play</form:option>
					<form:option value="8">Dress</form:option>
					<form:option value="16">Live</form:option>
					<form:option value="32">Transport</form:option>
					<form:option value="64">Live Eat</form:option>
				</form:select>
				<label for="description">description:</label>
				<form:errors path="description"></form:errors>
				<form:textarea rows="" cols="" path="description" id="description"></form:textarea>
				<label for="price">Price:</label>
				<form:errors path="price"></form:errors>
				<form:input type="number" path="price" id="price" /> 
				<label for="creditPrice">CreditPrice:</label>
				<form:errors path="creditPrice"></form:errors>
				<form:input type="number" path="creditPrice" id="creditPrice" />
				<label for="retCredit">Credit:</label>
				<form:errors path="retCredit"></form:errors>
				<form:input type="number" path="retCredit" id="retCredit" />
				<img alt="" src="${resourceRoot}/images/${OM.mediaId}"/>
				<label for="media">Product Picture:</label>
				<input type="file" name="media" id="media"/> 
				<input data-role="button" type="submit" value="Submit"/>
			</form:form>
		</div>
		<a data-role="button" href="/doume/admin/getCommentByProductId/${OM.productId}">Comments</a>
	</div>
</body>
</html>
