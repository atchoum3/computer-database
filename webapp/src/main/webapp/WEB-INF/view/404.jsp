<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<!DOCTYPE html>
<html>
<head>
    <title><spring:message code="text.title" /></title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- Bootstrap -->
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet" media="screen">
    <link href="${pageContext.request.contextPath}/resources/css/font-awesome.css" rel="stylesheet" media="screen">
    <link href="${pageContext.request.contextPath}/resources/css/main.css" rel="stylesheet" media="screen">
</head>
<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/computer/list"><spring:message code="text.navBar" /></a>
        </div>
    </header>

    <section id="main">
        <div class="container">
            <div class="alert alert-danger">
            	<spring:message code="text.error404" />
            </div>
        </div>
    </section>

    <script src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/dashboard.js"></script>

</body>
</html>