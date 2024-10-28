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

    private String getOrderStatus(int status) {
        switch (status) {
            case 0:
                return "Đang xử lý";
            case 1:
                return "Ðã xác nhận";
            case 2:
                return "Đang vận chuyển";
            case 3:
                return "Ðã hoàn thành";
            case 4:
                return "Ðã hủy";
            default:
                return "Đang xử lý";
        }
    }

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

    public List<ProductStatistics> getProductStatisticsByCategory(int categoryId) {
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
                + "    category c ON p.category_id = c.category_id\n"
                + "WHERE \n"
                + "    p.category_id = ?;";

        try {
            conn = DBConnect.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, categoryId);
            rs = ps.executeQuery();

            while (rs.next()) {
                productlist.add(new ProductStatistics(
                        rs.getInt("product_id"),
                        rs.getString("product_name"),
                        rs.getString("image_url"),
                        rs.getInt("category_id"),
                        rs.getString("category_name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productlist;
    }

    public List<ProductStatistics> getAllCategories() {
        List<ProductStatistics> categoryList = new ArrayList<>();
        String sql = "SELECT category_id, category_name FROM category";

        try {
            conn = DBConnect.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                categoryList.add(new ProductStatistics(
                        rs.getInt("category_id"),
                        rs.getString("category_name")
                ));
            }
        } catch (SQLException e) {
        }

        return categoryList;
    }

    public List<ProductStatistics> getOrderStatusStatistics() {
        List<ProductStatistics> statistics = new ArrayList<>();
        try {
            String sql = "SELECT \n"
                    + "    order_status,\n"
                    + "    COUNT(*) AS order_count \n"
                    + "FROM \n"
                    + "    [order] \n"
                    + "GROUP BY \n"
                    + "    order_status";
            conn = DBConnect.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                ProductStatistics order_sta = new ProductStatistics(
                        rs.getInt("order_status"),
                        rs.getInt("order_count"));

                order_sta.setOrderStatusString(getOrderStatus(order_sta.getOrder_status()));
                statistics.add(order_sta);

            }
        } catch (SQLException e) {
        }

        return statistics;
    }

    public static void main(String[] args) {
        ProductStatisticsDAO dao = new ProductStatisticsDAO();
        List<ProductStatistics> products = dao.getOrderStatusStatistics();

        if (products != null && !products.isEmpty()) {
            for (ProductStatistics stat : products) {
                System.out.println("Trang thai: " + stat.getOrder_status() + ", So: " + stat.getOrderstatus_count());
            }
        } else {
            System.out.println("Loi");
        }
    }
}
