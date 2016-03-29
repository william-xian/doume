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
	<div data-role="page" id="customer-private" data-title="Private">
		<div data-role="header">
			<h1>Private</h1>
		</div>
		<div data-role="content">
			<div data-role="collapsible-set">
				<div data-role="collapsible">
					<h1>Info</h1>
					<a href="/doume/customer/getUser" data-role="button">UpdateUser</a>
					<a href="/doume/customer/getCustomer" data-role="button">UpdateCustomer</a>
				</div>
				<div data-role="collapsible">
					<h1>Orders</h1>
					<ul data-role="listview">
						<c:forEach var="item" items="${OM[0]}">
						<li data-role="list-divider">
							<a href="/doume/customer/getOrders/${item.ordersId}">${item.ordersInfo}</a> 
							<a href="/doume/customer/confirmOrders/${item.ordersId}">Confirm</a> 
							<a href="/doume/customer/refuseOrders/${item.ordersId}">Refuse</a>
							<span class="ui-li-count">$${item.totalMoney} | ${item.totalCredit}</span>
						</li>
						</c:forEach>
					</ul>
				</div>

				<div data-role="collapsible">
					<h1>Message</h1>
					<ul data-role="listview" data-filter="true">
						<c:forEach var="msg" items="${OM[1]}">
						<li>
							<h1>${msg.head} - ${msg.msgDate}
							<a href="/doume/customer/removeMessage/${msg.msgId}">Remove</a>
							<a href="/doume/customer/sendMessage/${msg.senderId}">Reply</a>
							</h1>
							<h2>subject:${msg.subject}</h2>
							<h3>Content:${msg.content}</h3>
						</li>
						</c:forEach>
					</ul>
				</div>
				
				<div data-role="collapsible">
					<h1>Favorites</h1>
					<ul data-role="listview" data-filter="true">
						<c:forEach var="fav" items="${OM[2]}">
						<li>
							<a href="/doume/customer/getEnshrine/${fav.oid}">
								<img src="${fav.mediaId}"/>
								<h1>${fav.tarId}-title</h1>
								<p>${fav.keyWord}</p>
							</a>
						</li>
						</c:forEach>
					</ul>
				</div>
				<div data-role="collapsible">
					<h1>Comment</h1>
					<ul data-role="listview">
						<c:forEach var="cmm" items="${OM[3]}">
						<li>
							<h1>${cmm.subject}:<a href="/doume/customer/getComment/${cmm.cmmId}">Detail</a>
							</h1>
						</li>
						</c:forEach>
					</ul>
				</div>
				<a href='/doume/customer/getDeals' data-role="button">Deals</a>
			</div>
		</div>
		
		<div data-role="footer" data-position="fixed" class="tabbar"
			data-id="main-tabbar">
			<div data-role="navbar" class="tabbar">
				<ul>
					<li><a href="/doume/customer/getHome" data-icon="home"></a></li>
					<li><a href="/doume/customer/search" data-icon="search"></a></li>
					<li><a href="/doume/customer/private" data-icon="star" class="ui-btn-active ui-state-persist"></a></li>
					<li><a href="/doume/customer/more" data-icon="grid"></a></li>
				</ul>
			</div>
		</div>
	</div>
</body>
</html>