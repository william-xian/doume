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
	<div data-role="page" id="customer-product">
		<div data-role="header">
			<a href="/doume/customer/getHome" data-role="button" data-rel="back" data-icon="back">Back</a>
			<h1>name</h1>
			<a href="#customer-menu" data-role="button" data-icon="star">Menu</a>
		</div>
		<div data-role="content">
			<div data-role="fieldcontain">	
				<h1>${OM[0].name}</h1>
				<img src="${resourceRoot}/images/${OM[0].mediaId}">
				<h2>$${OM[0].price} | ${OM[0].retCredit}/${OM[0].creditPrice}</h2>
				<a href="/doume/customer/changeOrdersItem/add/${OM[0].productId}/1" data-role="button" data-inline="true">Add2Menu</a>
				<p>${OM[0].description}</p>
			</div>
			<div data-role="collapsible">
			<h1>Comment</h1>
			<ul data-role="listview">
				<c:forEach var="cmm" items="${OM[1]}">
				<li>
					<h1>${cmm.subject} -- ${cmm.cmmTime} - score:${cmm.score}</h1>
					<p>${cmm.content}</p>
				</li>
				</c:forEach>
			</ul>
			</div>
		</div>
		<div data-role="footer">
			<h3>footer</h3>
		</div>
	</div>
	<div data-role="page" id="customer-menu">
		<div data-role="header">
			
			<a href="/doume/customer/getHome" data-role="button" data-rel="back" data-icon="back">Back</a>
			<h1>Menu</h1>
			<a href="#customer-delete" data-role="button" data-icon="delete">Delete</a>
		</div>
		<div data-role="content">
			<ol>
				<li>
					<div data-role="controlgroup" data-type="horizontal">
						<a href="#customer-product">Name</a>&nbsp;&nbsp;Count:<count>10</count>&nbsp;&nbsp;$:<count>10</count>
					</div>
					<div data-role="ui-grid-d">
						<div class="ui-block-a">
							<select data-role="slider">
								<option value="0">Credit</option>
								<option value="1" selected>Cash</option>
							</select>
						</div>
						<div class="ui-block-b">
							<button data-icon="plus" data-iconpos="notext"></button>
						</div>
						<div class="ui-block-c">
							<button data-icon="minus" data-iconpos="notext"></button>
						</div>
						<div class="ui-block-e">
							<button data-icon="delete" data-iconpos="notext"></button>
						</div>
					</div>
				</li>
			</ol>
		</div>
		<div data-role="footer">
			<h1>
			<a href="#addOrders" data-role="button">Submit</a>
			</h1>
		</div>
	</div>
	
</body>
</html>
