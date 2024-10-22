package DAO;

import DB.DBConnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

/**
 *
 * @author Yin Kenna
 */
public class AuthenDAO {

    public boolean isPassLogin(String username, String password) {
        Connection conn;
        PreparedStatement ps;
        ResultSet rs;

        try {
            conn = DBConnect.getConnection();
            String sql = "SELECT * FROM account WHERE username = ? AND password = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Login failed: " + e.getMessage());
        }
        return false;
    }

    public int getAccountID(String email) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnect.getConnection();

            String sql = "SELECT a.account_id "
                    + "FROM account a "
                    + "JOIN user_profile up ON up.user_id = a.user_id "
                    + "WHERE up.user_email = ?";

            ps = conn.prepareStatement(sql);
            ps.setString(1, email);

            rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("account_id");
            }
        } catch (SQLException e) {
            System.out.println("Account ID error: " + e.getMessage());
        }

        return -1;
    }

    public int getUserIdByUsername(String username) {
        int userId = -1;

        String query = "SELECT up.user_id "
                + "FROM account ac "
                + "JOIN user_profile up ON up.user_id = ac.user_id "
                + "WHERE ac.username = ?";

        try ( Connection conn = DBConnect.getConnection();  PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, username);

            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    userId = rs.getInt("user_id");
                }
            }

        } catch (SQLException e) {
            System.out.println("User ID: " + e);
        }

        return userId;
    }

    public String getFullNameUser(String username) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnect.getConnection();

            String sql = "SELECT up.user_fullname\n"
                    + "FROM user_profile up\n"
                    + "JOIN account ac ON ac.user_id = up.user_id\n"
                    + "WHERE ac.username = ?";

            ps = conn.prepareStatement(sql);
            ps.setString(1, username);

            rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString("user_fullname");
            }
        } catch (SQLException e) {
            System.out.println("Account User Full Name error: " + e.getMessage());
        }

        return null;
    }

    public boolean updatePassword(int accountId, String newPassword) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean updateSuccess = false;

        try {
            conn = DBConnect.getConnection();
            String sql = "UPDATE account SET password = ? WHERE account_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, newPassword);
            ps.setInt(2, accountId);

            int rowsAffected = ps.executeUpdate();
            updateSuccess = (rowsAffected > 0);

        } catch (SQLException e) {
            System.out.println("Error while updating password: " + e.getMessage());
        }

        return updateSuccess;
    }

    public String getLatestCodeValue(int accountId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String codeValue = null;

        try {
            conn = DBConnect.getConnection();

            String sql = "SELECT TOP 1 ca.code_value "
                    + "FROM code_authenticate ca "
                    + "WHERE ca.account_id = ? "
                    + "ORDER BY ca.create_at DESC";

            // Chuẩn bị câu truy vấn
            ps = conn.prepareStatement(sql);
            ps.setInt(1, accountId);

            rs = ps.executeQuery();

            if (rs.next()) {
                codeValue = rs.getString("code_value");
            }

        } catch (SQLException e) {
            System.out.println("Error while fetching latest code_value: " + e.getMessage());
        }

        return codeValue;
    }

    public boolean insertCode(int accountId, String codeValue) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBConnect.getConnection();
            String sql = "INSERT INTO code_authenticate (account_id, code_value, create_at) "
                    + "VALUES (?, ?, GETDATE())";

            ps = conn.prepareStatement(sql);
            ps.setInt(1, accountId);
            ps.setString(2, codeValue);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Insert code error: " + e.getMessage());
        }
        return false;
    }

    public boolean registerUser(String username, String password, String fullname, String phone, boolean gender) {
        Connection conn = null;
        PreparedStatement psCheck = null;
        PreparedStatement psInsertUser = null;
        PreparedStatement psInsertAccount = null;
        ResultSet rs = null;

        try {
            conn = DBConnect.getConnection();

            String checkSql = "SELECT * FROM account WHERE username = ?";
            psCheck = conn.prepareStatement(checkSql);
            psCheck.setString(1, username);
            rs = psCheck.executeQuery();

            if (rs.next()) {
                return false;
            }

            String insertUserSql = "INSERT INTO user_profile (user_fullname, user_phone, gender) VALUES (?, ?, ?)";
            psInsertUser = conn.prepareStatement(insertUserSql, Statement.RETURN_GENERATED_KEYS);
            psInsertUser.setString(1, fullname);
            psInsertUser.setString(2, phone);
            psInsertUser.setBoolean(3, gender);

            psInsertUser.executeUpdate();

            rs = psInsertUser.getGeneratedKeys();
            int userId = 0;
            if (rs.next()) {
                userId = rs.getInt(1);
            }

            String insertAccountSql = "INSERT INTO account (username, password, user_id, role, is_lock, is_delete, create_at) VALUES (?, ?, ?, ?, ?, ?, ?)";
            psInsertAccount = conn.prepareStatement(insertAccountSql);
            psInsertAccount.setString(1, username);
            psInsertAccount.setString(2, password);
            psInsertAccount.setInt(3, userId);
            psInsertAccount.setString(4, "user");
            psInsertAccount.setBoolean(5, false);
            psInsertAccount.setBoolean(6, false);
            psInsertAccount.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
            psInsertAccount.executeUpdate();

            return true;

        } catch (SQLException e) {
            System.out.println("Registration failed: " + e.getMessage());
        }
        return false;
    }

}
