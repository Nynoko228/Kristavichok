<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Приветственная страница</title>
</head>
<body>
    <h1>Добро пожаловать, <%= request.getUserPrincipal().getName() %>!</h1>
    <p>Вы успешно авторизованы на странице профиля.</p>
    <!-- Можно добавить ссылку на выход из системы -->
    <form action="logout" method="POST">
        <a href="/Kursach/logout"><button type="button">Выйти</button></a>
    </form>
</body>
</html>
