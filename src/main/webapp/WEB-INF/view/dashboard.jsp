<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
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
        	<div class="<c:if test="${not empty errors['otherError']}">alert alert-danger</c:if>"><c:out value="${errors['otherError']}"></c:out></div>
            <h1 id="homeTitle">
                <c:out value="${page.nbElementTotal}"></c:out> Computers found
            </h1>
            <div id="actions" class="form-horizontal">
                <div class="pull-left">
                    <form id="searchForm" action="#" method="GET" class="form-inline">
	         			<input type="hidden" name="nbElementByPage" value="${page.nbElementByPage}"/>
                        <input type="search" id="searchbox" name="search" class="form-control" placeholder="Search name" value="${search}"/>
                        <input type="submit" id="searchsubmit" value="Filter by name" class="btn btn-primary" />
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
                       		<a href="
                        	 	<c:url value="/dashboard">
	                				<c:param name="column" value="COMPUTER_NAME"/>
	                				<c:param name="currentPage" value="${page.currentPage}"/>
	                				<c:param name="nbElementByPage" value="${page.nbElementByPage}"/>
	                				<c:param name="order" value="${page.reversedOrder}"/>
	                				<c:param name="search" value="${search}"/>
	                			</c:url>
                        	">Computer name
                        	<c:choose>
         						<c:when test = "${page.order == 'ASC' && page.column == 'COMPUTER_NAME'}">
									&#x25BC
						        </c:when>
						        <c:when test = "${page.order == 'DESC' && page.column == 'COMPUTER_NAME'}">
									&#x25B2
						        </c:when>
						    </c:choose>
                        	</a>
                        </th>
                        <th>
                        	<a href="
                        		<c:url value="/dashboard">
	                				<c:param name="column" value="INTRODUCED"/>
	                				<c:param name="currentPage" value="${page.currentPage}"/>
	                				<c:param name="nbElementByPage" value="${page.nbElementByPage}"/>
	                				<c:param name="order" value="${page.reversedOrder}"/>
	                				<c:param name="search" value="${search}"/>
	                			</c:url>
                        	 ">Introduced date
                        	 <c:choose>
         						<c:when test = "${page.order == 'ASC' && page.column == 'INTRODUCED'}">
									&#x25BC
						        </c:when>
						        <c:when test = "${page.order == 'DESC' && page.column == 'INTRODUCED'}">
									&#x25B2
						        </c:when>
						    </c:choose>
                        	</a>
                        </th>
                        <!-- Table header for Discontinued Date -->
                        <th>
                        	<a href="
                        	 	<c:url value="/dashboard">
	                				<c:param name="column" value="DISCONTINUED"/>
	                				<c:param name="currentPage" value="${page.currentPage}"/>
	                				<c:param name="nbElementByPage" value="${page.nbElementByPage}"/>
	                				<c:param name="order" value="${page.reversedOrder}"/>
	                				<c:param name="search" value="${search}"/>
	                			</c:url>
                        	 ">Discontinued date
                        	 <c:choose>
         						<c:when test = "${page.order == 'ASC' && page.column == 'DISCONTINUED'}">
									&#x25BC
						        </c:when>
						        <c:when test = "${page.order == 'DESC' && page.column == 'DISCONTINUED'}">
									&#x25B2
						        </c:when>
						    </c:choose>
						    </a>
                        </th>
                        <!-- Table header for Company -->
                        <th>
                        	<a href="
                        	 	<c:url value="/dashboard">
	                				<c:param name="column" value="COMPANY_NAME"/>
	                				<c:param name="currentPage" value="${page.currentPage}"/>
	                				<c:param name="nbElementByPage" value="${page.nbElementByPage}"/>
	                				<c:param name="order" value="${page.reversedOrder}"/>
	                				<c:param name="search" value="${search}"/>
	                			</c:url>
                        	 ">Company
                        	 <c:choose>
         						<c:when test = "${page.order == 'ASC' && page.column == 'COMPANY_NAME'}">
									&#x25BC
						        </c:when>
						        <c:when test = "${page.order == 'DESC' && page.column == 'COMPANY_NAME'}">
									&#x25B2
						        </c:when>
						    </c:choose>
						    </a>
                        </th>

                    </tr>
                </thead>
                <!-- Browse attribute computers -->
                <tbody id="results">
                	<c:forEach items="${computerList}" var="computer">
	                    <tr>
	                        <td class="editMode">
	                            <input type="checkbox" name="cb" class="cb" value="${computer.id}">
	                        </td>
	                        <td>
	                            <a href="<c:url value="editComputer"><c:param name="id" value="${computer.id}"/></c:url>" onclick="">${computer.name}</a>
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
                   		<c:if test="${page.currentPage > 1}">
                      		<c:url value="/dashboard">
                				<c:param name="order" value="${page.order}"/>
                     			<c:param name="column" value="${page.column}"/>
                     			<c:param name="nbElementByPage" value="${page.nbElementByPage}"/>
                      			<c:param name="currentPage" value="${page.currentPage - 1}"/>
                      			<c:param name="search" value="${search}"/>
                      		</c:url>
                      	</c:if>"
			  		  	aria-label="Previous">
                    	<span aria-hidden="true">&laquo;</span>
                  	</a>
	              </li>
	              <c:forEach var="i" begin="${page.beginSlider}" end="${page.endSlider}" step="1">
		              <li class="<c:if test="${i == page.currentPage}">active</c:if>">
		              	<a href="
		              		<c:url value="/dashboard">
					  			<c:param name="column" value="${page.column}"/>
					  			<c:param name="currentPage" value="${i}"/>
					  			<c:param name="nbElementByPage" value="${page.nbElementByPage}"/>
					  			<c:param name="search" value="${search}"/>
                				<c:param name="order" value="${page.order}"/>
					  		</c:url>">
					  	${i}</a>
					  </li>
				  </c:forEach>
	              <li>
	                	<a href="
	                		<c:if test="${page.currentPage < page.endSlider}">
	                			<c:url value="/dashboard">
									<c:param name="column" value="${page.column}"/>
									<c:param name="nbElementByPage" value="${page.nbElementByPage}"/>
	                				<c:param name="currentPage" value="${page.currentPage + 1}"/>
	                				<c:param name="search" value="${search}"/>
                					<c:param name="order" value="${page.order}"/>
	                			</c:url>
	                		</c:if>
	                	" aria-label="Next">
	                  <span aria-hidden="true">&raquo;</span>
	                </a>
	            </li>
	        </ul>
			<c:out value=""></c:out>

	        <div class="btn-group btn-group-sm pull-right" role="group" >
	         	<form method="GET">
	         		<input type="hidden" name="column" value="${page.column}"/>
	         		<input type="hidden" name="currentPage"  value="${page.currentPage}" />
	         		<input type="hidden" name="search"  value="${search}"/>
                	<input type="hidden" name="order" value="${page.order}"/>
		        	<button type="submit" class="btn btn-default <c:if test="${page.nbElementByPage == 10}">active</c:if>" name="nbElementByPage" value="10">10</button>
		            <button type="submit" class="btn btn-default <c:if test="${page.nbElementByPage == 50}">active</c:if>" name="nbElementByPage" value="50">50</button>
		            <button type="submit" class="btn btn-default <c:if test="${page.nbElementByPage == 100}">active</c:if>" name="nbElementByPage" value="100">100</button>
	            </form>
	        </div>
        </div>

    </footer>
<script src="./static/js/jquery.min.js"></script>
<script src="./static/js/bootstrap.min.js"></script>
<script src="./static/js/dashboard.js"></script>

</body>
</html>