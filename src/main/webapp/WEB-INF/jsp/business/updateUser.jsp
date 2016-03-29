<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form:form modelAttribute="OM" action="/doume/business/updateUser" method="POST">
		<label for="userName">UserName:</label>
		<form:input type="text" path="userName" id="userName" />
		<label for="password">Password:</label>
		<form:input type="password" path="password" id="password" /> 
		<label for="password2">Confirm:</label>
		<input type="password" name="password2" id="password2" />
		<input type="submit" data-role="button" data-icon="arrow-u" value="Update"/>
	</form:form>
</body>
</html>