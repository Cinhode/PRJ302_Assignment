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
import java.sql.Date;
import model.Employee;

/**
 *
 * @author admin
 */
public class CreateLeaveRequest extends BaseRequiredAuthenticationController {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, User user) throws ServletException, IOException {
        LeaveRequest lr = new LeaveRequest();
        HttpSession session = req.getSession();
        lr.setCreatedby(((User) session.getAttribute("user")).getEmployee());
        lr.setFrom(Date.valueOf(req.getParameter("from")));
        lr.setTo(Date.valueOf(req.getParameter("to")));
        lr.setReason(req.getParameter("reason"));
        lr.setTitle(req.getParameter("title"));

        Employee e = new Employee();

        e.setId(user.getEmployee().getId());
        e.setName(user.getEmployee().getName());
        lr.setOwner(e);

        LeaveRequestDBContext db = new LeaveRequestDBContext();
        db.insert(lr);
        
        LeaveRequestDBContext ldb = new LeaveRequestDBContext();
        ArrayList<LeaveRequest> ls = ldb.list();
        session.setAttribute("leaverequest", ls);
        
        resp.sendRedirect(req.getContextPath() + "/home");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, User user) throws ServletException, IOException {
        ArrayList<Employee> employees = new ArrayList<>();
        employees.add(user.getEmployee());
        for (Employee staff : user.getEmployee().getStaffs()) {
            employees.add(staff);
        }
        req.setAttribute("employees", employees);
        req.getRequestDispatcher("/view/request/leaverequest.jsp").forward(req, resp);
    }

}
