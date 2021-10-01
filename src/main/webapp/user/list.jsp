<%--
  Created by IntelliJ IDEA.
  User: nguye
  Date: 9/30/2021
  Time: 5:01 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>User list</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-F3w7mX95PdgyTmZZMECAngseQB83DfGTowi0iMjiWaeVhAn4FJkqJByhZMI3AhiU" crossorigin="anonymous">
</head>
<style>
    a {
        text-decoration: none;
        min-width: 100px;
    }
</style>
<body>

<div class="container">
    <h1>User list</h1>
    <hr>
    <a class="btn btn-primary" href="users?action=create" role="button">Create</a>
    <hr>
    <table class="table table-striped table-hover">
        <thead>
        <tr>
            <th>#</th>
            <th>Name</th>
            <th>Address</th>
            <th>Email</th>
            <th></th>
            <th></th>
        </tr>
        </thead>

        <tbody>
        <c:forEach items="${users}" var="user">
            <tr>
                <td>${user.id}</td>
                <td>${user.name}</td>
                <td>${user.address}</td>
                <td>${user.email}</td>
                <td><a class="btn btn-warning" href="users?action=edit&id=${user.id}" role="button">Edit</a></td>
                <td><a class="btn btn-danger" onclick="confirmDelete(${user.id})" role="button">Delete</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<script>
    function confirmDelete(id) {
        let checkDelete = confirm("Are you sure?");
        if (checkDelete) {
            window.location.href = "users?action=delete&id=" + id;
            alert("DELETE SUCCESSFULLY!!!")
            window.location.reload();
        }
    }
</script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-/bQdsTh/da6pkI1MST/rWKFNjaCP5gBSY4sEBT38Q/9RBh9AH40zEOg7Hlq2THRZ"
        crossorigin="anonymous">
</script>
</body>
</html>
