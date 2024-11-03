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
                    + "    c.category_name,\n"
                    + "    COALESCE(SUM(CASE WHEN o.order_status = 3 THEN od.product_quantity END), 0) AS quantity_sold,\n"
                    + "    COALESCE(SUM(CASE WHEN o.order_status = 3 THEN od.product_quantity * od.product_price END), 0) AS revenue\n"
                    + "FROM \n"
                    + "    product p\n"
                    + "JOIN \n"
                    + "    category c ON p.category_id = c.category_id\n"
                    + "LEFT JOIN \n"
                    + "    order_details od ON p.product_id = od.product_id\n"
                    + "LEFT JOIN \n"
                    + "    [order] o ON od.order_id = o.order_id\n"
                    + "GROUP BY \n"
                    + "    p.product_id, p.product_name, c.category_name;";

            conn = DBConnect.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                productlist.add(new ProductStatistics(
                        rs.getInt("product_id"),
                        rs.getString("product_name"),
                        rs.getString("category_name"),
                        rs.getInt("quantity_sold"),
                        rs.getBigDecimal("revenue")
                ));
            }
            return productlist;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<ProductStatistics> getProductStatisticsByCategory(int categoryId) {
        List<ProductStatistics> productStatisticsList = new ArrayList<>();
        String sql = "SELECT "
                + "    p.product_id, "
                + "    p.product_name, "
                + "    c.category_name, "
                + "    COALESCE(SUM(CASE WHEN o.order_status = 3 THEN od.product_quantity END), 0) AS quantity_sold, "
                + "    COALESCE(SUM(CASE WHEN o.order_status = 3 THEN od.product_quantity * od.product_price END), 0) AS revenue "
                + "FROM "
                + "    product p "
                + "JOIN "
                + "    category c ON p.category_id = c.category_id "
                + "LEFT JOIN "
                + "    order_details od ON p.product_id = od.product_id "
                + "LEFT JOIN "
                + "    [order] o ON od.order_id = o.order_id "
                + "WHERE "
                + "    c.category_id = ? "
                + "GROUP BY "
                + "    p.product_id, p.product_name, c.category_name;";

        try {
            conn = DBConnect.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, categoryId); // Thay thế tham số
            rs = ps.executeQuery();

            while (rs.next()) {
                productStatisticsList.add(new ProductStatistics(
                        rs.getInt("product_id"),
                        rs.getString("product_name"),
                        rs.getString("category_name"),
                        rs.getInt("quantity_sold"),
                        rs.getBigDecimal("revenue")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productStatisticsList;
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

    public List<ProductStatistics> getTopSellingProducts(int limit) {
        List<ProductStatistics> topProducts = new ArrayList<>();
        String sql = "SELECT TOP " + limit + " \n"
                + "    p.product_id, \n"
                + "    p.product_name, \n"
                + "    c.category_name, \n"
                + "    SUM(od.product_quantity) AS quantity_sold, \n"
                + "    SUM(od.product_quantity * od.product_price) AS revenue \n"
                + "FROM \n"
                + "    product p \n"
                + "JOIN \n"
                + "    order_details od ON p.product_id = od.product_id \n"
                + "JOIN \n"
                + "    [order] o ON od.order_id = o.order_id\n"
                + "JOIN \n"
                + "    category c ON p.category_id = c.category_id \n"
                + "WHERE \n"
                + "    o.order_status = 3\n"
                + "GROUP BY \n"
                + "    p.product_id, p.product_name, c.category_name \n"
                + "ORDER BY \n"
                + "    quantity_sold DESC; ";

        try {
            conn = DBConnect.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                topProducts.add(new ProductStatistics(
                        rs.getInt("product_id"),
                        rs.getString("product_name"),
                        rs.getString("category_name"),
                        rs.getInt("quantity_sold"),
                        rs.getBigDecimal("revenue")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return topProducts;
    }

    public ProductStatistics getTotalRevenue() {
        ProductStatistics totalRevenue = new ProductStatistics();
        String sql = "SELECT \n"
                + "    SUM(od.product_quantity) AS total_quantity_sold,\n"
                + "    SUM(od.product_quantity * od.total_price) AS total_revenue\n"
                + "FROM \n"
                + "    [order] o\n"
                + "JOIN \n"
                + "    order_details od ON o.order_id = od.order_id\n"
                + "WHERE \n"
                + "    o.order_status = 3;";

        try {
            conn = DBConnect.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            if (rs.next()) {
                totalRevenue.setTotal_revenue(rs.getBigDecimal("total_revenue"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalRevenue;
    }

    public ProductStatistics getTotalRevenueComparison(int currentMonth, int currentYear, int previousMonth, int previousYear) {
        ProductStatistics totalRevenue = new ProductStatistics();
        String sql = "WITH MonthlyRevenue AS (\n"
                + "    SELECT \n"
                + "        MONTH(o.order_date) AS month, \n"
                + "        YEAR(o.order_date) AS year, \n"
                + "        SUM(od.product_quantity * od.total_price) AS total_revenue\n"
                + "    FROM \n"
                + "        [order] o\n"
                + "    JOIN \n"
                + "        order_details od ON o.order_id = od.order_id\n"
                + "    WHERE \n"
                + "        o.order_status = 3\n"
                + "    GROUP BY \n"
                + "        MONTH(o.order_date), YEAR(o.order_date)\n"
                + "), \n"
                + "CurrentMonth AS (\n"
                + "    SELECT month, total_revenue FROM MonthlyRevenue WHERE month = ? AND year = ?\n"
                + "), \n"
                + "PreviousMonth AS (\n"
                + "    SELECT month, total_revenue FROM MonthlyRevenue WHERE month = ? AND year = ?\n"
                + ")\n"
                + "SELECT \n"
                + "    COALESCE(CM.total_revenue, NULL) AS current_revenue, \n"
                + "    COALESCE(PM.total_revenue, NULL) AS previous_revenue,\n"
                + "    CASE \n"
                + "        WHEN PM.total_revenue IS NULL THEN NULL\n"
                + "        ELSE ((COALESCE(CM.total_revenue, 0) - PM.total_revenue) / PM.total_revenue) * 100\n"
                + "    END AS percent_change\n"
                + "FROM \n"
                + "    CurrentMonth CM \n"
                + "FULL OUTER JOIN \n"
                + "    PreviousMonth PM ON 1=1;";

        try {
            conn = DBConnect.getConnection();
            ps = conn.prepareStatement(sql);

            // Set parameters for current month and previous month
            ps.setInt(1, currentMonth);
            ps.setInt(2, currentYear);
            ps.setInt(3, previousMonth);
            ps.setInt(4, previousYear);

            rs = ps.executeQuery();
            if (rs.next()) {
                totalRevenue.setCurrentRevenue(rs.getBigDecimal("current_revenue"));
                totalRevenue.setPreviousRevenue(rs.getBigDecimal("previous_revenue"));
                totalRevenue.setPercentChange(rs.getBigDecimal("percent_change"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalRevenue;
    }

    public static void main(String[] args) {
        ProductStatisticsDAO dao = new ProductStatisticsDAO();
        List<ProductStatistics> products = dao.getAllProductStatistics();

//        if (stats != null) {
//            System.out.println("Tổng doanh thu: " + stats.getTotal_revenue());
//        } else {
//            System.out.println("Không có dữ liệu hoặc xảy ra lỗi.");
//        }
        for (ProductStatistics product : products) {
            System.out.println("Product ID: " + product.getProduct_id());
            System.out.println("Product Name: " + product.getProduct_name());
            System.out.println("Category Name: " + product.getCategory_name());
            System.out.println("Quantity Sold: " + product.getQuantitySold());
            System.out.println("Revenue: " + product.getRevenue());
            System.out.println("-----");
        }
    }
}
