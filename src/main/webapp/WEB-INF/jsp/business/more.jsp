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
	<div data-role="page" id="business-more" data-theme="a" data-title="more">
		<div data-role="header" data-position="fixed">
			<h1>business-more</h1>
		</div>
		<div data-role="content" data-theme="c">
			<div data-role="collapsible-set">

				<div data-role="collapsible">
					<h1>About</h1>
					<p>Maidou Company</p>
				</div>
			</div>
		</div>
		<div data-role="footer" data-position="fixed" class="tabbar"
			data-id="main-tabbar">
			<div data-role="navbar" class="tabbar">
				<ul>
					<li><a href="/doume/business/getHome" data-icon="home">Home</a></li>
					<li><a href="/doume/business/getMembers" data-icon="star">Member</a></li>
					<li><a href="/doume/business/getPrivate" data-icon="grid">Mine</a></li>
					<li><a href="/doume/business/getMore" data-icon="arrow-r" data-iconpos="left" class="ui-btn-active ui-state-persist">More</a></li>
				</ul>
			</div>
		</div>
	</div>
</body>
</html>


