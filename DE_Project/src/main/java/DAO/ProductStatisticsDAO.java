/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DB.DBConnect;
import Model.OrderHistory;
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
            String sql = "SELECT product.product_id, product.product_name, category.category_name, category.category_id\n"
                    + "  FROM product, category\n"
                    + "  WHERE product.category_id = category.category_id";
            conn = DBConnect.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                productlist.add(new ProductStatistics(rs.getInt("product_id"),
                        rs.getString("product_name"),
                        rs.getInt("category_id"),
                        rs.getString("category_name")));
            }
            return productlist;
        } catch (SQLException e) {
        }
        return null;
    }

    public static void main(String[] args) {
        ProductStatisticsDAO product = new ProductStatisticsDAO();
        List<ProductStatistics> productlist = product.getAllProductStatistics();
        for (ProductStatistics o : productlist) {
            System.out.println(o);
        }
    }

}
