<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="../../cdb/css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="../../cdb/css/font-awesome.css" rel="stylesheet" media="screen">
<link href="../../cdb/css/main.css" rel="stylesheet" media="screen">
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
                                <input type="text" class="form-control" id="computerName" placeholder="Computer name" name="computerName">
                                <div class="${empty errors['computerName'] ? '':'alert alert-danger'}"><c:out value="${errors['computerName']}"></c:out></div>
                            </div>
                            <div class="form-group">
                                <label for="introduced">Introduced date</label>
                                <input type="date" class="form-control" id="introduced" placeholder="Introduced date" name="introduced">
                            </div>
                            <div class="form-group">
                                <label for="discontinued">Discontinued date</label>
                                <input type="date" class="form-control" id="discontinued" placeholder="Discontinued date" name="discontinued">
                            </div>
                            <div class="form-group">
                                <label for="companyId">Company</label>
                                <select class="form-control" id="companyId" name="companyId">
                                    <option value="0">--</option>
                                    <c:forEach var="company" items="${allCompanies}">
										<option value="${company.id}"><c:out value="${company.name}" /></option>
									</c:forEach>
                                </select>
                                <div class="${empty errors['companyId'] ? '':'alert alert-danger'}"><c:out value="${errors['companyId']}"></c:out></div>
                            </div>                  
                        </fieldset>
                        <div class="${empty errors['otherError'] ? '':'alert alert-danger'}"><c:out value="${errors['otherError']}"></c:out></div>
						<div class="${empty added ? '':'alert alert-success'}"><c:out value="${added}"></c:out></div>
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
</body>
</html>