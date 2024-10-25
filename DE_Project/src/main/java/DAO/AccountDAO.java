/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.UserProfile;
import java.sql.Connection;
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
}
