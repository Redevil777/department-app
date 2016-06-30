<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: kobri
  Date: 28.06.2016
  Time: 22:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Department add</title>
</head>
<body>
<h2>Add department</h2><br>

<c:url var="addUrl" value="/department/add" />

<c:choose>
    <c:when test="${not empty error}">
        <div class="message">${error}</div>
        <br>
        <a href="<spring:url value='/department/add' ></spring:url>"
           title="department-add">Add department</a>
    </c:when>
    <c:otherwise>
        <form:form modelAttribute="department" method="post" action="${addUrl}">

            <table>
                <tr>
                    <td><form:label path="dep_name">Department name:</form:label></td>
                    <td><form:input path="dep_name"/></td>
                </tr>
            </table>

            <input type="submit" value="Add">
        </form:form>
    </c:otherwise>
</c:choose>

<br>
<c:url var="mainUrl" value="/home" />
<p>Return to <a href="${mainUrl}">Home page</a></p>
</body>
</html>
