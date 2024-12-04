<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Управление студентами</title>
    <style>
        body {
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            height: 100vh;
            margin: 0;
            font-family: Arial, sans-serif;
        }
        form, table {
            margin-bottom: 20px;
        }
        table {
            border-collapse: collapse;
            width: 80%;
        }
        th, td {
            border: 1px solid black;
            padding: 8px;
            text-align: center;
        }
        th {
            background-color: #f2f2f2;
        }
        button {
            padding: 10px 20px;
            font-size: 16px;
            margin: 5px;
            cursor: pointer;
        }
        .button-container {
            display: flex;
            justify-content: center;
            margin-top: 20px;
        }
    </style>
</head>
<body>

<h1>Управление студентами</h1>

<form action="base" method="post">
    <table>
        <thead>
        <tr>
            <th>Имя</th>
            <th>Почта</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td><input type="text" name="name" placeholder="Имя студента" required></td>
            <td><input type="email" name="email" placeholder="Почта студента" required></td>
        </tr>
        </tbody>
    </table>

    <div class="button-container">
        <button type="submit" name="submitAction" value="add">Добавить</button>
        <button type="submit" name="submitAction" value="delete">Удалить</button>
    </div>
</form>

<h2>Список студентов</h2>
<table border="1">
    <thead>
    <tr>
        <th>ID</th>
        <th>Имя</th>
        <th>Почты</th>
    </tr>
    </thead>
    <tbody>
        <c:forEach var="student" items="${students}">
            <tr>
                <td><c:out value="${student.student_id}"/></td>
                <td><c:out value="${student.student_name}"/></td>
                <td>
                    <c:forEach var="mail" items="${student.mails}">
                        <c:out value="${mail.mail_name}"/>
                    </c:forEach>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>

</body>
</html>