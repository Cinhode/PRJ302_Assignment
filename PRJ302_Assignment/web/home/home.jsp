<%-- 
    Document   : home.jsp
    Created on : Feb 26, 2025, 10:59:20 PM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel ="stylesheet" href="css/style.css">
    </head>
    <body>
        <h1>*Trang chủ* </h1>
        <h1>Welcome, ${sessionScope.user.displayname}!</h1> 
    <c:if test="${sessionScope.user.employee.manager ne null}">
        Report to ${sessionScope.user.employee.manager.name}  <br/>
    </c:if>
    Report to you: <br/>
    <c:forEach items="${sessionScope.user.employee.staffs}" var="s"> 
        ${s.name} <br/>
    </c:forEach>
    
</body>
</html>
