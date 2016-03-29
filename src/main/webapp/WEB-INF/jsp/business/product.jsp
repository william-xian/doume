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
	<!-- business-product -->
	<div data-role="page" id="business-product">
		<div data-role="header">
			<a href="#customer-home" data-role="button" data-rel="back" data-icon="back">Back</a>
			<h3>name</h3>
		</div>
		<div data-role="content">
			<form>
				<label for="productId" class="ui-hidden-accessible">ProductId:</label>
				<input type="text" name="productId" id="productId" class="ui-hidden-accessible" placeholder="Null stand for Create" />
				<label for="name">Product Name:</label>
				<input type="text" name="name" id="name" />
				<label for="sellerId">Seller Id:</label>
				<input type="number" name="sellerId" id="sellerId" />
				<label for="productType">ProductType:</label> 
				<select name="productType" id="productType" data-role="none">
					<option value="1" selected>Eat</option>
					<option value="2">Drink</option>
					<option value="4">Play</option>
					<option value="8">Dress</option>
					<option value="16">Live</option>
					<option value="32">Transport</option>
					<option value="64">Live&amp;Eat</option>
				</select>
				<label for="description">description:</label>
				<textarea rows="" cols="" name="description" id="description"></textarea>
				
				<label for="price">Price:</label>
				<input type="number" name="price" id="price" />
				<label for="creditPrice">CreditPrice:</label>
				<input type="number" name="creditPrice" id="creditPrice" />
				<label for="retCredit">Credit:</label>
				<input type="number" name="retCredit" id="retCredit" />
				<label for="file">Product Picture:</label>
				<img alt="" src="images/product.jpg"/>
				<input type="file" name="file" id="file">
				<a id="btn-register" href="#" data-role="button">Submit</a>
			</form>
			
			<div data-role="collapsible">
			<h1>Comment</h1>
			<ul data-role="listview">
				<li>
					<h1>Commenter|score|Time&nbsp;nbsp;&nbsp;nbsp;<a href="#">Reply</a></h1>
							<p>Content....</p>
						<p>Content....</p>
				</li>
			</ul>
			</div>
		</div>
		<div data-role="footer">
			<h3>footer</h3>
		</div>
	</div>
</body>
</html>


