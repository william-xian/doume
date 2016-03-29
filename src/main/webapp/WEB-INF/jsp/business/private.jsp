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
	<div data-role="page" id="business-private" data-title="Private">
		<div data-role="header">
			<h1>Private</h1>
		</div>
		<div data-role="content">
			<div data-role="collapsible-set">
				<div data-role="collapsible">
					<h1>MyInfo</h1>
					<a href="/doume/business/getUser" data-role="button">UpdateUser</a>
					<a href="/doume/business/getBusiness" data-role="button">UpdateBusiness</a>
				</div>
				<div data-role="collapsible">
					<h1>Message</h1>
					<ul data-role="listview" data-filter="true">
					<c:forEach  var="msg" items="${OM[0]}">
						<li>
							<h1>${msg.head}
							<a href="/doume/business/getCustomer/${msg.receiverId}">Sender</a>
							<a href="/doume/business/removeMessage/${msg.msgId}">Delete</a>
							</h1>
							<h2>subject:${msg.subject}</h2>
							<h2>Content:${msg.content}</h2>
						</li>
					</c:forEach>
					</ul>
				</div>
				
				<div data-role="collapsible">
					<h1>Product</h1>
					<ul data-role="listview">
						<c:forEach  var="product" items="${OM[1]}">
						<li>
							<a href="/doume/business/getProduct/${product.productId}">
								<img src="${resourceRoot}/images/${product.mediaId}"/>
								<h1>${product.name} | ${product.selledCount}| ${product.score}</h1>
								<p>${product.description}</p>
							</a>
						</li>
						</c:forEach>
					</ul>
				</div>
				
				<div data-role="collapsible">
					<h1>Deal</h1>
					<label for="months">Months:</label>
					<input id="months" name="months" type="date">
					<ol data-role="listview" data-filter="true">
						<c:forEach  var="deal" items="${OM[2]}">
						<li>
							<h1>${deal.dealTime}|${deal.sellerId}| ${deal.buyerId} | ${deal.dealMsg} | ${deal.productId}</h1>
						</li>
						</c:forEach>
					</ol>
					<a href="#" data-role="button" data-icon="arrow-d">Download</a>
				</div>
				
				<div data-role="collapsible">
					<h1>Suggestion</h1>
					<ul data-role="listview">
						<c:forEach  var="sug" items="${OM[3]}">
						<li>
							<h1>${sug.targetId} | ${sug.cmmTime} | ${sug.score}
							<a href="/doume/business/removeSuggestion/${sug.cmmId}">Delete</a>
							<a href="/doume/business/getSendMessage/${sug.userId}">Reply</a></h1>
							<p>${sug.content}</p>
						</li>
						</c:forEach>
					</ul>
				</div>
			</div>
		</div>
		<div data-role="footer" data-position="fixed" class="tabbar"
			data-id="main-tabbar">
			<div data-role="navbar" class="tabbar">
				<ul>
					<li><a href="/doume/business/getHome" data-icon="home">Home</a></li>
					<li><a href="/doume/business/getMembers" data-icon="star">Member</a></li>
					<li><a href="/doume/business/getPrivate" data-icon="grid" class="ui-btn-active ui-state-persist">Mine</a></li>
					<li><a href="/doume/business/getMore" data-icon="arrow-r" data-iconpos="left">More</a></li>
				</ul>
			</div>
		</div>
	</div>
</body>
</html>


