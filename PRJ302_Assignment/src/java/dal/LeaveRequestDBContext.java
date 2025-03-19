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
        ArrayList<LeaveRequest> leaverequests = new ArrayList();

        try {
            String sql = "    SELECT lr.[lrid]\n"
                    + "                        ,lr.[title]\n"
                    + "                          ,lr.[reason]\n"
                    + "                         ,lr.[from]\n"
                    + "                        ,lr.[to]\n"
                    + "                         ,lr.[status]\n"
                    + "                        ,lr.[createddate]\n"
                    + "                          ,e.eid\n"
                    + "						,e.ename as [createdbyusername]\n"
                    + "                         ,p.[username] as [processedbyusername]\n"
                    + "                   	  ,p.[displayname] as [processedbydisplayname]\n"
                    + "                      FROM [LeaveRequests] lr\n"
                    + "                    	INNER JOIN Employees e ON e.eid = lr.createdby\n"
                    + "                    	LEFT JOIN Users p ON p.username = lr.processedby";

            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                LeaveRequest lr = new LeaveRequest();
                lr.setId(rs.getInt("lrid"));
                lr.setTitle(rs.getString("title"));
                lr.setReason(rs.getString("reason"));
                lr.setFrom(rs.getDate("from"));
                lr.setTo(rs.getDate("to"));

                String status = rs.getString("status");
                int s = Integer.parseInt(status);
                if (s == 0) {
                    lr.setStatus("Pending");
                } else if (s == 1) {
                    lr.setStatus("Approve");
                } else {
                    lr.setStatus("Reject");
                }

                lr.setCreateddate(rs.getTimestamp("createddate"));

                Employee e = new Employee();
                e.setId(rs.getInt("eid"));
                e.setName(rs.getString("createdbyusername"));

                lr.setCreatedby(e);

                String processbyusername = rs.getString("processedbyusername");
                if (processbyusername != null) {
                    User processby = new User();
                    processby.setUsername(processbyusername);
                    processby.setDisplayname(rs.getString("processedbydisplayname"));
                    lr.setProcessedby(processby);
                } else {
                    lr.setProcessedby(null);
                }

                leaverequests.add(lr);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LeaveRequestDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return leaverequests;
    }

    @Override
    public LeaveRequest get(int id) {
        LeaveRequest lr = null; // Định nghĩa biến lr từ đầu
        try {
            String sql = "SELECT lr.[lrid], lr.[title], lr.[reason], lr.[from], lr.[to], "
                    + "lr.[status], lr.[createddate], e.eid, e.ename AS createdbyusername, "
                    + "p.[username] AS processedbyusername, p.[displayname] AS processedbydisplayname "
                    + "FROM [LeaveRequests] lr "
                    + "INNER JOIN Employees e ON e.eid = lr.createdby "
                    + "LEFT JOIN Users p ON p.username = lr.processedby "
                    + "WHERE lr.lrid = ?";

            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                lr = new LeaveRequest();  // Khởi tạo đối tượng ở đây
                lr.setId(rs.getInt("lrid"));
                lr.setTitle(rs.getString("title"));
                lr.setReason(rs.getString("reason"));
                lr.setFrom(rs.getDate("from"));
                lr.setTo(rs.getDate("to"));
                lr.setCreateddate(rs.getTimestamp("createddate"));

                // Kiểm tra kiểu dữ liệu của cột "status" và xử lý an toàn
                String status = rs.getString("status");
                if (status != null) {
                    switch (status.trim()) {
                        case "0":
                            lr.setStatus("Pending");
                            break;
                        case "1":
                            lr.setStatus("Approve");
                            break;
                        case "2":
                            lr.setStatus("Reject");
                            break;
                        default:
                            lr.setStatus("Unknown");
                            break;
                    }
                }

                // Xử lý thông tin người tạo
                Employee e = new Employee();
                e.setId(rs.getInt("eid"));
                e.setName(rs.getString("createdbyusername"));
                lr.setCreatedby(e);

                // Xử lý thông tin người xử lý (nếu có)
                String processbyusername = rs.getString("processedbyusername");
                if (processbyusername != null) {
                    User processby = new User();
                    processby.setUsername(processbyusername);
                    processby.setDisplayname(rs.getString("processedbydisplayname"));
                    lr.setProcessedby(processby);
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(LeaveRequestDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lr; // Đảm bảo luôn return lr
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

    public void updatestatus(LeaveRequest model) {
        try {
            connection.setAutoCommit(false);
            String sql = "UPDATE [LeaveRequests]\n"
                    + "   SET [status] = ?\n"
                    + " WHERE lrid = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, model.getStatus());
            stm.setInt(2, model.getId());
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

    public boolean updateStatus(int id, int status) {
        try {
            String sql = "UPDATE LeaveRequest SET status = ? WHERE lrid = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, status);
            stm.setInt(2, id);
            int rowsUpdated = stm.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException ex) {
            Logger.getLogger(LeaveRequestDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public void delete(LeaveRequest model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
