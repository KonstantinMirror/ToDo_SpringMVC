<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<nav>
    <div class="container">
        <ul class="list-inline">
            <c:choose>
                <c:when test="${ not empty sessionScope.user}">
                   <ul class="breadcrumb">
                       <li > Hello <c:out value="${sessionScope.user.login}"/></li>
                       <li ><a href="<c:url value="/tasks/today" />">Tasks</a></li>
                       <li class="text-right col-sm-offset-10">
                           <form action="<c:url value="/user/logout" />" method="post">
                               <a href="javascript:" onclick="parentNode.submit();">Logout</a>
                               <input type="hidden" name="mess" value=#/>
                           </form>
                       </li>
                   </ul>
                </c:when>
                <c:otherwise>
                    <ul class="breadcrumb">
                        <li><a href="<c:url value="/authorization.jsp" />">Login</a></li>
                        <li><a href="<c:url value="/registration.jsp"/> ">Registrate</a></li>
                    </ul>
                </c:otherwise>
            </c:choose>
        </ul>
    </div>
</nav>


