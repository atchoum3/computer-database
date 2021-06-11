<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="${pageContext.request.contextPath}/resources/css/font-awesome.css" rel="stylesheet" media="screen">
<link href="${pageContext.request.contextPath}/resources/css/main.css" rel="stylesheet" media="screen">
</head>
<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/computer/list"> Application - Computer Database </a>
        </div>
    </header>
    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <div class="label label-default pull-right">
                        id: ${computer.id}
                    </div>
                    <h1>Edit Computer</h1>

                    <form:form modelAttribute="computer" method="POST">
                        <input type="hidden" value="${computer.id}" id="id" name="id"/>
                        <fieldset>
                            <div class="form-group">
                                <form:label path="name">Computer name</form:label>
                                <form:input type="text" class="form-control" id="computerName" path="name" placeholder="Computer name"/>
								<c:if test="${not empty errors['computerName']}"><div class="alert alert-danger"><c:out value="${errors['computerName']}"></c:out></div></c:if>
                            </div>
                            <div class="form-group">
                                <form:label path="introduced">Introduced date</form:label>
                                <form:input type="date" class="form-control" id="introduced" path="introduced" placeholder="Introduced date"/>
                            	<c:if test="${not empty errors['introduced']}"><div class="alert alert-danger"><c:out value="${errors['introduced']}"></c:out></div></c:if>
                            </div>
                            <div class="form-group">
                                <form:label path="discontinued">Discontinued date</form:label>
                                <form:input type="date" class="form-control" id="discontinued" path="discontinued" placeholder="Discontinued date" />
                                <c:if test="${not empty errors['discontinued']}"><div class="alert alert-danger"><c:out value="${errors['discontinued']}"></c:out></div></c:if>
                            </div>
                            <div class="form-group">
                                <label for="companyId">Company</label>
                                <form:select class="form-control" id="companyId" path="companyId">
                                    <form:option value="0">--</form:option>
                                    <form:options items="${allCompanies}" itemLabel="name" itemValue="id" />
                                </form:select>
                                <c:if test="${not empty errors['companyId']}"><div class="alert alert-danger"><c:out value="${errors['otherError']}"></c:out></div></c:if>
                            </div>
                        </fieldset>
                        <c:if test="${not empty errors['otherError']}"> <div class="alert alert-danger"><c:out value="${errors['otherError']}"></c:out></div></c:if>
                        <div class="<c:if test="${not empty success}">alert alert-success</c:if>"><c:out value="${success}"></c:out></div>
                        <div class="actions pull-right">
                            <input type="submit" value="Edit" class="btn btn-primary">
                            or
                            <a href="${pageContext.request.contextPath}/computer/list" class="btn btn-default">Cancel</a>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>
    </section>
</body>
</html>