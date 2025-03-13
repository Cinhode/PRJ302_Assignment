<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Leave Request Manager</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    </head>
    <body>
        <div class="container">
            <div class="navigation">
                <ul>
                    <li>
                        <a href="#">
                            <span class="icon">
                                <ion-icon name="logo-octocat"></ion-icon>
                            </span>
                            <span class="title">Leave Request Manager</span>
                        </a>
                    </li>
                    <li> <%-- DASHBROAD   --%>
                        <a href="#">
                            <span class="icon">
                                <ion-icon name="home-outline"></ion-icon>
                            </span>
                            <span class="title">Dashboard</span>
                        </a>
                    </li><%-- CUSTOMER   --%>
                    <li>
                        <a href="#">
                            <span class="icon">
                                <ion-icon name="people-outline"></ion-icon>
                            </span>
                            <span class="title">Customers</span>
                        </a>
                    </li>
                    <li><%-- CREATE   --%>
                        <a href="http://localhost:8080/Assignment/request/create">
                            <span class="icon">
                                <ion-icon name="chatbubble-outline"></ion-icon>
                            </span>
                            <span class="title">Create</span>
                        </a>
                    </li>
                    <li><%-- VIEW   --%>
                        <a href="#">
                            <span class="icon">
                                <ion-icon name="eye-outline"></ion-icon>
                            </span>
                            <span class="title">View</span>
                        </a>
                    </li>
                    <li><%-- AGENDA   --%>
                        <a href="#">
                            <span class="icon">
                                <ion-icon name="stats-chart-outline"></ion-icon>
                            </span>
                            <span class="title">Agenda</span>
                        </a>
                    </li>
                    <li> <%-- HOME-DONE --%>
                        <a href="${pageContext.request.contextPath}/login">
                            <span class="icon">
                                <ion-icon name="log-out-outline"></ion-icon>
                            </span>
                            <span class="title">Sign Out</span>
                        </a>
                    </li>
                </ul>
            </div>
            <div class="main">
                <div class="topbar">
                    <div class="toggle">
                        <ion-icon name="menu-outline"></ion-icon>
                    </div><%-- SEARCH  --%>
                    <div class="search">
                        <label>
                            <input type="text" placeholder="Search here">
                            <ion-icon name="search-outline"></ion-icon>
                        </label>
                    </div>
                    <div class="user">
                        <img src="${pageContext.request.contextPath}/assets/image/usericon.png" alt="User">
                    </div>
                </div>
                <div class="details">
                    <div class="recentOrders">
                        <div class="cardHeader">
                            <h2>Recent Leave Request</h2>
                            <a href="#" class="btn">View All</a>
                        </div>
                        <table>
                            <thead>
                                <tr>
                                    <td>ID</td>
                                    <td>Owner</td>
                                    <td>From</td>
                                    <td>To</td>
                                    <td>Reason</td>
                                    <td>Status</td>
                                    <td>Processor</td>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="request" items="${leaverequest}">
                                    <%--<c:if test="${request.status eq 0}">--%>
                                    <tr>
                                        <td>${request.id}</td>
                                        <td>${request.owner}</td>
                                        <td>${request.date}</td>
                                        <td>${request.reason}</td>
                                        <td><span class="status pending">${request.status}</span></td>
                                        <td>${request.processor}</td>
                                    </tr>
                                    <%--</c:if>--%>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                    <div class="recentCustomers">
                        <div class="cardHeader">
                            <h2>Your Staff</h2>
                        </div>
                        <table>
                            <c:forEach items="${sessionScope.user.employee.staffs}" var="s">
                                <tr>
                                    <td width="60px">
                                        <div class="imgBx"><img src="${pageContext.request.contextPath}/assets/image/usericon.png" alt="User"></div>
                                    </td>
                                    <td>
                                        <h4>${s.name} <br> <span>${s.dept.name}</span></h4>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        <script src="${pageContext.request.contextPath}/assets/js/main.js"></script>
        <script type="module" src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.esm.js"></script>
        <script nomodule src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.js"></script>
    </body>
</html>
