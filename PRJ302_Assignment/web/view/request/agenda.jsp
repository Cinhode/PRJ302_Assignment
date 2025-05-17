<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.GregorianCalendar" %>
<%@ page import="java.sql.Date" %>
<%@ page import="model.Employee" %> <!-- Thêm import cho model.Employee -->
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Leave Request Manager</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/style/assets/css/style.css">
        <style>
            .leave-table td, .leave-table th {
                border: 1px solid #ddd;
                padding: 8px;
                text-align: center;
            }
            .leave-day {
                background-color: #ffcccc; /* Màu đỏ nhạt cho ngày nghỉ */
            }
        </style>
    </head>
    <body>
        <div class="container">
            <!-- Navigation giữ nguyên -->
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
                    <li>
                        <a href="http://localhost:8080/Assignment/profile">
                            <span class="icon">
                                <ion-icon name="person-outline"></ion-icon>
                            </span>
                            <span class="title">Profile</span>
                        </a>
                    </li>
                    <li>
                        <a href="http://localhost:8080/Assignment/home">
                            <span class="icon">
                                <ion-icon name="home-outline"></ion-icon>
                            </span>
                            <span class="title">Dashboard</span>
                        </a>
                    </li>
                    <li>
                        <a href="http://localhost:8080/Assignment/request/create">
                            <span class="icon">
                                <ion-icon name="chatbubble-outline"></ion-icon>
                            </span>
                            <span class="title">Create</span>
                        </a>
                    </li>
                    <li>
                        <a href="http://localhost:8080/Assignment/request/receive">
                            <span class="icon">
                                <ion-icon name="eye-outline"></ion-icon>
                            </span>
                            <span class="title">View</span>
                        </a>
                    </li>
                    <li>
                        <a href="http://localhost:8080/Assignment/request/agenda">
                            <span class="icon">
                                <ion-icon name="stats-chart-outline"></ion-icon>
                            </span>
                            <span class="title">Agenda</span>
                        </a>
                    </li>
                    <li>
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
                    </div>
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
                <div class="details">
                    <div class="recentOrders">
                        <div class="cardHeader">
                            <h2>Leave Agenda<% if (request.getAttribute("staffName") != null) { %> of <%= request.getAttribute("staffName") %><% } else { %> of All Direct Staff<% } %></h2>
                            <a href="http://localhost:8080/Assignment/home/view" class="btn">View All</a>
                        </div>
                        <% 
                            Calendar currentCal = Calendar.getInstance();
                            int currentMonth = currentCal.get(Calendar.MONTH);
                            int currentYear = currentCal.get(Calendar.YEAR);

                            String monthParam = request.getParameter("month");
                            String yearParam = request.getParameter("year");

                            int displayMonth = monthParam != null ? Integer.parseInt(monthParam) : currentMonth;
                            int displayYear = yearParam != null ? Integer.parseInt(yearParam) : currentYear;

                            Calendar cal = new GregorianCalendar(displayYear, displayMonth, 1);
                            int daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

                            String[] months = {"Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4", "Tháng 5", "Tháng 6", 
                                            "Tháng 7", "Tháng 8", "Tháng 9", "Tháng 10", "Tháng 11", "Tháng 12"};

                            int prevMonth = displayMonth - 1;
                            int prevYear = displayYear;
                            int nextMonth = displayMonth + 1;
                            int nextYear = displayYear;

                            if (prevMonth < 0) {
                                prevMonth = 11;
                                prevYear--;
                            }
                            if (nextMonth > 11) {
                                nextMonth = 0;
                                nextYear++;
                            }

                            java.util.List<model.LeaveRequest> leaveRequests = (java.util.List<model.LeaveRequest>) request.getAttribute("leaveRequests");
                            java.util.List<model.Employee> directStaffs = (java.util.List<model.Employee>) request.getAttribute("directStaffs");
                            String staffName = (String) request.getAttribute("staffName");
                        %>
                        <div class="table-container">
                            <h2><%= months[displayMonth] %> <%= displayYear %></h2>
                            <div class="nav-container">
                                <div class="nav-buttons">
                                    <a href="${pageContext.request.contextPath}/request/agenda<% if (staffName != null) { %>?name=<%= staffName %><% } %>?month=<%= prevMonth %>&year=<%= prevYear %>"><button>Previous</button></a>
                                </div>
                                <div class="selector">
                                    <form id="monthForm" method="get" action="${pageContext.request.contextPath}/request/agenda">
                                        <% if (staffName != null) { %>
                                        <input type="hidden" name="name" value="<%= staffName %>">
                                        <% } %>
                                        <select name="month" onchange="document.getElementById('monthForm').submit()">
                                            <% for (int i = 0; i < 12; i++) { %>
                                            <option value="<%= i %>" <%= i == displayMonth ? "selected" : "" %>><%= months[i] %></option>
                                            <% } %>
                                        </select>
                                        <select name="year" onchange="document.getElementById('monthForm').submit()">
                                            <% 
                                                int startYear = currentYear - 10;
                                                int endYear = currentYear + 10;
                                                for (int i = startYear; i <= endYear; i++) { 
                                            %>
                                            <option value="<%= i %>" <%= i == displayYear ? "selected" : "" %>><%= i %></option>
                                            <% } %>
                                        </select>
                                    </form>
                                </div>
                                <div class="nav-buttons">
                                    <a href="${pageContext.request.contextPath}/request/agenda<% if (staffName != null) { %>?name=<%= staffName %><% } %>?month=<%= nextMonth %>&year=<%= nextYear %>"><button>Next</button></a>
                                </div>
                            </div>
                            <table class="leave-table">
                                <tr>
                                    <th>Staff Name</th>
                                    <% for (int day = 1; day <= daysInMonth; day++) { %>
                                    <th><%= day %></th>
                                    <% } %>
                                </tr>
                                <% 
                                    if (staffName != null) { 
                                %>
                                <tr>
                                    <td><%= staffName %></td>
                                    <% 
                                        for (int day = 1; day <= daysInMonth; day++) { 
                                            Calendar currentCellDate = new GregorianCalendar(displayYear, displayMonth, day);
                                            boolean isLeaveDay = false;
                                            if (leaveRequests != null) {
                                                for (model.LeaveRequest lr : leaveRequests) {
                                                    Date sqlDateFrom = lr.getFrom();
                                                    Date sqlDateTo = lr.getTo();
                                                    Calendar fromCal = Calendar.getInstance();
                                                    Calendar toCal = Calendar.getInstance();
                                                    fromCal.setTime(new java.util.Date(sqlDateFrom.getTime()));
                                                    toCal.setTime(new java.util.Date(sqlDateTo.getTime()));
                                                    if (!currentCellDate.before(fromCal) && !currentCellDate.after(toCal)) {
                                                        isLeaveDay = true;
                                                        break;
                                                    }
                                                }
                                            }
                                    %>
                                    <td <%= isLeaveDay ? "class=\"leave-day\"" : "" %>></td>
                                    <% } %>
                                </tr>
                                <% 
                                    } else if (directStaffs != null) { 
                                        // Hiển thị tất cả direct staff
                                        for (model.Employee staff : directStaffs) { // Sử dụng model.Employee
                                %>
                                <tr>
                                    <td><%= staff.getName() %></td>
                                    <% 
                                        for (int day = 1; day <= daysInMonth; day++) { 
                                            Calendar currentCellDate = new GregorianCalendar(displayYear, displayMonth, day);
                                            boolean isLeaveDay = false;
                                            if (leaveRequests != null) {
                                                for (model.LeaveRequest lr : leaveRequests) {
                                                    Date sqlDateFrom = lr.getFrom();
                                                    Date sqlDateTo = lr.getTo();
                                                    Calendar fromCal = Calendar.getInstance();
                                                    Calendar toCal = Calendar.getInstance();
                                                    fromCal.setTime(new java.util.Date(sqlDateFrom.getTime()));
                                                    toCal.setTime(new java.util.Date(sqlDateTo.getTime()));
                                                    // Giả định LeaveRequest có getEmployee() trả về Employee
                                                    if (!currentCellDate.before(fromCal) && !currentCellDate.after(toCal) && 
                                                        lr.getEmployee() != null && lr.getEmployee().getName().equals(staff.getName())) { 
                                                        isLeaveDay = true;
                                                        break;
                                                    }
                                                }
                                            }
                                    %>
                                    <td <%= isLeaveDay ? "class=\"leave-day\"" : "" %>></td>
                                    <% } %>
                                </tr>
                                <% 
                                        }
                                    } 
                                %>
                            </table>
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
                                        <a href="${pageContext.request.contextPath}/request/agenda?name=${s.name}" class="staff-link">
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