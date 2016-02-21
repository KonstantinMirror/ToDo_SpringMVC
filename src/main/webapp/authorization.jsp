<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<%@include file="WEB-INF/jsp/head.jsp"%>
<body>
<div class="container">
    <h2 class="text-center text-primary">Please fill the fields for authorization</h2>
    <form class="form-horizontal" role="form" method="post" action="<c:url value="/user/authorization"/>">
        <jsp:include page="WEB-INF/jsp/loginAndPassword.jsp"/>
        <div class="form-group">
            <div class="col-sm-offset-4 col-sm-10">
                <button type="submit" class="btn btn-success" value="send">OK</button>
                <a href="/start.jsp"><button type="button" class="btn btn-warning" >Cancel</button></a>
            </div>
        </div>
    </form>
</div>
<%@include file="WEB-INF/jsp/errorMessage.jsp"%>
</body>
</html>



