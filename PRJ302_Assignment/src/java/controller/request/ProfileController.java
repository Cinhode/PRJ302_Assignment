/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.request;

import controller.authentication.BaseRequiredAuthenticationController;
import dal.EmployeeDBContext;
import dal.UserDBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import model.Department;
import model.Employee;
import model.User;

/**
 *
 * @author admin
 */
public class ProfileController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String staffId = req.getParameter("staffId");
        User user = (User) req.getSession().getAttribute("user");
        UserDBContext udb = new UserDBContext();
        EmployeeDBContext edb = new EmployeeDBContext();

        if (staffId != null && !staffId.isEmpty()) {
            try {
                // Tìm staff theo ID
                Employee staff = edb.get(Integer.parseInt(staffId));
                User u = edb.getInfor(Integer.parseInt(staffId));

                if (staff != null && u != null) {
                    // Nếu u có employee, cập nhật thông tin từ u vào staff
                    if (u.getEmployee() != null) {
                        staff.setId(u.getEmployee().getId());
                        staff.setName(u.getEmployee().getName());
                        staff.setDept(u.getEmployee().getDept());
                    }
                    // Gán staff đã cập nhật vào u
                    u.setEmployee(staff);
                    req.setAttribute("selectedStaff", u);
                } else {
                    req.setAttribute("selectedStaff", null); // Không tìm thấy staff hoặc user
                }
            } catch (NumberFormatException e) {
                req.setAttribute("selectedStaff", null); // staffId không phải số
            }
        } else {
            req.setAttribute("selectedStaff", null); // Không có staffId
        }

        // Forward về JSP
        req.getRequestDispatcher("view/home/profile.jsp").forward(req, resp);
    }
}
