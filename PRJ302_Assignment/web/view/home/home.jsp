<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Leave Request Manager</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/style/assets/css/style.css">
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
                    <li> <%-- Profile --%>
                        <a href="${pageContext.request.contextPath}/profile">
                            <span class="icon">
                                <ion-icon name="person-outline"></ion-icon>
                            </span>
                            <span class="title">Profile</span>
                        </a>
                    </li>
                    <li> <%-- DASHBROAD   --%>
                        <a href="home">
                            <span class="icon">
                                <ion-icon name="home-outline"></ion-icon>
                            </span>
                            <span class="title">Dashboard</span>
                        </a>
                    <li><%-- CREATE-DONE   --%>
                        <a href="request/create">
                            <span class="icon">
                                <ion-icon name="chatbubble-outline"></ion-icon>
                            </span>
                            <span class="title">Create</span>
                        </a>
                    </li>
                    <li><%-- VIEW   --%>
                        <a href="request/receive">
                            <span class="icon">
                                <ion-icon name="eye-outline"></ion-icon>
                            </span>
                            <span class="title">View</span>
                        </a>
                    </li>
                    <li><%-- AGENDA   --%>
                        <a href="request/agenda">
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
                        <img src="${pageContext.request.contextPath}/style/assets/image/usericon.png" alt="User">
                    </div>


                </div>
                <!-- ================ Order Details List ================= -->
                <div class="details">
                    <div class="recentOrders">
                        <div class="cardHeader">
                            <h2>Recent Orders</h2>
                            <a href="request/receive" class="btn">View All</a>
                        </div>
                        <table>
                            <thead>
                                <tr>
                                    <td>Owner</td>
                                    <td>Title</td>
                                    <td class="tddate">From</td>
                                    <td class="tddate">To</td>
                                    <td>Status</td>
                                    <td>Processor</td>

                                </tr>
                            </thead>
                            <tbody>
                                <c:choose>
                                    <c:when test="${not empty leaverequest}">
                                        <c:forEach var="request" items="${leaverequest}">
                                            <c:set var="isDirectStaffOrSelf" value="false" />
                                            <!-- Kiểm tra nếu request.createdby.name là user hiện tại -->
                                            <c:if test="${request.createdby.name eq sessionScope.user.employee.name}">
                                                <c:set var="isDirectStaffOrSelf" value="true" />
                                            </c:if>
                                            <!-- Kiểm tra nếu request.createdby.name nằm trong direcstaffs -->
                                            <c:forEach var="staff" items="${sessionScope.user.employee.directstaffs}">
                                                <c:if test="${request.createdby.name eq staff.name}">
                                                    <c:set var="isDirectStaffOrSelf" value="true" />
                                                </c:if>
                                            </c:forEach>
                                            <!-- Chỉ hiển thị request nếu thỏa mãn cả 2 điều kiện -->
                                            <c:if test="${fn:contains(request.status, 'Pending') && isDirectStaffOrSelf}">
                                                <tr>
                                                    <td>${request.createdby.name}</td>
                                                    <td>${request.title}</td>
                                                    <td>${request.from}</td>
                                                    <td>${request.to}</td>
                                                    <td><span class="status Pending">${request.status}</span></td>
                                                    <td>${request.processedby.employee.name}</td>
                                                </tr>
                                            </c:if>
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise>
                                        <tr><td colspan="6">No pending requests found.</td></tr>
                                    </c:otherwise>
                                </c:choose>                       
                            </tbody>
                        </table>
                    </div>
                    <div class="recentCustomers">
                        <div class="cardHeader">
                            <h2>Your Staff</h2>
                        </div>
                        <table>
                            <c:forEach items="${sessionScope.user.employee.directstaffs}" var="s">
                                <tr>
                                    <td width="60px">
                                        <div class="imgBx"><img src="${pageContext.request.contextPath}/style/assets/image/usericon.png" alt="User"></div>
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
        <script src="${pageContext.request.contextPath}/style/assets/js/main.js"></script>
        <script type="module" src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.esm.js"></script>
        <script nomodule src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.js"></script>
    </body>
</html>
