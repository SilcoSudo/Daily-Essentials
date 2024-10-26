/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DB.DBConnect;
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

    public List<OrderHistory> getOrdersByUserId(int user_id) {
        try {
            List<OrderHistory> orderlist = new ArrayList<>();
            String sql = "SELECT o.order_id, o.user_id, o.order_date, o.total_amount, o.order_status, i.fee_shipp "
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
                        rs.getDouble("total_amount"),
                        rs.getInt("order_status"),
                        rs.getDouble("fee_shipp"));

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
                        rs.getDouble("total_amount"),
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

    public static void main(String[] args) {
        ViewOrderDAO orderDAO = new ViewOrderDAO();

        int userId = 26; 
        List<OrderHistory> orders = orderDAO.getOrdersByUserId(userId);

        if (orders != null && !orders.isEmpty()) {
            for (OrderHistory order : orders) {
                System.out.println("Order ID: " + order.getOrder_id()
                        + ", User ID: " + order.getUser_id()
                        + ", Order Date: " + order.getOrder_date()
                        + ", Total Amount: " + order.getTotal_amount()
                        + ", Order Status: " + order.getOrderStatusString()
                        + ", Shipping Fee: " + order.getFee_shipp());
            }
        } else {
            System.out.println("Không tìm thấy đơn hàng cho người dùng ID: " + userId);
        }
    }
}
