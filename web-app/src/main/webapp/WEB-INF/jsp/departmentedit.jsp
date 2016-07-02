<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: kobri
  Date: 28.06.2016
  Time: 20:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Department edit</title>
</head>
<body>
<c:url var="saveUrl" value="/department/edit" />
<form:form modelAttribute="department" method="POST" action="${saveUrl}">
    <table>
        <tr>
            <td><form:label path="id">Id:</form:label></td>
            <td><form:input path="id" readonly="true"/></td>
        </tr>

        <tr>
            <td><form:label path="dep_name">Name:</form:label></td>
            <td><form:input path="dep_name"/></td>
        </tr>
    </table>
    <input type="submit" value="Save" />
</form:form>
</body>
</html>