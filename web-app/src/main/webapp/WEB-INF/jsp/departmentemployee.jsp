<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: kobri
  Date: 28.06.2016
  Time: 23:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Employee from select department</title>
</head>
<body>

<h2>Employee from select department</h2>
<br>
<br>
<table style="border: 1px solid; width: 500px; text-align:center" border="1">

    <tr>
        <td>№</td>
        <td>First name</td>
        <td>Last name</td>
        <td>Middle Name</td>
        <td>Birthday</td>
        <td>Salary</td>
    </tr>


    <c:forEach items="${employees}" var="employee">

        <tr>
            <td><c:out value="${employee.id}" /></td>
            <td><c:out value="${employee.fname}" /></td>
            <td><c:out value="${employee.lname}" /></td>
            <td><c:out value="${employee.mname}" /></td>
            <td><c:out value="${employee.birthday}" /></td>
            <td><c:out value="${employee.salary}" /></td>
        </tr>
    </c:forEach>
</table>
<br>
<c:url var="back" value="/department/all"/>

<c:url var="mainUrl" value="/home" />
<p><a href="${back}">Back</a> </p>

<p>Return to <a href="${mainUrl}">Home page</a></p>
</body>
</html>