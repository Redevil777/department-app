<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: kobri
  Date: 28.06.2016
  Time: 12:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Employee add</title>
</head>
<body>

<h2>Add employee</h2>
<c:url var="addUrl" value="/employee/add" />


<c:choose>
    <c:when test="${not empty error}">
        <div class="message">${error}</div>
        <br>
        There are currently no departments in the list.
        <br>
        <a href="<spring:url value='/department/add' ></spring:url>"
           title="department-add">Add department</a>
    </c:when>
    <c:otherwise>
        <form:form modelAttribute="employees" method="post" action="${addUrl}">

            <table>
                <tr>
                    <td><form:label path="fname" >First name:</form:label></td>
                    <td><form:input path="fname"/></td>
                </tr>
                <tr>
                    <td><form:label path="lname">Last name:</form:label></td>
                    <td><form:input path="lname"/></td>
                </tr>
                <tr>
                    <td><form:label path="mname">Middle name:</form:label></td>
                    <td><form:input path="mname"/></td>
                </tr>
                <tr>
                    <td><form:label path="birthday">Birthday:</form:label></td>
                    <td><input data-format="yyyy-MM-dd" type="date" name="birthday"></td>
                </tr>
                <tr>
                    <td><form:label path="salary">Salary:</form:label></td>
                    <td><form:input path="salary"/></td>
                </tr>
                <tr>
                    <td>Department:</td>
                    <td>
                        <form:select path="dep_id">
                            <c:forEach items="${departments}" var="department">
                                <option value="${department.id}">
                                    <c:out value="${department.dep_name}" />
                                </option>
                            </c:forEach>
                        </form:select>
                    </td>
                </tr>
            </table>

            <input type="submit" value="Add">
        </form:form>
    </c:otherwise>
</c:choose>


<br>
<c:url var="mainUrl" value="/home" />

<p>Return to <a href="${mainUrl}">Home page</a> </p>
</body>
</html>
