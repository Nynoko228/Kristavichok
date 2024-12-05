<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
    <form action="login" method="post">
        <h2>Login</h2>
        <label for="username">Username:</label>
        <input type="text" name="username" required/><br/>
        <label for="password">Password:</label>
        <input type="password" name="password" required/><br/>
        <input type="submit" value="Login"/>
    </form>
    <div style="color:red">
        <%= request.getAttribute("errorMessage") %>
    </div>
</body>
</html>
