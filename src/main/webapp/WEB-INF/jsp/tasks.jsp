<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<%@include file="head.jsp"%>
<body>
<div class="jumbotron text-center">
    <h1>ToDo TASKS</h1>
</div>
<div class="container">
    <%@include file="nav.jsp"%>
    <nav class="navbar navbar-default">
        <div class="container-fluid">
            <div class="navbar-header">
                <a class="navbar-brand" href="<c:url value="/start.jsp"/>">Home</a>
            </div>
            <ul class="nav navbar-nav ">
                <li
                        <c:if test="${sessionScope.location eq 'today'}">class="active"</c:if> ><a
                        href="<c:url value="/tasks/today"/>">Today</a></li>
                <li
                        <c:if test="${sessionScope.location eq 'tomorrow'}">class="active"</c:if> ><a
                        href="<c:url value="/tasks/tomorrow"/>">Tomorrow</a></li>
                <li
                        <c:if test="${sessionScope.location eq 'some'}">class="active"</c:if> ><a
                        href="<c:url value="/tasks/some"/>">Some</a></li>
                <li
                        <c:if test="${sessionScope.location eq 'fixed'}">class="active"</c:if> ><a
                        href="<c:url value="/tasks/fixed"/>">Fixed</a></li>
                <li
                        <c:if test="${sessionScope.location eq 'all'}">class="active"</c:if> ><a
                        href="<c:url value="/tasks/all"/>">All</a></li>
                <li
                        <c:if test="${sessionScope.location eq 'remove'}">class="active"</c:if> ><a
                        href="<c:url value="/tasks/remove"/>">Remove</a></li>
                <li><a href="<c:url value="/tasks/new"/>">New</a></li>
            </ul>
        </div>
    </nav>
    <form action="<c:url value="/tasks/action"/>" method="post">
        <div class="table-responsive">
            <table class="table">
                <thead>
                <tr>
                    <th>#</th>
                    <th></th>
                    <th>Create date</th>
                    <th>Execute date</th>
                    <th>Status</th>
                    <th>Description</th>
                    <th>File</th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${requestScope.tasks}" var="task" varStatus="iterator">
                    <tr>
                        <td><c:out value="${iterator.index + 1}"/></td>
                        <td><input name="task" value="${task.idTask}" type="checkbox"></td>
                        <td><c:out value="${task.creationDate}"/></td>
                        <td><c:out value="${task.executionDate}"/></td>
                        <td><c:out value="${task.status}"/></td>
                        <td><c:out value="${task.description}"/></td>
                        <td>
                            <div>
                                <c:choose>
                                    <c:when test="${task.fileName eq 'NO FILE'}">
                                        ${task.fileName}
                                    </c:when>
                                    <c:otherwise>
                                        <a href="${'/tasks/file/'.concat(task.idTask)}">${task.fileName}</a>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </td>
                        <td><a href="${'/tasks/edit/'.concat(task.idTask)}">
                            <button type="button" name="edit" value="edit" class="btn btn-xs btn-info">Edit</button>
                        </a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div>
            <c:if test="${ not (sessionScope.location eq 'fixed')}">
                <button type="submit" name="button" value="fix" class="btn btn-success">Fix</button>
            </c:if>
            <c:if test="${ not (sessionScope.location eq 'remove')}">
                <button type="submit" name="button" value="remove" class="btn btn-primary">Remove</button>
            </c:if>
            <c:if test="${sessionScope.location eq 'remove'}">
                <button type="submit" name="button" value="restore" class="btn btn-primary">Restore</button>
                <button type="submit" name="button" value="delete" class="btn btn-danger">Delete</button>
            </c:if>
        </div>
    </form>
</div>
<%@include file="errorMessage.jsp"%>
<%@include file="footer.jsp"%>
</body>
</html>
