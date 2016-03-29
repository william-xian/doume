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
	<div data-role="page" id="business-members" data-theme="a" data-title="business-members">
		<div data-role="header" data-position="fixed"
			class="segmented-control">
			<div data-role="controlgroup" data-type="horizontal">
				<a href="/doume/business/addMember" data-role="button" class="ui-control-inactive" data-icon="plus" data-iconpos="right">Memeber</a>
				<a href="/doume/business/sendMessage" data-role="button" class="ui-control-active" data-icon="info" data-iconpos="right">Send</a>
				<a href="/doume/business/subCredit" data-role="button" class="ui-control-inactive" data-icon="minus" data-iconpos="left">Credit</a>
				<a href="/doume/business/addCredit" data-role="button" class="ui-control-inactive" data-icon="plus" data-iconpos="right">Credit</a>
			</div>
		</div>
		<div data-role="content" data-theme="c">
	
			<ul data-role="listview" data-filter="true">
			<c:forEach var="item" items="${OM}">
				<li>
					<p>${item.userId}</p>
					<p>${item.name}</p>
					<p>Sex:${item.sex}</p>
					<a href="tel:${item.contact}">Contact:${item.contact}</a>
					<p>Birthday:${item.birthday}</p>
					<p>Address:${item.addr}</p>
				</li>
			</c:forEach>
			</ul>
		</div>
		
		<div data-role="footer" data-position="fixed" class="tabbar"
			data-id="main-tabbar">
			<div data-role="navbar" class="tabbar">
				<ul>
					<li><a href="/doume/business/getHome" data-icon="home">Home</a></li>
					<li><a href="/doume/business/getMembers" data-icon="star" class="ui-btn-active ui-state-persist">Member</a></li>
					<li><a href="/doume/business/getPrivate" data-icon="grid">Mine</a></li>
					<li><a href="/doume/business/getMore" data-icon="arrow-r" data-iconpos="left">More</a></li>
				</ul>
			</div>
		</div>
	</div>
</body>
</html>


