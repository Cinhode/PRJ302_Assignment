/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.request;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dal.LeaveRequestDBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import model.LeaveRequest;

/**
 *
 * @author admin
 */
public class ReceiveLeaveRequest extends HttpServlet {
        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
           
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("id") != null) {
            try {
                int id = Integer.parseInt(req.getParameter("id"));
                System.out.println("Received ID: " + id); // Debug

                LeaveRequestDBContext ldb = new LeaveRequestDBContext();
                LeaveRequest request = ldb.get(id);
                System.out.println("Request Object: " + request); // Debug

                if (request == null) {
                    resp.setContentType("application/json");
                    resp.setCharacterEncoding("UTF-8");
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    resp.getWriter().write("{\"error\": \"Request not found\"}");
                    return;
                }

                // Chuyển đối tượng thành JSON
                Gson gson = new Gson();
                String json = gson.toJson(request);
                System.out.println("JSON Output: " + json); // Debug

                // Trả về JSON
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                resp.getWriter().write(json);

            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("{\"error\": \"Invalid ID format\"}");
                return;
            }
        } else {
            req.getRequestDispatcher("/view/request/receive.jsp").forward(req, resp);
        }

    }
}

