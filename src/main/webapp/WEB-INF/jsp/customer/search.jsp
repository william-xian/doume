<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<title>Administrator</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta content="width=device-width,initial-scale=1" name="viewport">
<link href="${resourceRoot}/css/jquery.mobile.min.css" rel="stylesheet" type="text/css"/>
<script src="${resourceRoot}/js/jquery.min.js" type="text/javascript"></script>
<script src="${resourceRoot}/js/customer.js" type="text/javascript"></script>
<script src="${resourceRoot}/js/jquery.mobile.min.js" type="text/javascript"></script>
<link href="${resourceRoot}/css/style.css" rel="stylesheet"/>
</head>
<body>
	<div data-role="page" id="search" data-title="Search">
		<div data-role="header" class="segmented-control">
			<form action="/doume/customer/search" method="get">
			<input type="search" id="search" name="key" placeholder="Search" />
			<fieldset data-role="controlgroup" data-type="horizontal">
				<select name="btype" id="business-type"
					data-native-menu="false" data-theme="a">
					<option value="65535">全部</option>
					<option value="1">Eating</option>
					<option value="8">Dress</option>
					<option value="2">Entertainment</option>
					<option value="4">Use</option>
				</select>
				<select name="orderby" id="orderby"
					data-native-menu="false" data-theme="a">
					<option value="65535">Order By</option>
					<option value="1">Count Of Sell</option>
					<option value="8">AvgScore</option>
					<option value="2">Price</option>
				</select>
			</fieldset>
			</form>
		</div>
		<div data-role="content">
			<ul data-role="listview">
				<c:forEach var="bus" items="${OM}">
				<li><a href="/doume/customer/getBusiness/${bus.userId}"> 
					<img src="${resourceRoot}/images/${bus.home.mediaId}"/>
					<h1>${bus.bName}</h1>
					<p>${bus.information}</p></a>
				</li>
				</c:forEach>
			</ul>
		</div>

		<div data-role="footer" data-position="fixed" class="tabbar"
			data-id="main-tabbar">
			<div data-role="navbar" class="tabbar">
				<ul>
					<li><a href="/doume/customer/getHome" data-icon="home"></a></li>
					<li><a href="/doume/customer/search" data-icon="search"
						class="ui-btn-active ui-state-persist"></a></li>
					<li><a href="/doume/customer/private" data-icon="star"></a></li>
					<li><a href="/doume/customer/more" data-icon="grid"></a></li>
				</ul>
			</div>
		</div>
	</div>
</body>
</html>