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

/**
 *
 * @author admin
 */
public class UpdateLeaveRequest extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Đọc dữ liệu từ request
        try (BufferedReader reader = req.getReader()) {
            Gson gson = new Gson();
            JsonObject json = gson.fromJson(reader, JsonObject.class);

            // Lấy id và status từ dữ liệu JSON
            int id = json.has("id") ? json.get("id").getAsInt() : -1;
            int status = json.has("status") ? json.get("status").getAsInt() : -1;

            // Kiểm tra dữ liệu đầu vào
            if (id == -1 || status == -1) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);  // 400 Bad Request
                resp.setContentType("application/json");
                resp.getWriter().write("{\"message\":\"Invalid request data\"}");
                return;
            }

            // Xử lý cập nhật trạng thái đơn xin nghỉ
            LeaveRequestDBContext ldb = new LeaveRequestDBContext();
            boolean success = ldb.updateStatus(id, status);

            // Trả về kết quả
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");

            try (PrintWriter out = resp.getWriter()) {
                if (success) {
                    out.print("{\"message\":\"Successful\"}");
                } else {
                    out.print("{\"message\":\"Failed to update\"}");
                }
                out.flush();
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);  // 500 Internal Server Error
            resp.setContentType("application/json");
            resp.getWriter().write("{\"message\":\"Server error: " + e.getMessage() + "\"}");
        }
    }

@Override
protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }
    
    
    
}
