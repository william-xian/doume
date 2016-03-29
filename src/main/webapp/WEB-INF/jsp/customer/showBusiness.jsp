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
<body>	<!--  -->
	<div data-role="page" id="customer-business">
		<div data-role="header">
			<a href="/doume/customer/getHome" data-role="button" data-rel="back" data-icon="back">Back</a>
			<h2>Business Name</h2>
			<a href="/doume/customer/getTempOrders" data-role="button" data-icon="star">Menu</a>
		</div>
		<div data-role="content">
			<div data-role="none">
				<h2>${OM[0].bName}</h2>
				<h2>${OM[0].information}</h2>
				<h2>${OM[0].addr}</h2>
				<h2>${OM[0].phoneno}</h2>
				<img src="${resourceRoot}/images/${OM[0].home.mediaId}"/>
			</div>
			<div data-role="collapsible">
				<h2>Product</h2>
				<ul data-role="listview">
					<c:forEach var="g" items="${OM[1]}">
					<li>
						<a href="/doume/customer/getProduct/${g.productId}">
						<img src="${resourceRoot}/images/${g.mediaId}">
						<h3>${g.name} | ${g.selledCount} | ${g.score}/${g.scoreCount}</h3>
						<p>Price:${g.price}&nbsp;&nbsp;&nbsp;&nbsp; Credit: ${g.retCredit}/${g.creditPrice}
						</p>
						<p>${g.description}</p>
						</a>
						<a href="/doume/customer/changeOrdersItem/add/${g.productId}/1" data-icon="plus">Add</a>
					</li>
					</c:forEach>
				</ul>
			</div>
		</div>
	</div>
</body>
</html>
