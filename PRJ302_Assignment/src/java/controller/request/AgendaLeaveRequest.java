package controller.request;

import dal.LeaveRequestDBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import model.Employee;
import model.LeaveRequest;
import model.User;

public class AgendaLeaveRequest extends HttpServlet {

    LeaveRequestDBContext dbContext = new LeaveRequestDBContext();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        ArrayList<LeaveRequest> allLeaveRequests = new ArrayList<>();
        ArrayList<Employee> directStaffs = new ArrayList<>();

        if (user != null && user.getEmployee() != null) {
            // Lấy danh sách direct staff của user hiện tại
            directStaffs = user.getEmployee().getDirectstaffs();
            
            String staffname = req.getParameter("name");
            if (staffname == null) {
                if (directStaffs != null) {
                    for (Employee staff : directStaffs) {
                        ArrayList<LeaveRequest> staffRequests = dbContext.fromtolist(staff.getName());
                        allLeaveRequests.addAll(staffRequests); // Gộp tất cả request vào một danh sách
                    }
                }
                // Thêm leave requests của chính user hiện tại nếu cần
                allLeaveRequests.addAll(dbContext.fromtolist(user.getEmployee().getName()));
            } else {
                allLeaveRequests = dbContext.fromtolist(staffname);
                req.setAttribute("staffName", staffname);
            }
        }

        // Đưa danh sách tổng hợp và directstaffs vào request attribute
        req.setAttribute("leaveRequests", allLeaveRequests);
        req.setAttribute("directStaffs", directStaffs);

        // Forward sang JSP
        req.getRequestDispatcher("/view/request/agenda.jsp").forward(req, resp);
    }
}