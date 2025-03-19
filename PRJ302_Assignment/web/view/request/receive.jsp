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
                        <a href="http://localhost:8080/Assignment/home">
                            <span class="icon">
                                <ion-icon name="home-outline"></ion-icon>
                            </span>
                            <span class="title">Dashboard</span>
                        </a>
                        <!--                    </li><%-- CUSTOMER   --%>
                                            <li>
                                                <a href="#">
                                                    <span class="icon">
                                                        <ion-icon name="people-outline"></ion-icon>
                                                    </span>
                                                    <span class="title">Customers</span>
                                                </a>
                                            </li>-->
                    <li><%-- CREATE-DONE   --%>
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
                <!-- ================ Order Details List ================= -->
                <div class="details">
                    <div class="recentOrders">
                        <div class="cardHeader">
                            <h2>Recent Orders</h2>
                            <a href="http://localhost:8080/Assignment/home/view" class="btn">View All</a>
                        </div>
                        <table>
                            <thead>
                                <tr>
                                    <td>ID</td>
                                    <td>Owner</td>
                                    <td>Title</td>
                                    <td class="tddate">From</td>
                                    <td class="tddate">To</td>
                                    <td>Reason</td>
                                    <td>Status</td>
                                    <td>Processor</td>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="request" items="${leaverequest}">
                                    <tr onclick="showRequestDetails(${request.id})" >
                                        <td>${request.id}</td>
                                        <td>${request.createdby.name}</td>
                                        <td>${request.title}</td>
                                        <td>${request.from}</td>
                                        <td>${request.to}</td>
                                        <td>${request.reason}</td>
                                        <td><span class="status ${request.status}">${request.status}</span></td>
                                        <td>${request.processedby.username}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>

                        <!-- Modal -->
                        <div id="requestModal" class="modal">
                            <div class="modal-content">
                                <span class="close" onclick="closeModal()">&times;</span>
                                <form action="update" method="post" >
                                    <h2>Request Details</h2>
                                    <p><strong>Owner:</strong> <span id="modalOwner"></span></p>
                                    <p><strong>Title:</strong> <span id="modalTitle"></span></p>
                                    <p><strong>From:</strong> <span id="modalFrom"></span></p>
                                    <p><strong>To:</strong> <span id="modalTo"></span></p>
                                    <p><strong>Reason:</strong> <span id="modalReason"></span></p>
                                    <p><strong>Status:</strong> <span id="modalStatus"></span></p>
                                    <p><strong>Processor:</strong> <span id="modalProcessor"></span></p>
                                    <c:if test="${modalProcessor == null}">
                                    <button type="button" id="btnAccept" class="accept-btn">Accept</button>
                                    <button type="button" id="btnReject" class="reject-btn">Reject</button>
                                    </c:if>
                                    </form>

                            </div>
                        </div>

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
        <script>
            function showRequestDetails(id) {
                if (!id) {
                    console.error("Invalid ID:", id); // Debugging
                    return;
                }
                fetch("http://localhost:8080/Assignment/request/receive?id=" + id)
                        .then(response => {
                            if (response.headers.get('Content-Type').includes('application/json')) {
                                return response.json();
                            } else {
                                throw new Error('Phản hồi không phải JSON');
                            }
                        })
                        .then(data => {
                            // Điền dữ liệu vào modal
                            document.getElementById("modalOwner").innerText = data.createdby.name;
                            document.getElementById("modalTitle").innerText = data.title;
                            document.getElementById("modalFrom").innerText = data.from;
                            document.getElementById("modalTo").innerText = data.to;
                            document.getElementById("modalReason").innerText = data.reason;
                            document.getElementById("modalStatus").innerText = data.status;
                            document.getElementById("modalProcessor").innerText = data.processedby ? data.processedby.username : " ";

                            // Cập nhật sự kiện cho nút Accept và Reject
                            document.getElementById("btnAccept").onclick = function () {
                                updateRequestStatus(data.id, 1); // 1 = Accept
                            };
                            document.getElementById("btnReject").onclick = function () {
                                updateRequestStatus(data.id, 2); // 2 = Reject
                            };

                            // Hiển thị modal
                            document.getElementById("requestModal").style.display = "block";
                        })
                        .catch(error => console.error("Error:", error));
            }

// Đóng modal khi nhấn ra ngoài
            function closeModal() {
                document.getElementById("requestModal").style.display = "none";
            }

// Đóng modal khi click ra ngoài vùng modal-content
            window.onclick = function (event) {
                let modal = document.getElementById("requestModal");
                if (event.target === modal) {
                    modal.style.display = "none";
                }
            }

            function updateRequestStatus(id, status) {
                fetch("http://localhost:8080/Assignment/request/update", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify({id: id, status: status})
                })
                        .then(response => response.json())
                        .then(data => {
                            alert(data.message);
                            closeModal(); // Đóng modal sau khi cập nhật
                            location.reload(); // Reload trang để cập nhật trạng thái
                        })
                        .catch(error => console.error("Error:", error));
            }


        </script>


        <script src="${pageContext.request.contextPath}/assets/js/main.js"></script>
        <script type="module" src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.esm.js"></script>
        <script nomodule src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.js"></script>
    </body>
</html>
