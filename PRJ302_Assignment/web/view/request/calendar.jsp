<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Calendar Agenda</title>
        <style>
            .controls {
                text-align: center;
                margin: 20px;
            }
            .calendar {
                width: 80%;
                margin: 0 auto;
                border: 1px solid #000;
                border-radius: 5px;
                overflow: hidden;
            }
            .header {
                display: flex;
                background-color: #f0f0f0;
            }
            .header div {
                flex: 1;
                text-align: center;
                padding: 10px;
                border-right: 1px solid #ccc;
                border-bottom: 1px solid #ccc;
                background-color: #e0e0e0;
            }
            .header div:last-child {
                border-right: none;
            }
            .days {
                display: grid;
                grid-template-columns: repeat(7, 1fr);
                gap: 1px;
                background-color: #ccc;
            }
            .day {
                background-color: #fff;
                border: 1px solid #ccc;
                height: 80px;
                text-align: center;
                padding: 5px;
                box-sizing: border-box;
            }
            .empty {
                background-color: #f0f0f0;
                border: 1px solid #ccc;
                height: 80px;
            }
            /* Màu sắc mẫu cho các ngày (sẽ cập nhật logic sau) */
            .day.sunday {
                background-color: #a3c9f0;
            }
            .day.monday {
                background-color: #f0a3a3;
            }
            .day.tuesday {
                background-color: #a3f0a3;
            }
            .day.wednesday {
                background-color: #f0f0a3;
            }
            .day.thursday {
                background-color: #f0a3f0;
            }
            .day.friday {
                background-color: #a3f0f0;
            }
            .day.saturday {
                background-color: #f0e6a3;
            }
        </style>
    </head>
    <body>
        <div class="controls">
            <select id="month">
                <option value="1">January</option>
                <option value="2">February</option>
                <option value="3">March</option>
                <option value="4">April</option>
                <option value="5">May</option>
                <option value="6">June</option>
                <option value="7">July</option>
                <option value="8">August</option>
                <option value="9">September</option>
                <option value="10">October</option>
                <option value="11">November</option>
                <option value="12">December</option>
            </select>
            <select id="year">
                <% for(int i = 2025; i <= 2030; i++) { %>
                <option value="<%=i%>"><%=i%></option>
                <% } %>
            </select>
            <button onclick="updateCalendar()">Update</button>
        </div>

        <div class="calendar">
            <div class="header">
                <div>Sunday</div>
                <div>Monday</div>
                <div>Tuesday</div>
                <div>Wednesday</div>
                <div>Thursday</div>
                <div>Friday</div>
                <div>Saturday</div>
            </div>
            <div class="days" id="calendarDays">
                <!-- Ô ngày sẽ được tạo động bằng JavaScript -->
            </div>
        </div>

        <script>
            function updateCalendar() {
                var month = document.getElementById("month").value;
                var year = document.getElementById("year").value;
                // Gọi Servlet để lấy dữ liệu lịch
                fetch('/calendar?month=' + month + '&year=' + year)
                        .then(response => response.json())
                        .then(data => {
                            var daysDiv = document.getElementById("calendarDays");
                            daysDiv.innerHTML = '';

                            // Lặp qua tất cả các ngày (bao gồm ô trống)
                            data.days.forEach(day => {
                                var dayDiv = document.createElement("div");
                                if (day.dayNumber === 0) {
                                    dayDiv.className = "empty";
                                } else {
                                    dayDiv.className = "day " + day.dayOfWeek.toLowerCase();
                                    dayDiv.textContent = day.dayNumber;
                                }
                                daysDiv.appendChild(dayDiv);
                            });
                        })
                        .catch(error => console.error('Error fetching calendar data:', error));
            }
            // Tải lịch mặc định khi trang mở
            window.onload = updateCalendar;
        </script>
    </body>
</html>