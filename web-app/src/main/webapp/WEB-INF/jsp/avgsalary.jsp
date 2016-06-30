<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: kobri
  Date: 29.06.2016
  Time: 0:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Average salary</title>
</head>
<body>

<c:if test="${avg==0}">
    There are currently no employees in the list.
</c:if>

<form:form items="${avg}" var="avg">
    Average salary :  <td><c:out value="${avg}" /></td>
</form:form>


<c:url var="mainUrl" value="/home" />
<p>Return to <a href="${mainUrl}">Home page</a></p>
</body>
</html>
