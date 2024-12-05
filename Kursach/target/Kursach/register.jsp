<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
    <form action="register" method="post">
        <h2>Register</h2>
        <label for="username">Username:</label>
        <input type="text" name="username" required/><br/>
        <label for="password">Password:</label>
        <input type="password" name="password" required/><br/>
        <label for="role">Role:</label>
        <input type="text" name="role" required/><br/>
        <input type="submit" value="Register"/>
    </form>
</body>
</html>
