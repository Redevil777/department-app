<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: kobri
  Date: 28.06.2016
  Time: 19:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Employee edit</title>
</head>
<body>
<c:url var="saveUrl" value="/employee/edit" />
<form:form modelAttribute="employee" method="POST" action="${saveUrl}">
    <table>
        <tr>
            <td><form:label path="id">Id:</form:label></td>
            <td><form:input path="id"/></td>
        </tr>
        <tr>
            <td><form:label path="fname">First name:</form:label></td>
            <td><form:input path="fname"/></td>
        </tr>
        <tr>
            <td><form:label path="lname">Last name:</form:label></td>
            <td><form:input path="lname"/></td>
        </tr>
        <tr>
            <td><form:label path="mname">Middle name:</form:label></td>
            <td><form:input path="mname" /></td>
        </tr>
        <tr>
            <td><form:label path="birthday">Birthday:</form:label></td></td>
            <td><form:input data-format="yyyy-MM-dd" type="text" path="birthday" value="${employee.birthday}"/></td>
        </tr>
        <tr>
            <td><form:label path="salary" >Salary:</form:label></td>
            <td><form:input path="salary"/></td>
        </tr>
        <tr>
            <td>Department:</td>
            <td>
                <form:select path="dep_id">
                    <c:forEach items="${departments}" var="department">
                        <c:choose>
                            <c:when test="${department.id==employee.dep_id}">
                                <option value="${department.id}" selected>
                                    <c:out value="${department.dep_name}" />
                                </option>
                            </c:when>
                            <c:otherwise>
                                <option value="${department.id}">
                                    <c:out value="${department.dep_name}" />
                                </option>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </form:select>
            </td>
        </tr>
    </table>
    <input type="submit" value="Save" />
</form:form>
</body>
</html>