/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.authentication;
import dal.EmployeeDBContext;
import dal.UserDBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import model.Employee;
import model.User;
public class LoginController extends HttpServlet{

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        UserDBContext db = new UserDBContext();
        User user = db.get(username, password);
        if(user != null)
        {
            EmployeeDBContext edb = new EmployeeDBContext();
            Employee profile = edb.get(user.getEmployee().getId());
            profile.setManager(user.getEmployee().getManager());
            user.setEmployee(profile);
            HttpSession session = req.getSession();
            session.setAttribute("user", user);
            resp.sendRedirect("home");
        }
        else
        {
            resp.getWriter().println("Access denied!");
        }
    
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("login.html").forward(req, resp);
    
    }
    
}
