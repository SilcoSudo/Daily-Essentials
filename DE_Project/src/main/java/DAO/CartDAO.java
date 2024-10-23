/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.ProductModel;
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

    public void updateProductQuantity(int productId, int userId, boolean increase) {
        String updateQuery = "UPDATE cart SET quantity = quantity + ? WHERE product_id = ? AND user_id = ?";
        String deleteQuery = "DELETE FROM cart WHERE product_id = ? AND user_id = ? AND quantity = 0";

        try ( Connection conn = DB.DBConnect.getConnection()) {
            // Thực hiện UPDATE
            try ( PreparedStatement psUpdate = conn.prepareStatement(updateQuery)) {
                int quantityChange = increase ? 1 : -1;
                psUpdate.setInt(1, quantityChange);
                psUpdate.setInt(2, productId);
                psUpdate.setInt(3, userId);
                psUpdate.executeUpdate();
            }

            // Thực hiện DELETE nếu quantity = 0
            if (!increase) {
                try ( PreparedStatement psDelete = conn.prepareStatement(deleteQuery)) {
                    psDelete.setInt(1, productId);
                    psDelete.setInt(2, userId);
                    psDelete.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getProductQuantityInCart(int userId, int productId) {
        String query = "SELECT quantity FROM cart WHERE user_id = ? AND product_id = ? AND status = 0";
        try ( Connection conn = DB.DBConnect.getConnection();  PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, userId);
            ps.setInt(2, productId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("quantity");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<ProductModel> getAllProductCart() {
        List<ProductModel> productList = new ArrayList<>();
        String query = "SELECT p.image_url, p.product_name, p.product_price, c.quantity, c.product_id\n"
                + "FROM cart c\n"
                + "JOIN product p on c.product_id = p.product_id \n"
                + "WHERE c.status = 0";

        try ( Connection conn = DB.DBConnect.getConnection();  PreparedStatement ps = conn.prepareStatement(query);  ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ProductModel product = new ProductModel();
                product.setProductId(rs.getInt("product_id"));
                product.setImageUrl(rs.getString("image_url"));
                product.setProductName(rs.getString("product_name"));
                product.setProductPrice(rs.getBigDecimal("product_price"));
                product.setQuantityInCart(rs.getInt("quantity"));

                productList.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productList;
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
