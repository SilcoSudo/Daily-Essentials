/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DB.DBConnect;
import Model.UserProfile;
import Models.Account;
import jakarta.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Yin Kenna
 */
public class AccountDAO {

    public boolean updateUserProfile(int userId, String fullName, String phone, String email, boolean gender, String province, String district, String ward, String address) {
        String sql = "UPDATE user_profile "
                + "SET user_fullname = ?, user_phone = ?, user_email = ?, gender = ?, user_province = ?, user_district = ?, user_ward = ?, user_address = ? "
                + "WHERE user_id = ?";
        try ( Connection conn = DB.DBConnect.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, fullName);
            ps.setString(2, phone);
            ps.setString(3, email);
            ps.setBoolean(4, gender);
            ps.setString(5, province);
            ps.setString(6, district);
            ps.setString(7, ward);
            ps.setString(8, address);
            ps.setInt(9, userId);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Error updating user profile: " + e.getMessage());
            return false;
        }
    }

    public List<UserProfile> getInfoUser(String username) {
        Connection conn;
        PreparedStatement ps;
        ResultSet rs;
        List<UserProfile> result = new ArrayList<>();
        try {
            conn = DB.DBConnect.getConnection();
            String sql = "SELECT user_fullname, user_phone, user_email, gender, user_province, user_district, user_ward, user_address\n"
                    + "FROM user_profile up\n"
                    + "JOIN account a ON a.user_id = up.user_id\n"
                    + "WHERE username = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            rs = ps.executeQuery();
            UserProfile e = new UserProfile();
            if (rs.next()) {
                e.setFullName(rs.getString("user_fullname"));
                e.setUser_phone(rs.getInt("user_phone"));
                e.setUser_email(rs.getString("user_email"));
                e.setGender(rs.getBoolean("gender"));
                e.setUser_province(rs.getString("user_province"));
                e.setUser_district(rs.getString("user_district"));
                e.setUser_ward(rs.getString("user_ward"));
                e.setUser_address(rs.getString("user_address"));
            }
            result.add(e);
        } catch (SQLException e) {
            System.out.println("getInfoUser: " + e);
        }

        return result;
    }
    
    // Method to retrieve all accounts
    public ResultSet getAllAccountsResultSet() {
        String query = "SELECT a.account_id, a.username, a.password, up.user_fullname, up.user_phone, "
                + "up.user_email, a.role, a.is_lock, up.update_at "
                + "FROM account a "
                + "JOIN user_profile up ON a.user_id = up.user_id";

        try {
            // Get a connection to the database
            Connection conn = DBConnect.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);

            // Return the ResultSet from the executed query
            return ps.executeQuery(); // Returns the ResultSet
        } catch (SQLException e) {
            e.printStackTrace(); // Consider using a logging framework in production
            return null; // Handle error appropriately
        }
    }

    // Method to process the update of an account using data from an HttpServletRequest
    public boolean processUpdateAccount(HttpServletRequest request) {
        String id = request.getParameter("id");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String fullName = request.getParameter("fullName");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String role = request.getParameter("role");
        String status = request.getParameter("status");

        boolean isLocked = "Đã khóa".equals(status);

        // Create Account object with updated values
        Account account = new Account(
                Integer.parseInt(id),
                username,
                password,
                fullName,
                phone,
                email,
                role,
                isLocked,
                new Date(System.currentTimeMillis()) // Current date for updatedAt
        );

        return updateAccount(account);
    }

    // Method to retrieve an account by its ID
    public Account getAccountById(int id) {
        String query = "SELECT a.account_id, a.username, a.password, up.user_fullname, "
                + "up.user_phone, up.user_email, a.role, a.is_lock, up.update_at "
                + "FROM account a "
                + "JOIN user_profile up ON a.user_id = up.user_id "
                + "WHERE a.account_id = ?";

        try ( Connection conn = DBConnect.getConnection();  PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Account(
                        rs.getInt("account_id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("user_fullname"),
                        rs.getString("user_phone"),
                        rs.getString("user_email"),
                        rs.getString("role"),
                        rs.getBoolean("is_lock"),
                        rs.getDate("update_at")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Consider using a logging framework in production
        }
        return null;
    }

    // Method to update account details
    public boolean updateAccount(Account account) {
        String accountQuery = "UPDATE account SET username = ?, password = ?, role = ?, is_lock = ? WHERE account_id = ?";
        String userProfileQuery = "UPDATE user_profile SET user_fullname = ?, user_phone = ?, user_email = ?, update_at = ? WHERE user_id = (SELECT user_id FROM account WHERE account_id = ?)";

        try ( Connection conn = DBConnect.getConnection();  PreparedStatement psAccount = conn.prepareStatement(accountQuery);  PreparedStatement psUserProfile = conn.prepareStatement(userProfileQuery)) {

            // Update account details
            psAccount.setString(1, account.getUsername());
            psAccount.setString(2, account.getPassword());
            psAccount.setString(3, account.getRole());
            psAccount.setBoolean(4, account.isLocked());
            psAccount.setInt(5, account.getId());

            // Update user profile details
            psUserProfile.setString(1, account.getFullName());
            psUserProfile.setString(2, account.getPhone());
            psUserProfile.setString(3, account.getEmail());
            psUserProfile.setDate(4, new Date(System.currentTimeMillis())); // Update timestamp
            psUserProfile.setInt(5, account.getId());

            // Execute both updates
            int accountUpdated = psAccount.executeUpdate();
            int profileUpdated = psUserProfile.executeUpdate();

            return accountUpdated > 0 && profileUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace(); // Consider using a logging framework in production
            return false;
        }
    }

    // Method to delete an account by ID
    public boolean deleteAccount(int accountId) {
        String query = "DELETE FROM account WHERE account_id = ?";
        try ( Connection conn = DBConnect.getConnection();  PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, accountId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace(); // Consider using a logging framework in production
            return false;
        }
    }

    // Method to retrieve accounts based on search criteria
    public ResultSet getFilteredAccountsResultSet(String accountId, String status, String username) {
        StringBuilder query = new StringBuilder("SELECT a.account_id, a.username, a.password, up.user_fullname, "
                + "up.user_phone, up.user_email, a.role, a.is_lock, up.update_at "
                + "FROM account a "
                + "JOIN user_profile up ON a.user_id = up.user_id WHERE 1=1");

        // Build query based on provided parameters
        if (accountId != null && !accountId.isEmpty()) {
            query.append(" AND a.account_id = ?");
        }
        if (status != null && !status.isEmpty()) {
            query.append(" AND a.is_lock = ?");
        }
        if (username != null && !username.isEmpty()) {
            query.append(" AND a.username LIKE ?");
        }

        try {
            Connection conn = DBConnect.getConnection();
            PreparedStatement ps = conn.prepareStatement(query.toString());

            int index = 1;
            // Set the parameters
            if (accountId != null && !accountId.isEmpty()) {
                ps.setInt(index++, Integer.parseInt(accountId));
            }
            if (status != null && !status.isEmpty()) {
                int lockStatus = status.equals("locked") ? 1 : 0;
                ps.setInt(index++, lockStatus);
            }
            if (username != null && !username.isEmpty()) {
                ps.setString(index++, "%" + username + "%");
            }

            return ps.executeQuery(); // Return the ResultSet
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
