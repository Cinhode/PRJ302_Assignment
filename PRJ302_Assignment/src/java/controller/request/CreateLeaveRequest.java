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
        lr.setCreatedby(user);
        lr.setCreateddate(Date.valueOf(req.getParameter("createddate")));
        lr.setFrom(Date.valueOf(req.getParameter("from")));
        lr.setTo(Date.valueOf(req.getParameter("to")));
        lr.setId(Integer.parseInt(req.getParameter("lrid")));
        lr.setReason(req.getParameter("reason"));
        lr.setStatus(Integer.parseInt(req.getParameter("status")));
        lr.setTitle(req.getParameter("title"));

        Employee e = new Employee();
        e.setId(Integer.parseInt(req.getParameter("eid")));
        e.setName(req.getParameter("ename"));
        lr.setOwner(e);

        LeaveRequestDBContext db = new LeaveRequestDBContext();
        db.insert(lr);
        //req.getRequestDispatcher("../view/insert.jsp").forward(req, resp);

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, User user) throws ServletException, IOException {
        ArrayList<Employee> employees = new ArrayList<>();
        employees.add(user.getEmployee());
        for (Employee staff : user.getEmployee().getStaffs()) {
            employees.add(staff);
        }
        req.setAttribute("employees", employees);

        //req.getRequestDispatcher("leaverequest/create.jsp").forward(req, resp);
    }

}
