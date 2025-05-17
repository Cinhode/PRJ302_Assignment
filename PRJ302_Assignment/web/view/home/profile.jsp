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
                        <a href="http://localhost:8080/Assignment/profile">
                            <span class="icon">
                                <ion-icon name="person-outline"></ion-icon>
                            </span>
                            <span class="title">Profile</span>
                        </a>
                    </li>
                    <li> <%-- DASHBROAD   --%>
                        <a href="http://localhost:8080/Assignment/home">
                            <span class="icon">
                                <ion-icon name="home-outline"></ion-icon>
                            </span>
                            <span class="title">Dashboard</span>
                        </a>
                    <li><%-- CREATE-DONE   --%>
                        <a href="http://localhost:8080/Assignment/request/create">
                            <span class="icon">
                                <ion-icon name="chatbubble-outline"></ion-icon>
                            </span>
                            <span class="title">Create</span>
                        </a>
                    </li>
                    <li><%-- VIEW   --%>
                        <a href="http://localhost:8080/Assignment/request/receive">
                            <span class="icon">
                                <ion-icon name="eye-outline"></ion-icon>
                            </span>
                            <span class="title">View</span>
                        </a>
                    </li>
                    <li><%-- AGENDA   --%>
                        <a href="http://localhost:8080/Assignment/request/agenda">
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
                <!-- ================ Profile  ================= -->
                <div class="details">
                    <div class="recentOrders">
                        <div class="cardHeader">
                            <h2>Profile</h2>
                        </div>
                        <div class="profile-container">
                            <div class="profile-card">
                                <div class="profile-header">
                                    <a></a>
                                    <img src="view/request/avar.jpg" alt="Profile Picture" class="profile-pic">
                                    <a></a>
                                </div>
                                <div class="profile-info">
                                    <h2>${selectedStaff != null ? selectedStaff.username : sessionScope.user.username}</h2>
                                    <p class="information">New York, United States</p>
                                </div>
                                <div>
                                    <h3>Detail Information:</h3>
                                    <p class="information">
                                        Display name: ${selectedStaff != null ? selectedStaff.displayname : sessionScope.user.username}
                                    </p>
                                    <p class="information">
                                        Employee ID: ${selectedStaff != null ? selectedStaff.employee.id : sessionScope.user.employee.id}
                                    </p>
                                    <p class="information">
                                        Employee Name: ${selectedStaff != null ? selectedStaff.employee.name : sessionScope.user.employee.name}
                                    </p>
                                    <p class="information">
                                        Department: ${selectedStaff != null ? selectedStaff.employee.dept.name : sessionScope.user.employee.dept.name}
                                    </p>
                                    <p class="information">
                                        Manager: ${selectedStaff != null ? (selectedStaff.employee.manager.name == null ? 'BOSS' : selectedStaff.manager.name) : (sessionScope.user.employee.manager.name == null ? 'BOSS' : sessionScope.user.employee.manager.name)}
                                    </p>
                                    <p class="information">
                                        Staff: <br/>
                                        <c:forEach items="${selectedStaff != null ? selectedStaff.employee.staffs : sessionScope.user.employee.staffs}" var="s"> 
                                            ${s.name}, 
                                        </c:forEach>
                                    </p>

                                </div>
                            </div>
                        </div>
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
                                        <!-- Bọc toàn bộ h4 trong a để nhấn vào cả ô -->
                                        <a href="${pageContext.request.contextPath}/profile?staffId=${s.id}" class="staff-link">
                                            <h4>
                                                ${s.name}
                                                <br>
                                                <span>${s.dept.name}</span>
                                            </h4>
                                        </a>
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
