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
                    <div class="label label-default pull-right">
                        id: ${computer.id}
                    </div>
                    <h1>Edit Computer</h1>

                    <form action="editComputer" method="POST">
                        <input type="hidden" value="${computer.id}" id="id" name="id"/>
                        <fieldset>
                            <div class="form-group">
                                <label for="computerName">Computer name</label>
                                <input type="text" class="form-control" id="computerName" name="computerName" placeholder="Computer name" value="${computer.name}">
								<c:if test="${not empty errors['computerName']}"><div class="alert alert-danger"><c:out value="${errors['computerName']}"></c:out></div></c:if>
                            </div>
                            <div class="form-group">
                                <label for="introduced">Introduced date</label>
                                <input type="date" class="form-control" id="introduced" name="introduced" placeholder="Introduced date" value="${computer.introduced}">
                            	<c:if test="${not empty errors['introduced']}"><div class="alert alert-danger"><c:out value="${errors['introduced']}"></c:out></div></c:if>
                            </div>
                            <div class="form-group">
                                <label for="discontinued">Discontinued date</label>
                                <input type="date" class="form-control" id="discontinued" name="discontinued" placeholder="Discontinued date" value="${computer.discontinued}">
                                <c:if test="${not empty errors['discontinued']}"><div class="alert alert-danger"><c:out value="${errors['discontinued']}"></c:out></div></c:if>
                            </div>
                            <div class="form-group">
                                <label for="companyId">Company</label>
                                <select class="form-control" id="companyId" name="companyId">
                                    <option value="0">--</option>
                                    <c:forEach var="company" items="${allCompanies}">
                                    	<option value="${company.id}" <c:if test="${not empty computer.companyId && computer.companyId == company.id}">selected="selected"</c:if>><c:out value="${company.name}"/></option>
									</c:forEach>
                                </select>
                                <c:if test="${not empty errors['companyId']}"><div class="alert alert-danger"><c:out value="${errors['otherError']}"></c:out></div></c:if>
                            </div>            
                        </fieldset>
                        <c:if test="${not empty errors['otherError']}"> <div class="alert alert-danger"><c:out value="${errors['otherError']}"></c:out></div></c:if>
                        <div class="<c:if test="${not empty success}">alert alert-success</c:if>"><c:out value="${success}"></c:out></div>
                        <div class="actions pull-right">
                            <input type="submit" value="Edit" class="btn btn-primary">
                            or
                            <a href="dashboard" class="btn btn-default">Cancel</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>
</body>
</html>