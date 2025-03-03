/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.request;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import model.Request;
import java.sql.Date;

/**
 *
 * @author admin
 */
public class AddLeaveRequest extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ArrayList<Request> requests = new ArrayList();
//        String[] indexes = req.getParameterValues("index");
//        for (String indexe : indexes) {
            String name = req.getParameter("name");
            String role = req.getParameter("role");
            String depart = req.getParameter("department");
            String from = req.getParameter("start_date");
            String to = req.getParameter("end_date");
            String reason = req.getParameter("reason");
            
            Request r = new Request();
            r.setName(name);
            r.setDepart(depart);
            r.setStart_date(Date.valueOf(from));
            r.setEnd_date(Date.valueOf(to));
            r.setRole(role);
            r.setReason(reason);
            
            requests.add(r);      
//        }
        
        req.setAttribute("model", requests);
        req.getRequestDispatcher("../view/request/list.jsp").forward(req, resp);

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("../request.html").forward(req, resp);
    }

}
