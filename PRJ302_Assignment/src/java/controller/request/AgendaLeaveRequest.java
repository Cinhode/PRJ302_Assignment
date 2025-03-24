/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.request;

import controller.authentication.BaseRequiredAuthenticationController;
import dal.LeaveRequestDBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import model.LeaveRequest;
import model.User;

/**
 *
 * @author admin
 */
public class AgendaLeaveRequest extends HttpServlet {

    LeaveRequestDBContext dbContext = new LeaveRequestDBContext();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Lấy danh sách request từ DB
        HttpSession session = req.getSession();
        String staffname = req.getParameter("name");
        ArrayList<LeaveRequest> leaveRequests = new ArrayList();
        if (staffname == null) {
            User u = (User) session.getAttribute("user");
            if (u != null && u.getEmployee() != null) {
                leaveRequests = dbContext.fromtolist(u.getEmployee().getName());
                req.setAttribute("staffName", staffname);
            }
        } else {
            leaveRequests = dbContext.fromtolist(staffname);
        }

        // Đưa danh sách request vào request attribute để gửi sang JSP
        req.setAttribute("leaveRequests", leaveRequests);

        // Forward sang JSP
        req.getRequestDispatcher("/view/request/agenda.jsp").forward(req, resp);
    }
}
