<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title><spring:message code="text.title" /></title>
</head>
<body>
   	<h1>Login</h1>
   	<form:form name='login' action="login" method='POST'>
	   	<div class="form-group">
	   		<form:label path="username">User:</form:label>
	    	<form:input type='text' path='username' />
	    	<div class="<c:if test="${not empty errors['username']}">alert alert-danger</c:if>">${errors['username']}</div>
	   	</div>
	   	<div class="form-group">
	   		<form:label path="password">Password:</form:label>
	    	<form:input type='password' path='password' />
	    	<div class="<c:if test="${not empty errors['password']}">alert alert-danger</c:if>">${errors['password']}</div>
	   	</div>
		<input name="submit" type="submit" value="submit" />
	    <spring:message code="${errors['otherError']}" var="otherErrorError"/>
  	</form:form>
<body>

</body>
</html>