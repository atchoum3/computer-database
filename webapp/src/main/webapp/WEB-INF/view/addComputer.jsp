<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
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
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <h1><spring:message code="text.title.addComputer" /></h1>
                    <form:form modelAttribute="computer" method="POST">
                        <fieldset>
                            <div class="form-group">
                            	<spring:message code="text.computerName" var="computerNamePlaceholder"/>
                                <form:label path="name"><spring:message code="text.computerName" /></form:label>
                                <form:input type="text" class="form-control" id="name" placeholder="${computerNamePlaceholder}" path="name" value="${computer.name}" />
                                <spring:message code="${errors['computerName']}" var="computerNameError"/>
                                <div id="errorComputerName" class="<c:if test="${not empty errors['computerName']}">alert alert-danger</c:if>">${computerNameError}</div>
                            </div>
                            <div class="form-group">
                            	<spring:message code="text.computerIntroduced" var="computerIntroducedPlaceholder"/>
                                <form:label path="introduced"><spring:message code="text.computerIntroduced" /></form:label>
                                <form:input type="date" class="form-control" id="introduced" placeholder="${computerIntroducedPlaceholder}" path="introduced" value="${computer.introduced}" />
                                <spring:message code="${errors['introduced']}" var="introducedError"/>
                                <div id="errorIntroduced" class="<c:if test="${not empty errors['introduced']}">alert alert-danger</c:if>">${introducedError}</div>
                            </div>
                            <div class="form-group">
                            	<spring:message code="text.computerDiscontinued" var="computerDiscontinuedPlaceholder"/>
                                <form:label path="discontinued"><spring:message code="text.computerDiscontinued" /></form:label>
                                <form:input type="date" class="form-control" id="discontinued" placeholder="${computerDiscontinuedPlaceholder}" path="discontinued" value="${computer.discontinued}"/>
                                <spring:message code="${errors['discontinued']}" var="discontinuedError"/>
                                <div id="errorDiscontinued" class="<c:if test="${not empty errors['discontinued']}">alert alert-danger</c:if>">${discontinuedError}</div>
                            </div>
                            <div class="form-group">
                                <label for="companyId"><spring:message code="text.company" /></label>
                                <form:select class="form-control" id="companyId" path="companyId">
                                    <form:option value="0">--</form:option>
									<form:options items="${allCompanies}" itemLabel="name" itemValue="id" />
                                </form:select>
                                <spring:message code="${errors['companyId']}" var="companyIdError"/>
                                <div id="errorCompany" class="<c:if test="${not empty errors['companyId']}">alert alert-danger</c:if>">${companyIdError}</div>
                            </div>
                        </fieldset>
                        <spring:message code="${errors['otherError']}" var="otherErrorError"/>
                        <c:if test="${not empty errors['otherError']}"> <div class="alert alert-danger">${otherErrorError}</div></c:if>
                        <spring:message code="${success}" var="successMessage"/>
						<div class="<c:if test="${not empty success}">alert alert-success</c:if>">${successMessage}</div>
                        <div class="actions pull-right">
                            <input type="submit" value="<spring:message code="text.addComputer" />" class="btn btn-primary">
                            <spring:message code="text.addComputer" />
                            <a href="${pageContext.request.contextPath}/computer/list" class="btn btn-default"><spring:message code="text.cancel" /></a>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>
    </section>
<script src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/modifyComputer.js"></script>
</body>
</html>