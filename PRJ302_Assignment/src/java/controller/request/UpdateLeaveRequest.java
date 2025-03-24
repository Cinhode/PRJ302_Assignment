/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.request;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import controller.authentication.BaseRequiredAuthenticationController;
import dal.LeaveRequestDBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.out;
import model.User;

/**
 *
 * @author admin
 */
public class UpdateLeaveRequest extends BaseRequiredAuthenticationController {

  @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, User user) throws ServletException, IOException {
        System.out.println("Nhận request POST tới /request/update");

        StringBuilder jsonBuffer = new StringBuilder();
        try (BufferedReader reader = req.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                jsonBuffer.append(line);
            }
        }
        System.out.println("Payload: " + jsonBuffer.toString());

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        try (PrintWriter out = resp.getWriter()) {
            Gson gson = new Gson();
            JsonObject json = gson.fromJson(jsonBuffer.toString(), JsonObject.class);
            int id = json.has("id") ? json.get("id").getAsInt() : -1;
            int status = json.has("status") ? json.get("status").getAsInt() : -1;

            if (id == -1 || status == -1) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print("{\"message\":\"Invalid request data\"}");
                out.flush();
                return;
            }

            HttpSession session = req.getSession();
            User u = (User) session.getAttribute("user");
            LeaveRequestDBContext ldb = new LeaveRequestDBContext();
            boolean success = ldb.updateStatus(id, status, u); // Truyền chuỗi và user
            LeaveRequestDBContext ldb1 = new LeaveRequestDBContext();
            session.setAttribute("leaverequest", ldb1.list());
            
            System.out.println("Update success: " + success);

            if (success) {
                out.print("{\"message\":\"Cập nhật thành công\"}");
            } else {
                out.print("{\"message\":\"Failed to update\"}");
            }
            out.flush();
        } catch (Exception e) { // Catch tất cả exception
            System.out.println("Lỗi server: " + e.getMessage());
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"message\":\"Server error: " + e.getMessage() + "\"}");
            out.flush();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, User user) throws ServletException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
