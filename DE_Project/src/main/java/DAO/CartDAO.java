/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Yin Kenna
 */
public class CartDAO {

    public void insertProductToCart(int userID, int productId, int quantity) {
        String query = "INSERT INTO cart (user_id, product_id, quantity, status) VALUES (?, ?, ?, ?)";

        try ( Connection conn = DB.DBConnect.getConnection();  PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, userID);
            ps.setInt(2, productId);
            ps.setInt(3, quantity);
            ps.setInt(4, 0);

            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("insertProductToCart :" + e);
        }
    }

    public int getTotalCartItems(int userID) {
        int totalItems = 0;
        String query = "SELECT SUM(quantity) AS total FROM cart WHERE user_id = ? AND status = 0";

        try ( Connection conn = DB.DBConnect.getConnection();  PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, userID);

            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    totalItems = rs.getInt("total");
                }
            }
        } catch (SQLException e) {
            System.out.println("getTotalCartItems: " + e);
        }
        return totalItems;
    }
}
