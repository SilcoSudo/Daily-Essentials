/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DB.DBConnect;
import Model.OrderDetail;
import Model.OrderHistory;
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
public class ViewOrderDAO {

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    private String getOrderStatus(int status) {
        switch (status) {
            case 0:
                return "Processing";
            case 1:
                return "Confirmed";
            case 2:
                return "Shipping";
            case 3:
                return "Completed";
            case 4:
                return "Cancelled";
            default:
                return "Processing";
        }
    }

    public List<OrderHistory> getOrdersByUserId(int user_id) {
        try {
            List<OrderHistory> orderlist = new ArrayList<>();
            String sql = "SELECT o.order_id, o.user_id, o.order_date, o.total_amount, o.order_status "
                    + "FROM [order] o "
                    + "JOIN invoice i ON o.order_id = i.order_id "
                    + "WHERE o.user_id = ?";
            conn = DBConnect.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, user_id);
            rs = ps.executeQuery();

            while (rs.next()) {
                OrderHistory orders = new OrderHistory(
                        rs.getInt("order_id"),
                        rs.getInt("user_id"),
                        rs.getDate("order_date"),
                        rs.getBigDecimal("total_amount"),
                        rs.getInt("order_status")
                );
                orders.setOrderStatusString(getOrderStatus(orders.getOrder_status()));
                orderlist.add(orders);
            }
            return orderlist;
        } catch (SQLException e) {

        }
        return null;
    }

    public List<OrderHistory> getAllOrder() {
        try {
            List<OrderHistory> orderlist = new ArrayList<>();
            String sql = "SELECT * FROM [order]";
            conn = DBConnect.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {

                OrderHistory orders = new OrderHistory(
                        rs.getInt("order_id"),
                        rs.getInt("user_id"),
                        rs.getDate("order_date"),
                        rs.getBigDecimal("total_amount"),
                        rs.getInt("order_status"));

                orders.setOrderStatusString(getOrderStatus(orders.getOrder_status()));
                orderlist.add(orders);
            }
            return orderlist;
        } catch (SQLException e) {
        }
        return null;
    }

    public boolean updateOrderStatus(int orderId, int status) {
        String sql = "UPDATE [order] SET order_status = ? WHERE order_id = ?";
        try {
            conn = DBConnect.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, status);
            ps.setInt(2, orderId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
        }
        return false;
    }

    public OrderHistory getOrderById(int orderId) {
        OrderHistory order = null;
        String sql = "SELECT o.order_id, o.user_id, o.order_date, o.total_amount, o.order_status "
                + "FROM [order] o "
                + "JOIN invoice i ON o.order_id = i.order_id "
                + "WHERE o.order_id = ?";

        try {
            conn = DBConnect.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, orderId);
            rs = ps.executeQuery();

            if (rs.next()) {
                order = new OrderHistory(
                        rs.getInt("order_id"),
                        rs.getInt("user_id"),
                        rs.getDate("order_date"),
                        rs.getBigDecimal("total_amount"),
                        rs.getInt("order_status"));

                order.setOrderStatusString(getOrderStatus(order.getOrder_status()));
            }
        } catch (SQLException e) {
        }
        return order;
    }

    public List<OrderDetail> getOrderDetailsWithProducts(int orderId) {
        List<OrderDetail> orderDetails = new ArrayList<>();
        String query = "SELECT od.product_id, od.product_quantity, od.total_price, p.product_name, p.image_url "
                + "FROM order_details od JOIN product p ON od.product_id = p.product_id "
                + "WHERE od.order_id = ?";

        try {
            conn = DBConnect.getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, orderId);
            rs = ps.executeQuery();

            while (rs.next()) {
                OrderDetail detail = new OrderDetail(
                        rs.getInt("product_id"),
                        rs.getInt("product_quantity"),
                        rs.getBigDecimal("total_price"),
                        rs.getString("product_name"),
                        rs.getString("image_url")
                );
                orderDetails.add(detail);
            }
        } catch (SQLException e) {
        }
        return orderDetails;
    }

}
