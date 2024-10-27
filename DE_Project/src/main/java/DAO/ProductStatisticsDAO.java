/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DB.DBConnect;
import Model.ProductStatistics;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nhatl
 */
public class ProductStatisticsDAO {

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public List<ProductStatistics> getAllProductStatistics() {
        try {
            List<ProductStatistics> productlist = new ArrayList<>();
            String sql = "SELECT \n"
                    + "    p.product_id,\n"
                    + "    p.product_name,\n"
                    + "    p.image_url,\n"
                    + "    p.category_id,\n"
                    + "    c.category_name\n"
                    + "FROM \n"
                    + "    product p\n"
                    + "JOIN \n"
                    + "    category c ON p.category_id = c.category_id;";
            conn = DBConnect.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                productlist.add(new ProductStatistics(
                        rs.getInt("product_id"),
                        rs.getString("product_name"),
                        rs.getString("image_url"),
                        rs.getInt("category_id"),
                        rs.getString("category_name")));
            }
            return productlist;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<ProductStatistics> getProductsByCategoryId(int categoryId) {
        List<ProductStatistics> productlist = new ArrayList<>();
        String sql = "SELECT p.product_id, p.product_name, p.image_url, p.category_id, c.category_name "
                + "FROM product p "
                + "JOIN category c ON p.category_id = c.category_id "
                + "WHERE p.category_id = ?";

        try ( Connection conn = DBConnect.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, categoryId);
            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    productlist.add(new ProductStatistics(
                            rs.getInt("product_id"),
                            rs.getString("product_name"),
                            rs.getString("image_url"),
                            rs.getInt("category_id"),
                            rs.getString("category_name")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productlist;
    }

    public static void main(String[] args) {
        ProductStatisticsDAO dao = new ProductStatisticsDAO();
        List<ProductStatistics> products = dao.getAllProductStatistics();

        if (products != null && !products.isEmpty()) {
            for (ProductStatistics product : products) {
                System.out.println(product);
            }
        } else {
            System.out.println("No products found or an error occurred.");
        }
    }
}
