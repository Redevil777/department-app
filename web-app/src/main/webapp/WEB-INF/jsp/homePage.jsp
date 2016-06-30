<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: kobri
  Date: 14.05.2016
  Time: 17:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Home page</title>
</head>
<body>
<h2>Home page</h2>

<br>
<li><a href="<spring:url value='/department/add' ></spring:url>"
       title="department-add">Add department</a></li><br>

<li><a href="<spring:url value='/department/all' ></spring:url>"
       title="department-all">Show all departments</a></li><br>
<li><a href="<spring:url value='/employee/add' ></spring:url>"
       title="employee-add">Add employee</a></li><br>
<li><a href="<spring:url value='/employee/all' ></spring:url>"
       title="employee-all">Show all employees</a></li>

</body>
</html>
