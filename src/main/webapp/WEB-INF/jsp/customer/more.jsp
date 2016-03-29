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
	<div data-role="page" id="customer-more" data-theme="a" data-title="More">
		<div data-role="header" data-position="fixed">
			<h1>More</h1>
		</div>
		<div data-role="content" data-theme="c">
			<div data-role="collapsible-set">
				<div data-role="collapsible">
					<h1>About</h1>
					<p>Maidou Company</p>
				</div>
			</div>
		</div>
		<div data-role="footer" data-position="fixed" class="tabbar" data-id="main-tabbar">
			<div data-role="navbar" class="tabbar">
				<ul>
					<li><a href="/doume/customer/getHome" data-icon="home"></a></li>
					<li><a href="/doume/customer/search" data-icon="search"></a></li>
					<li><a href="/doume/customer/private" data-icon="star"></a></li>
					<li><a href="/doume/customer/more" data-icon="grid" class="ui-btn-active ui-state-persist"></a></li>
				</ul>
			</div>
		</div>
	</div>
</body>
</html>