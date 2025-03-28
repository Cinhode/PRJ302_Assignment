/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.authentication;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import model.User;
import dal.UserDBContext;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpSession;
import model.*;

/**
 *
 * @author admin
 */
public abstract class BaseRequiredAuthenticationController extends HttpServlet {

    private User getLoggedUser(HttpServletRequest req) {
        return (User) req.getSession().getAttribute("user");
    }    
    private boolean isAllowedAccess(User u,HttpServletRequest req){
        String current_endpoint= req.getServletPath();
            for (Role role : u.getRoles()) {
                for(Feature feature: role.getFeatures()){
                    if(feature.getUrl().equals(current_endpoint))
                        return true;
                }
        }
            return false;
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User u = getLoggedUser(req);
        if (u != null ) {
            doPost(req, resp, u);
        } else {
            resp.getWriter().println("Access denied.You do not have allowed");
        }
    }

    protected abstract void doPost(HttpServletRequest req, HttpServletResponse resp, User user) throws ServletException, IOException;

    protected abstract void doGet(HttpServletRequest req, HttpServletResponse resp, User user) throws ServletException, IOException;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User u = getLoggedUser(req);
        if (u != null &&  isAllowedAccess( u, req) ) {
            doGet(req, resp, u);
        } else {
            resp.getWriter().println("Access denied");
        }
    }
}
