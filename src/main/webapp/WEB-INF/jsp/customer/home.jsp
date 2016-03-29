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
	<div data-role="page" id="customer-home" data-theme="a"
		data-title="Home">
		<div data-role="header" data-position="fixed"
			class="segmented-control">
			<div data-role="controlgroup" data-type="horizontal">
				<a href="/doume/customer/getLogin" data-role="button" class="ui-control-active">Login</a>
				<a href="/doume/customer/logout" data-role="button" class="ui-control-inactive">Logout</a>
			</div>
		</div>
		<div data-role="content" data-theme="c">
			<h4>New Message</h4>
			<h4>Product</h4>
			<div class="ui-grid-c" style="text-align: center;">
				<div class="ui-block-a">
					<a href="/doume/customer/searchProduct/0x000001/0/0" data-role="button" width="50">Meat</a>
				</div>
				<div class="ui-block-b">
					<a href="/doume/customer/searchProduct/0x000002/0/0" data-role="button" width="50">Vegetables</a>
				</div>
				<div class="ui-block-c">
					<a href="/doume/customer/searchProduct/0x000004/0/0" data-role="button" width="50">Fruit</a>
				</div>
				<div class="ui-block-d">
					<a href="/doume/customer/searchProduct/0x000008/0/0" data-role="button" width="50">Fun</a>
				</div>
				<div class="ui-block-a">
					<a href="/doume/customer/searchProduct/0x000010/0/0" data-role="button" width="50">Live</a>
				</div>
				<div class="ui-block-b">
					<a href="/doume/customer/searchProduct/0x000011/0/0" data-role="button" width="50">Beauty</a>
				</div>
				<div class="ui-block-c">
					<a href="/doume/customer/searchProduct/0x000012/0/0" data-role="button" width="50">Meat</a>
				</div>
				<div class="ui-block-d">
					<a href="/doume/customer/searchProduct/0x000014/0/0" data-role="button" width="50">Meat</a>
				</div>
			</div>

		</div>
		<div data-role="footer" data-position="fixed" class="tabbar"
			data-id="main-tabbar">
			<div data-role="navbar" class="tabbar">
				<ul>
					<li><a href="/doume/customer/getHome" data-icon="home"
						class="ui-btn-active ui-state-persist"></a></li>
					<li><a href="/doume/customer/search" data-icon="search"></a></li>
					<li><a href="/doume/customer/private" data-icon="star"></a></li>
					<li><a href="/doume/customer/more" data-icon="grid"></a></li>
				</ul>
			</div>
		</div>
	</div>
</body>
</html>