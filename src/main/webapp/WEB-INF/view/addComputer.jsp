<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="./static/css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="./static/css/font-awesome.css" rel="stylesheet" media="screen">
<link href="./static/css/main.css" rel="stylesheet" media="screen">
</head>
<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="dashboard"> Application - Computer Database </a>
        </div>
    </header>

    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <h1>Add Computer</h1>
                    <form action="addComputer" method="POST">
                        <fieldset>
                            <div class="form-group">
                                <label for="computerName">Computer name</label>
                                <input type="text" class="form-control" id="computerName" placeholder="Computer name" name="computerName" value="${computer.name}">
                                <div class="<c:if test="${not empty errors['computerName']}">alert alert-danger</c:if>"><c:out value="${errors['computerName']}"></c:out></div>
                            </div>
                            <div class="form-group">
                                <label for="introduced">Introduced date</label>
                                <input type="date" class="form-control" id="introduced" placeholder="Introduced date" name="introduced" value="${computer.introduced}">
                                <div class="<c:if test="${not empty errors['introduced']}">alert alert-danger</c:if>"><c:out value="${errors['introduced']}"></c:out></div>
                            </div>
                            <div class="form-group">
                                <label for="discontinued">Discontinued date</label>
                                <input type="date" class="form-control" id="discontinued" placeholder="Discontinued date" name="discontinued" value="${computer.discontinued}">
                                <div class="<c:if test="${not empty errors['discontinued']}">alert alert-danger</c:if>"><c:out value="${errors['discontinued']}"></c:out></div>
                            </div>
                            <div class="form-group">
                                <label for="companyId">Company</label>
                                <select class="form-control" id="companyId" name="companyId">
                                    <option value="0">--</option>
                                    <c:forEach var="company" items="${allCompanies}">
										<option value="${company.id}" <c:if test="${computer.companyId == company.id}">selected="selected</c:if>><c:out value="${company.name}"/></option>
									</c:forEach>
                                </select>
                                <div class="<c:if test="${not empty errors['companyId']}">alert alert-danger</c:if>"><c:out value="${errors['companyId']}"></c:out></div>
                            </div>                  
                        </fieldset>
                        <div class="<c:if test="${not empty errors['otherError']}">alert alert-danger</c:if>"><c:out value="${errors['otherError']}"></c:out></div>
						<div class="<c:if test="${not empty added}">alert alert-success</c:if>"><c:out value="${added}"></c:out></div>
                        <div class="actions pull-right">
                            <input type="submit" value="Add" class="btn btn-primary">
                            or
                            <a href="dashboard" class="btn btn-default">Cancel</a>
                        </div>
                        
                    </form>
                </div>
            </div>
        </div>
    </section>
    
<script src="./static/js/modifyComputer.js"></script>
</body>
</html>