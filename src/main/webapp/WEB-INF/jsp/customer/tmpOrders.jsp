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

	<!-- -->
	<div data-role="page" data-theme="a"
		data-title="Home">
		<div data-role="header" data-position="fixed"
			class="segmented-control">
			<div data-role="controlgroup" data-type="horizontal">
				<a href="/doume/customer/getBusiness/${OM.sellerId}" data-role="button" data-rel="back" data-icon="left" class="ui-control-active">Back</a>
				<a href="/doume/customer/changeOrdersItem/del/0/0" data-role="button" data-role="delete" class="ui-control-active">Remove</a>
			</div>
		</div>
		<div data-role="content" data-theme="c">
			<h1><a href="/doume/customer/submitTempOrders">Submit</a></h1>
			<ul data-role="listview">
			<c:forEach var="item" items="${OM.items}">
				<li data-role="list-divider">
					<p><a href="/doume/customer/getProduct/${item.product.productId}">${item.product.name}</a> - ${item.isUsingCredit} - ${item.count}</p>
					<p><a href="/doume/customer/changeOrdersItem/reset/${item.product.productId}/${item.count}">Update</a>
					</p>
				</li>
			</c:forEach>
			</ul>
		</div>
	</div>
</body>
</html>