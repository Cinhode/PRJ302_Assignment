<%-- 
    Document   : list
    Created on : Feb 20, 2025, 12:16:08 PM
    Author     : Nhi
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.*"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Request List</title>
    </head>
    <body>
       <h1>Welcome, ${sessionScope.user.username}!</h1>
    <c:if test="${sessionScope.user.role == 'Quản lý'}">
        <a href="/admin">Quản lý người dùng</a><br>
    </c:if>
    <c:if test="${sessionScope.user.role == 'USER'}">
        <a href="/user">Xem thông tin cá nhân</a><br>
    </c:if>
    <a href="/logout">Logout</a>
    </body>
</html>
