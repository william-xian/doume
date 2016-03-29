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
	<!-- -->
	<div data-role="page" data-theme="a" data-title="Product">
		<div data-role="header">
			<a href="/doume/customer/private" data-ref="back" data-role="button" data-icon="left">Back</a>
			<h3>Product</h3>
		</div>
		<div data-role="content" data-theme="c">
			<h2>Product</h2>
			<ul data-role="listview">
				<c:forEach var="g" items="${OM}">
					<li><a href="/doume/customer/getProduct/${g.productId}"> <img
							src="${resourceRoot}/images/${g.mediaId}">
							<h3>${g.name}| ${g.selledCount} | ${g.score}/${g.scoreCount}</h3>
							<p>Price:${g.price}&nbsp;&nbsp;&nbsp;&nbsp; Credit:
								${g.retCredit}/${g.creditPrice}</p>
							<p>${g.description}</p>
					</a> <a href="/doume/customer/changeOrdersItem/add/${g.productId}/1"
						data-icon="plus">Add</a></li>
				</c:forEach>
			</ul>
		</div>
	</div>
</body>
</html>
