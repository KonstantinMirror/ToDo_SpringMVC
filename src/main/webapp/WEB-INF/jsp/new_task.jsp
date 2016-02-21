<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<%@include file="head.jsp" %>
<link rel="stylesheet" href="/css/fileInput.css">
<body>
<div class="container">
    <c:choose>
        <c:when test="${requestScope.edit}">
            <h2>Please edit fields</h2>
        </c:when>
        <c:otherwise>
            <h2>Please fill fields for create new task</h2>
        </c:otherwise>
    </c:choose>
    <form class="form-horizontal"  method="post" enctype="multipart/form-data"
          action="<c:url value="/tasks/file/upload"/>">
        <div class="form-group">
            <input type="hidden" id="location" value="${sessionScope.location}">
            <input type="hidden" name="idTask" id="idTask" value="${requestScope.task.idTask}">
            <input type="hidden" name="edit" id="edit" value="${requestScope.edit}">
            <label class="control-label col-sm-4" for="create">Create date:</label>

            <div class="col-sm-3">
                <input type="date" class="form-control" id="create"
                       name="create" value="${requestScope.task.creationDate}" readonly>
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-sm-4" for="execute">Execute date:</label>

            <div class="col-sm-3">
                <input type="date" class="form-control" id="execute"
                       name="execute" value="${requestScope.task.executionDate}" required>
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-sm-4" for="description">Description:</label>

            <div class="col-sm-5">
                <input type="text" class="form-control" id="description"
                       name="description" value="${requestScope.task.description}" required>
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-sm-4" for="file">Task file</label>
            <span class="btn btn-primary btn-file">Browse
                <input type="file" name="file" id="file">
            </span>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-4 col-sm-8">
                <button type="submit" class="btn btn-success" value="send">OK</button>
                <a href="${'/tasks/'.concat(sessionScope.location)}">
                    <button type="button" class="btn btn-warning">Cancel</button>
                </a>
            </div>
        </div>
    </form>
    <c:if test="${not requestScope.edit}">
        <script>
            document.getElementById('create').valueAsDate = new Date();
            var loc = document.getElementById('location').value;
            if (loc === 'today') {
                document.getElementById('execute').valueAsDate = new Date();
            } else if (loc === 'tomorrow') {
                var date = new Date();
                date.setDate(date.getDate() + 1);
                document.getElementById('execute').valueAsDate = date;
            }
        </script>
    </c:if>
</div>
<%@include file="errorMessage.jsp" %>
</body>
</html>
