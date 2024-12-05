<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Profile Page</title>
</head>
<body>
    <h1>Welcome to Your Profile</h1>

    <p>
        Hello,
        <c:out value="${pageContext.request.userPrincipal.name}" />!
    </p>

    <p>You have successfully logged in.</p>

    <h3>Actions:</h3>
    <ul>
        <li><a href="<c:url value='/logout' />">Logout</a></li>
        <li><a href="<c:url value='/base' />">Go to Main Page</a></li>
    </ul>
</body>
</html>
