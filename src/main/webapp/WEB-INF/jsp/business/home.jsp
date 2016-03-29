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

	<!-- -->
	<div data-role="page" data-theme="a"
		data-title="Home">
		<div data-role="header" data-position="fixed"
			class="segmented-control">
			<div data-role="controlgroup" data-type="horizontal">
				<a href="/doume/business/getLogin" data-role="button" data-icon="" class="ui-control-active">Login</a>
				<a href="#business-register" data-role="button" class="ui-control-active">Register</a>
				<a href="/doume/business/logout" data-role="button" class="ui-control-inactive">Exit</a>
			</div>
		</div>
		<div data-role="content" data-theme="c">
			<ul data-role="listview">
			<c:forEach var="item" items="${OM}">
				<li data-role="list-divider">
					<a href="/doume/business/getOrders/${item.ordersId}">More</a>
					<a href="/doume/business/confirmOrders/${item.ordersId}">Confirm</a>
					<a href="/doume/business/refuseOrders/${item.ordersId}">Refuse</a>
					<span class="ui-li-count">${item.totalCredit}</span>
					<p>${item.ordersInfo}</p>
				</li>
			</c:forEach>
			</ul>
		</div>

		<div data-role="footer" data-position="fixed" class="tabbar"
			data-id="main-tabbar">
			<div data-role="navbar" class="tabbar">
				<ul>
					<li><a href="/doume/business/getHome" data-icon="home" class="ui-btn-active ui-state-persist">Home</a></li>
					<li><a href="/doume/business/getMembers" data-icon="star">Member</a></li>
					<li><a href="/doume/business/getPrivate" data-icon="grid">Mine</a></li>
					<li><a href="/doume/business/getMore" data-icon="arrow-r" data-iconpos="left">More</a></li>
				</ul>
			</div>
		</div>
	</div>
</body>
</html>


