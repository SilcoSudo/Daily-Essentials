/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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

    public boolean registerUser(String username, String password, String fullname, String phone, String gender) {
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

            String insertUserSql = "INSERT INTO user_profile (user_fullname, user_phone) VALUES (?, ?)";
            psInsertUser = conn.prepareStatement(insertUserSql, Statement.RETURN_GENERATED_KEYS);
            psInsertUser.setString(1, fullname);
            psInsertUser.setString(2, phone);
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
