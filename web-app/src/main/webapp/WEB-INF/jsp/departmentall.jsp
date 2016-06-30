<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: kobri
  Date: 28.06.2016
  Time: 15:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>all department</title>
</head>
<body>

<c:url var="addUrl" value="/department/add" />
<c:url var="all" value="/department/all" />

<c:choose>

    <c:when test="${empty departments}">
        There are currently no departments in the list.
        <br>
        <a href="<spring:url value='/department/add' ></spring:url>"
           title="department-add">Add department</a>

    </c:when>
    <c:when test="${not empty error}">
        <div class="message">${error}</div>
        <br>
        <a href="<spring:url value='/department/add' ></spring:url>"
           title="department-add">Add department</a>
    </c:when>
    <c:otherwise>
        All departments.

        <br><br>
        <table style="border: 1px solid; width: 500px; text-align:center">
            <tr>
                <td>№</td>
                <td>Name</td>
            </tr>
            <c:forEach items="${departments}" var="department">

                <c:url var="deleteUrl" value="/department/delete/${department.id}" />
                <c:url var="editUrl" value="/department/edit/${department.id}" />
                <c:url var="averageSalary" value="/department/salary/${department.id}" />
                <c:url var="empDep" value="/department/employees/${department.id}"/>
                <tr>
                    <td><c:out value="${department.id}" /></td>
                    <td><c:out value="${department.dep_name}" /></td>
                    <td><a href="${editUrl}">Edit</a> </td>
                    <td><a href="${deleteUrl}">Delete</a></td>
                    <td><a href="${averageSalary}">Average salary</a> </td>
                    <td><a href="${empDep}">Show employees</a> </td>
                </tr>
            </c:forEach>
        </table>
    </c:otherwise>
</c:choose>


<c:url var="mainUrl" value="/home" />
<p>Return to <a href="${mainUrl}">Home page</a></p>
</body>
</html>