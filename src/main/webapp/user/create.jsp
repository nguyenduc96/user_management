<%--
  Created by IntelliJ IDEA.
  User: nguye
  Date: 10/1/2021
  Time: 8:26 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Create user</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-F3w7mX95PdgyTmZZMECAngseQB83DfGTowi0iMjiWaeVhAn4FJkqJByhZMI3AhiU" crossorigin="anonymous">
</head>
<style>
    a {
        text-decoration: none;
    }

    input {
        width: 60%;
    }

    div button {
        position: relative;
        left: 40%;
    }
</style>
<body>
<div class="container">
    <a class="btn btn-primary" href="users" role="button">Back to home</a>
    <h1>
        <c:if test="${message != null}">
            ${message}
        </c:if>
    </h1>
    <form method="post">
        <table class="table table-striped table-hover">
            <tr>
                <td>Name</td>
                <td><input type="text" placeholder="Enter name" name="name"></td>
            </tr>
            <tr>
                <td>Address</td>
                <td><input type="text" placeholder="Enter address" name="address"></td>
            </tr>
            <tr>
                <td>Email</td>
                <td><input type="text" placeholder="Enter mail" name="email"></td>
            </tr>
        </table>
        <button class="btn btn-success">Create</button>
    </form>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-/bQdsTh/da6pkI1MST/rWKFNjaCP5gBSY4sEBT38Q/9RBh9AH40zEOg7Hlq2THRZ"
        crossorigin="anonymous">
</script>
</body>
</html>
