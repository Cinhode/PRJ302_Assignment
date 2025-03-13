<%-- 
    Document   : leaverequest
    Created on : Mar 12, 2025, 6:21:16 PM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Leave Request</title>
        <link rel="stylesheet" href="../style/style.css">
    </head>
    <body>
        <div class="leaverequest-container">
            <h2>Leave Request</h2>
            <form action = "create" method="POST" class="login-form">

                <div class="input-group">
                    <label for="title">Title:</label>
                    <input type="text" id="title" name="title" required><br>
                </div>
                <div class="input-group">
                    <label for="name">Name:</label>
                    <input type="text" id="name" name="name" value="${sessionScope.user.employee.name}" required><br>
                </div>
                <div class="input-group">
                    <label for="startDate">Start Date:</label>
                    <input type="date" id="from" name="from" required><br>
                </div>
                <div class="input-group">
                    <label for="endDate">End Date:</label>
                    <input type="date" id="to" name="to" required><br>
                </div>
                <div class="input-group">
                    <label for="reason">Reason:</label><br>
                    <textarea id="reason" name="reason" rows="9" cols="59" required></textarea><br><br>
                </div>
                <input type="submit" class="btn" value="Submit">
            </form>
        </div>
    </body>
</html>
