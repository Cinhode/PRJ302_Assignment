/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.util.ArrayList;
import model.LeaveRequest;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Employee;
import model.User;

/**
 *
 * @author sonnt-local
 */
public class LeaveRequestDBContext extends DBContext<LeaveRequest> {

    @Override
    public ArrayList<LeaveRequest> list() {
        try {
            String sql = "              SELECT lr.[lrid]\n"
                    + "                        ,lr.[title]\n"
                    + "                          ,lr.[reason]\n"
                    + "                         ,lr.[from]\n"
                    + "                        ,lr.[to]\n"
                    + "                         ,lr.[status]\n"
                    + "                        ,lr.[createddate]\n"
                    + "                          ,e.eid\n"
                    + "                         ,e.ename as [createdbyusername]\n"
                    + "                         ,p.[username] as [processedbyusername]\n"
                    + "                   	  ,p.[displayname] as [processedbydisplayname]\n"
                    + "                      FROM [LeaveRequests] lr\n"
                    + "                    	INNER JOIN Employees e ON e.eid = lr.createdby\n"
                    + "                    	LEFT JOIN Users p ON p.username = lr.processedby\n";

            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            ArrayList<LeaveRequest> leaverequests = new ArrayList();
            if (rs.next()) {
                LeaveRequest lr = new LeaveRequest();
                lr.setId(rs.getInt("lrid"));
                lr.setTitle(rs.getString("title"));
                lr.setReason(rs.getString("reason"));
                lr.setFrom(rs.getDate("from"));
                lr.setTo(rs.getDate("to"));
                lr.setStatus(rs.getInt("status"));
                lr.setCreateddate(rs.getTimestamp("createddate"));

                Employee e = new Employee();
                e.setId(rs.getInt("eid"));
                e.setName(rs.getNString("createdbyusername"));

                lr.setCreatedby(e);

                String processbyusername = rs.getString("processedbyusername");
                if (processbyusername != null) {
                    User processby = new User();
                    processby.setUsername(processbyusername);
                    processby.setDisplayname(rs.getString("processedbydisplayname"));
                    lr.setProcessedby(processby);
                }
                leaverequests.add(lr);
            }
            return leaverequests;
        } catch (SQLException ex) {
            Logger.getLogger(LeaveRequestDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public LeaveRequest get(int id) {
        try {
            String sql = "              SELECT lr.[lrid]\n"
                    + "                        ,lr.[title]\n"
                    + "                          ,lr.[reason]\n"
                    + "                         ,lr.[from]\n"
                    + "                        ,lr.[to]\n"
                    + "                         ,lr.[status]\n"
                    + "                        ,lr.[createddate]\n"
                    + "                          ,e.eid\n"
                    + "                         ,e.ename as [createdbyusername]\n"
                    + "                         ,p.[username] as [processedbyusername]\n"
                    + "                   	  ,p.[displayname] as [processedbydisplayname]\n"
                    + "                      FROM [LeaveRequests] lr\n"
                    + "                    	INNER JOIN Employees e ON e.eid = lr.createdby\n"
                    + "                    	LEFT JOIN Users p ON p.username = lr.processedby\n"
                    + "                             WHERE lr.lrid = ?";

            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                LeaveRequest lr = new LeaveRequest();
                lr.setId(rs.getInt("lrid"));
                lr.setTitle(rs.getString("title"));
                lr.setReason(rs.getString("reason"));
                lr.setFrom(rs.getDate("from"));
                lr.setTo(rs.getDate("to"));
                lr.setStatus(rs.getInt("status"));
                lr.setCreateddate(rs.getTimestamp("createddate"));

                Employee e = new Employee();
                e.setId(rs.getInt("eid"));
                e.setName(rs.getNString("createdbyusername"));

                lr.setCreatedby(e);

                String processbyusername = rs.getString("processedbyusername");
                if (processbyusername != null) {
                    User processby = new User();
                    processby.setUsername(processbyusername);
                    processby.setDisplayname(rs.getString("processedbydisplayname"));
                    lr.setProcessedby(processby);
                }
                return lr;

            }
        } catch (SQLException ex) {
            Logger.getLogger(LeaveRequestDBContext.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void insert(LeaveRequest model) {
        try {
            connection.setAutoCommit(false);
            String sql = "INSERT INTO [dbo].[LeaveRequests]\n"
                    + "           ([title]\n"
                    + "           ,[reason]\n"
                    + "           ,[from]\n"
                    + "           ,[to]\n"
                    + "           ,[status]\n"
                    + "           ,[createdby]\n"
                    + "           ,[createddate]\n"
                    + "           ,[processedby])\n"
                    + "     VALUES\n"
                    + "           (?,?,\n"
                    + "		   ?,?,\n"
                    + "		   0,?,getdate(),null)";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, model.getTitle());
            stm.setString(2, model.getReason());
            stm.setDate(3, model.getFrom());
            stm.setDate(4, model.getTo());

            stm.setInt(5, model.getOwner().getId());
//          stm.setInt(6, model . get Username );
            stm.executeUpdate();

            //get request id
            String sql_getid = "SELECT @@IDENTITY as lrid";
            PreparedStatement stm_getid = connection.prepareStatement(sql_getid);
            ResultSet rs = stm_getid.executeQuery();
            if (rs.next()) {
                model.setId(rs.getInt("lrid"));
            }
            connection.commit();

        } catch (SQLException ex) {
            Logger.getLogger(LeaveRequestDBContext.class
                    .getName()).log(Level.SEVERE, null, ex);
            try {
                connection.rollback();

            } catch (SQLException ex1) {
                Logger.getLogger(LeaveRequestDBContext.class
                        .getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            try {
                connection.setAutoCommit(true);

            } catch (SQLException ex) {
                Logger.getLogger(LeaveRequestDBContext.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
            if (connection != null)
                try {
                connection.close();

            } catch (SQLException ex) {
                Logger.getLogger(LeaveRequestDBContext.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @Override
    public void update(LeaveRequest model) {
        try {
            connection.setAutoCommit(false);
            String sql = "UPDATE [LeaveRequests]\n"
                    + "   SET [title] = ?\n"
                    + "      ,[reason] = ?\n"
                    + "      ,[from] = ?\n"
                    + "      ,[to] = ?\n"
                    + "      ,[owner_eid] = ?\n"
                    + " WHERE lrid = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, model.getTitle());
            stm.setString(2, model.getReason());
            stm.setDate(3, model.getFrom());
            stm.setDate(4, model.getTo());
            stm.setInt(5, model.getOwner().getId());
            stm.setInt(6, model.getId());
            stm.executeUpdate();
            connection.commit();

        } catch (SQLException ex) {
            Logger.getLogger(LeaveRequestDBContext.class
                    .getName()).log(Level.SEVERE, null, ex);
            try {
                connection.rollback();

            } catch (SQLException ex1) {
                Logger.getLogger(LeaveRequestDBContext.class
                        .getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            try {
                connection.setAutoCommit(true);

            } catch (SQLException ex) {
                Logger.getLogger(LeaveRequestDBContext.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
            if (connection != null)
                try {
                connection.close();

            } catch (SQLException ex) {
                Logger.getLogger(LeaveRequestDBContext.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void delete(LeaveRequest model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
