<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${ not empty requestScope.errorMessage}">
  <div class="alert alert-warning">
    <strong>Warning!</strong> <c:out value="${requestScope.errorMessage}"/>
  </div>
</c:if>
