<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: kobri
  Date: 15.05.2016
  Time: 13:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Employee all</title>
</head>
<body>
<c:url var="addUrl" value="/employee/add" />
<c:url var="mainUrl" value="/home" />
<c:url var="all" value="/employee/all" />
<c:if test="${not empty error}">
    <div class="error">${error}</div>
</c:if>


<c:choose>
    <c:when test="${empty employees}">
        There are currently no employees in the list.
        <br>
        <br>
        <a href="<spring:url value='/employee/add' ></spring:url>"
           title="employee-add">Add employee</a>
    </c:when>
    <c:when test="${not empty error}">
        <div class="message">${error}</div>
        <br>
        <br>
        <a href="<spring:url value='/web/department/add' ></spring:url>"
           title="department-add">Add employee</a>
    </c:when>
    <c:otherwise>
        <h3>All employees</h3>

        <table style="border: 1px solid; width: 500px; text-align:center" border="1">

            <tr>
                <td>№</td>
                <td>First name</td>
                <td>Last name</td>
                <td>Middle Name</td>
                <td>Birthday</td>
                <td>Salary</td>
                <td>Department</td>
                <td></td>
                <td></td>
            </tr>


            <c:forEach items="${employees}" var="employee">

                <c:url var="deleteUrl" value="/employee/delete/${employee.id}" />
                <c:url var="editUrl" value="/employee/edit/${employee.id}" />
                <tr>
                    <td><c:out value="${employee.id}" /></td>
                    <td><c:out value="${employee.fname}" /></td>
                    <td><c:out value="${employee.lname}" /></td>
                    <td><c:out value="${employee.mname}" /></td>
                    <td><c:out value="${employee.birthday}" /></td>
                    <td><c:out value="${employee.salary}" /></td>
                    <td>
                        <c:forEach items="${departments}" var="department">
                            <c:choose>
                                <c:when test="${department.id == employee.dep_id }">
                                    <c:out value="${department.dep_name}" />
                                </c:when>
                            </c:choose>
                        </c:forEach>
                    </td>
                    <td><a href="${editUrl}">Edit</a> </td>
                    <td><a href="${deleteUrl}">Delete</a></td>
                </tr>
            </c:forEach>
        </table>
        <br><br>
        <h4>Search employees between date of birthday</h4>
        <c:url var="search" value="/employee/between" />
        <form:form method="get" action="${search}">
            From <input type="date" name="from">
            To <input type="date" name="to">
            <input type="submit" value="search">
        </form:form> <br>

        <br>
        <h4>Search employees by date of birthday</h4>
        <c:url var="search2" value="/employee/date" />
        <form:form method="get" action="${search2}">
            Date <input type="date" name="date">
            <input type="submit" value="search">
        </form:form> <br>
    </c:otherwise>
</c:choose>





<p>Return to <a href="${mainUrl}">Home page</a></p>
</body>
</html>
