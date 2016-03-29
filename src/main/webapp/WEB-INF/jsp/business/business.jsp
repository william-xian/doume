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
	<div data-role="page" id="business-home" data-theme="a"
		data-title="Home">
		<div data-role="header" data-position="fixed"
			class="segmented-control">
			<div data-role="controlgroup" data-type="horizontal">
				<a href="#login" data-role="button" data-icon="" class="ui-control-active">Login</a>
				<!-- 
				<a href="#business-register" data-role="button" class="ui-control-active">Register</a>
				 -->
				 <a href="#user-exit" data-role="button" class="ui-control-inactive">Exit</a>
			</div>
		</div>
		<div data-role="content" data-theme="c">
			<ul data-role="listview">
				<li data-role="list-divider">
					<a href="#orders?ordersId=1">ordersInfo</a>
					<a href="orders?ordersId=1&option=Confirm">Confirm</a>
					<a href="orders?ordersId=1&option=Refuse">Refuse</a>
					<span class="ui-li-count">$25</span>
				</li>
				<li data-role="list-divider">
					<a href="#orders?ordersId=1">ordersInfo</a>
					<a href="orders?ordersId=1&option=Confirm">Confirm</a>
					<a href="orders?ordersId=1&option=Refuse">Refuse</a>
					<span class="ui-li-count">$25</span>
				</li>
			</ul>
		</div>

		<div data-role="footer" data-position="fixed" class="tabbar"
			data-id="main-tabbar">
			<div data-role="navbar" class="tabbar">
				<ul>
					<li><a href="#business-home" data-icon="home" class="ui-btn-active ui-state-persist">Home</a></li>
					<li><a href="#business-members" data-icon="star">Member</a></li>
					<li><a href="#business-private" data-icon="grid">Mine</a></li>
					<li><a href="#business-more" data-icon="arrow-r" data-iconpos="left">business-more</a></li>
				</ul>
			</div>
		</div>
	</div>

	<!-- -->
	<div data-role="page" id="business-members" data-theme="a" data-title="business-members">
		<div data-role="header" data-position="fixed"
			class="segmented-control">
			<div data-role="controlgroup" data-type="horizontal">
				<a href="#addMember" data-role="button" class="ui-control-inactive" data-icon="plus" data-iconpos="right">Memeber</a>
				<a href="#sendMessage" data-role="button" class="ui-control-active" data-icon="info" data-iconpos="right">Send</a>
				<a href="#subCredit" data-role="button" class="ui-control-inactive" data-icon="minus" data-iconpos="left">Credit</a>
				<a href="#addCredit" data-role="button" class="ui-control-inactive" data-icon="plus" data-iconpos="right">Credit</a>
			</div>
		</div>
		<div data-role="content" data-theme="c">
	
			<ul data-role="listview" data-filter="true">
				<li>
					<a href="#business-customer">memberName|phone</a>
				</li>
				<li>
					<a href="#business-customer">memberName|phone</a>
				</li>
			</ul>
		</div>
		
		<div data-role="footer" data-position="fixed" class="tabbar"
			data-id="main-tabbar">
			<div data-role="navbar" class="tabbar">
				<ul>
					<li><a href="#business-home" data-icon="home">Home</a></li>
					<li><a href="#business-members" data-icon="star" class="ui-btn-active ui-state-persist">Member</a></li>
					<li><a href="#business-private" data-icon="grid">Mine</a></li>
					<li><a href="#business-more" data-icon="arrow-r" data-iconpos="left">business-more</a></li>
				</ul>
			</div>
		</div>
	</div>
	<!--  -->
	<div data-role="dialog" id="addMember">
		<div data-role="header"><h1>Add Member</h1></div>
		<div data-role="content">
			<form action="">
				<label for="userName">UserName:</label>
				<input type="text" name="userName" id="userName">
				<input type="submit" value="Add">
			</form>
		</div>
	</div>
	
	<!--  -->
	<div data-role="dialog" id="addCredit">
		<div data-role="header"><h1>Add Credit</h1></div>
		<div data-role="content">
			<form action="">
				<label for="business-members">business-members:</label>
				<select name="business-members" id="business-members" data-native-menu="false" multiple="multiple">
					<option value="0" selected>All</option>
					<option value="2">Drink</option>
					<option value="4">Play</option>
				</select>
				<label for="credit">CreditValue:</label>
				<input type="number" min="0" name="credit" id="credit">
				<input type="submit" value="Submit">
			</form>
		</div>
	</div>
		
	<!--  -->
	<div data-role="dialog" id="subCredit">
		<div data-role="header"><h1>Sub Credit</h1></div>
		<div data-role="content">
			<form action="">
				<label for="business-members">business-members:</label>
				<select name="business-members" id="business-members" data-native-menu="false" multiple="multiple">
					<option value="0" selected>All</option>
					<option value="2">Drink</option>
					<option value="4">Play</option>
				</select>
				<label for="credit">CreditValue:</label>
				<input type="number" min="0" name="credit" id="credit">
				<input type="submit" value="Submit">
			</form>
		</div>
	</div>
	
	<!-- -->
	<div data-role="page" id="business-private" data-title="Private">
		<div data-role="header">
			<h1>Private</h1>
		</div>
		<div data-role="content">
			<div data-role="collapsible-set">
				<div data-role="collapsible">
					<h1>Private Info</h1>
					<div data-role="collapsible">
						<h1>User Info</h1>
						<form>
							<label for="userName">UserName:</label>
							<input type="text" name="userName" id="userName" />
							<label for="password">Password:</label>
							<input type="password" name="password" id="password" /> 
							<label for="password2">Confirm:</label>
							<input type="password" name="password2" id="password2" />
							<input type="submit" data-role="button" data-icon="arrow-u" value="Update"/>
						</form>
					</div>
					<div data-role="collapsible">
						<h1>Business Info</h1>
						<form>
						<div data-role="fieldcontain">
							<label for="bName">ShopName:</label>
							<input type="text" name="bName" id="bName" />
							<label for="phoneno">tel:</label>
							<input type="tel" name="phoneno" id="phoneno" /> 
							<label for="info">info:</label>
							<textarea rows="" cols="" name="info" id="info"></textarea>
							<div data-role="collapsible">
								<h1>HomePage</h1>
								<input type="file" id="bhomeImg" name="file">
								<img src="images/business.jpg">
							</div>
							<div data-role="collapsible">
								<h1>Location</h1>
								<label for="lng">longitude:</label>
								<input type="number" id="lng" name="lng">
								<label for="lat">latitude:</label>
								<input type="number" id="lat" name="lat">
								<button id="getGeoLocation">AutoGet</button>
								<input type="file" id="location.mediaId" name="location.mediaId">
								<img src="images/location.jpg">
							</div>
							<input type="submit" value="Update">
						</div>
						</form>
					</div>
				</div>
				<div data-role="collapsible">
					<h1>Message</h1>
					<ul data-role="listview" data-filter="true">
						<li>
							<h1>Time:
							<a href="#">Sender</a>
							<a href="#">Return</a>
							</h1>
							<h2>subject:</h2>
							<h3>Content:How are you?</h3>
						</li>
					</ul>
				</div>
				
				<div data-role="collapsible">
					<h1>Product</h1>
					<ul data-role="listview">
						<li>
							<a href="#business-product">
								<img src="images/product.jpg"/>
								<h1>ProductName|SellCount|avgScroe</h1>
								<p>infomation</p>
							</a>
						</li>
					</ul>
				</div>
				
				<div data-role="collapsible">
					<h1>Deal</h1>
					<label for="months">Months:</label>
					<input id="months" name="months" type="date">
					<ol data-role="listview" data-filter="true">
						<li>
							<h1>Time|seller|buyer|dealMsg|value</h1>
						</li>
						<li>
							<h1>Time|seller|buyer|dealMsg|value</h1>
						</li>
					</ol>
					<a href="#" data-role="button" data-icon="arrow-d">Download</a>
				</div>
				
				<div data-role="collapsible">
					<h1>Suggestion</h1>
					<ul data-role="listview">
						<li>
							<h1>productId|Time
							<a href="#">Delete</a>
							<a href="#">Reply</a>
							</h1>
							<p>Content....</p>
						</li>
					</ul>
				</div>
				
				
			</div>
		</div>
		<div data-role="footer" data-position="fixed" class="tabbar"
			data-id="main-tabbar">
			<div data-role="navbar" class="tabbar">
				<ul>
					<li><a href="#business-home" data-icon="home">Home</a></li>
					<li><a href="#business-members" data-icon="star">Member</a></li>
					<li><a href="#business-private" data-icon="grid" class="ui-btn-active ui-state-persist">Mine</a></li>
					<li><a href="#business-more" data-icon="arrow-r" data-iconpos="left">business-more</a></li>
				</ul>
			</div>
		</div>
	</div>
	
	
	<!-- business-product -->
	<div data-role="page" id="business-product">
		<div data-role="header">
			<a href="#customer-home" data-role="button" data-rel="back" data-icon="back">Back</a>
			<h3>name</h3>
		</div>
		<div data-role="content">
			<form>
				<label for="productId" class="ui-hidden-accessible">ProductId:</label>
				<input type="text" name="productId" id="productId" class="ui-hidden-accessible" placeholder="Null stand for Create" />
				<label for="name">Product Name:</label>
				<input type="text" name="name" id="name" />
				<label for="sellerId">Seller Id:</label>
				<input type="number" name="sellerId" id="sellerId" />
				<label for="productType">ProductType:</label> 
				<select name="productType" id="productType" data-role="none">
					<option value="1" selected>Eat</option>
					<option value="2">Drink</option>
					<option value="4">Play</option>
					<option value="8">Dress</option>
					<option value="16">Live</option>
					<option value="32">Transport</option>
					<option value="64">Live&amp;Eat</option>
				</select>
				<label for="description">description:</label>
				<textarea rows="" cols="" name="description" id="description"></textarea>
				
				<label for="price">Price:</label>
				<input type="number" name="price" id="price" />
				<label for="creditPrice">CreditPrice:</label>
				<input type="number" name="creditPrice" id="creditPrice" />
				<label for="retCredit">Credit:</label>
				<input type="number" name="retCredit" id="retCredit" />
				<label for="file">Product Picture:</label>
				<img alt="" src="images/product.jpg"/>
				<input type="file" name="file" id="file">
				<a id="btn-register" href="#" data-role="button">Submit</a>
			</form>
			
			<div data-role="collapsible">
			<h1>Comment</h1>
			<ul data-role="listview">
				<li>
					<h1>Commenter|score|Time&nbsp;nbsp;&nbsp;nbsp;<a href="#">Reply</a></h1>
							<p>Content....</p>
						<p>Content....</p>
				</li>
			</ul>
			</div>
		</div>
		<div data-role="footer">
			<h3>footer</h3>
		</div>
	</div>

	<!-- -->
	<div data-role="page" id="business-more" data-theme="a" data-title="business-more">
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
					<li><a href="#business-home" data-icon="home">Home</a></li>
					<li><a href="#business-members" data-icon="star">Member</a></li>
					<li><a href="#business-private" data-icon="grid">Mine</a></li>
					<li><a href="#business-more" data-icon="arrow-r" data-iconpos="left" class="ui-btn-active ui-state-persist">business-more</a></li>
				</ul>
			</div>
		</div>
	</div>
	
	<!-- -->
	<div data-role="page" id="orders" data-theme="a" data-title="Orders">
		<div data-role="header" data-position="fixed">
			<h3>OrdersId|Name</h3>
		</div>
		<div data-role="content" data-theme="c">
			<ul data-role="listview">
				<li>
					<a href="#product?productId=1">
						<img src="images/product.jpg"/>
						<h1>name</h1>
						<h2>Price | Creidt</h2>
						<h2>infomation</h2>
					</a>
				</li>
				
				<li>
					<a href="#product?productId=1">
						<img src="images/product.jpg">
						<h1>name</h1>
						<h2>Price | Creidt</h2>
						<h2>infomation</h2>
					</a>
				</li>
			</ul>
		</div>
		<div data-role="footer" data-position="fixed" class="tabbar"
			data-id="main-tabbar">
			<div data-role="navbar" class="tabbar">
				<ul>
					<li><a href="#business-home" data-icon="home" class="ui-btn-active ui-state-persist">Home</a></li>
					<li><a href="#business-members" data-icon="star">Member</a></li>
					<li><a href="#business-private" data-icon="grid" >Mine</a></li>
					<li><a href="#business-more" data-icon="arrow-r" data-iconpos="left">business-more</a></li>
				</ul>
			</div>
		</div>
	</div>
	
	<!--  -->
	<div data-role="page" id="business-customer">
		<div data-role="header">
			<a href="#business-customer" data-role="button" data-icon="back" data-rel="back">Back</a>
			<h3>SendMessage</h3>
		</div>
		<div data-role="content">
			<fieldset>
				<legend>Member Info</legend>
				<h3>UserName:Tome</h3>
				<h3>Phone:123123123</h3>
				<h3>Birthday:123123123</h3>
			</fieldset>
			<a href="#sendMssage" data-role="button" data-icon="info" data-inline="true">SendMessage</a>
			<a href="#Credit" data-role="button" data-icon="info" data-inline="true">addCredit</a>
			<a href="#Credit" data-role="button" data-icon="info" data-inline="true">subCredit</a>
		
		</div>
	</div>
	
	<!--  -->
	<div data-role="dialog" id="sendMessage">
		<div data-role="header">
			<h3>SendMessage</h3>
		</div>
		<div data-role="content">
			<form>
				<label for="receiverIds" >Receovers:</label>
				<select name="receiverIds" id="receiverIds" data-native-menu="false" multiple="multiple">
					<option value="2">Drink</option>
					<option value="4">Play</option>
				</select>
				
				<label for="senderId" class="ui-hidden-accessible">Seller Id:</label>
				<input type="number" name="senderId" id="senderId" class="ui-hidden-accessible"/>
				<label for="content">Content:</label>
				<textarea rows="" cols="" name="content" id="content"></textarea>
				<input type="submit" value="Send">
			</form>
		</div>
	</div>
</body>
</html>


