<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
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
        	<div class="${empty errors['otherError'] ? '':'alert alert-danger'}"><c:out value="${errors['otherError']}"></c:out></div>
            <h1 id="homeTitle">
                <c:out value="${computerNumber}"></c:out> Computers found
            </h1>
            <div id="actions" class="form-horizontal">
                <div class="pull-left">
                    <form id="searchForm" action="#" method="GET" class="form-inline">

                        <input type="search" id="searchbox" name="search" class="form-control" placeholder="Search name" />
                        <input type="submit" id="searchsubmit" value="Filter by name"
                        class="btn btn-primary" />
                    </form>
                </div>
                <div class="pull-right">
                    <a class="btn btn-success" id="addComputer" href="addComputer">Add Computer</a> 
                    <a class="btn btn-default" id="editComputer" href="#" onclick="$.fn.toggleEditMode();">Edit</a>
                </div>
            </div>
        </div>

        <form id="deleteForm" action="#" method="POST">
            <input type="hidden" name="selection" value="">
        </form>

        <div class="container" style="margin-top: 10px;">
            <table class="table table-striped table-bordered">
                <thead>
                    <tr>
                        <!-- Variable declarations for passing labels as parameters -->
                        <!-- Table header for Computer Name -->

                        <th class="editMode" style="width: 60px; height: 22px;">
                            <input type="checkbox" id="selectall" /> 
                            <span style="vertical-align: top;">
                                 -  <a href="#" id="deleteSelected" onclick="$.fn.deleteSelected();">
                                        <i class="fa fa-trash-o fa-lg"></i>
                                    </a>
                            </span>
                        </th>
                        <th>
                            Computer name
                        </th>
                        <th>
                            Introduced date
                        </th>
                        <!-- Table header for Discontinued Date -->
                        <th>
                            Discontinued date
                        </th>
                        <!-- Table header for Company -->
                        <th>
                            Company
                        </th>

                    </tr>
                </thead>
                <!-- Browse attribute computers -->
                <tbody id="results">
                	<c:forEach items="${computerList}" var="computer">
	                    <tr>
	                        <td class="editMode">
	                            <input type="checkbox" name="cb" class="cb" value="0">
	                        </td>
	                        <td>
	                            <a href="editComputer" onclick="">${computer.name}</a>
	                        </td>
	                        <td>${computer.introduced}</td>
	                        <td>${computer.discontinued}</td>
	                        <td>${computer.companyName}</td>
	                    </tr>
	                </c:forEach>
                </tbody>
            </table>
        </div>
    </section>

    <footer class="navbar-fixed-bottom">
        <div class="container text-center">
            <ul class="pagination">
               <li>
                    <a href="
                      	<c:url value="/dashboard">
                      		<c:param name="page" value="${currentPage > 1 ? currentPage - 1 : currentPage}"/>
                      	</c:url>"
			  		  	aria-label="Previous">
                    	<span aria-hidden="true">&laquo;</span>
                  	</a>
	              </li>
	              <c:forEach var="i" begin="${pageIndexBegin}" end="${pageIndexEnd}" step="1">
		              <li>
		              	<a href="
		              		<c:url value="/dashboard">
					  			<c:param name="page" value="${i}"/>
					  		</c:url>">
					  		${i}</a>
					  </li>
				  </c:forEach>
	              <li>
	                <a href="
	                	<c:url value="/dashboard">
                      		<c:param name="page" value="${currentPage < maxPage ? currentPage + 1 : currentPage}"/>
                      	</c:url>"
			  		  	aria-label="Next">
	                  <span aria-hidden="true">&raquo;</span>
	                </a>
	            </li>
	        </ul>

	        <div class="btn-group btn-group-sm pull-right" role="group" >
	         	<form method="GET">
		        	<button type="submit" class="btn btn-default" name="nbElemByPage" value="10">10</button>
		            <button type="submit" class="btn btn-default" name="nbElemByPage" value="50">50</button>
		            <button type="submit" class="btn btn-default" name="nbElemByPage" value="100">100</button>
	            </form>
	        </div>
        </div>

    </footer>
<script src="../../csb/js/jquery.min.js"></script>
<script src="../../csb/js/bootstrap.min.js"></script>
<script src="../../csb/js/dashboard.js"></script>

</body>
</html>