/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DB.DBConnect;
import Model.OrderHistory;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nhatl
 */
public class CustomerOrderDAO {

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public List<OrderHistory> getOrdersByUserId(int user_id) {
        List<OrderHistory> orders = new ArrayList<>();
        try {
            String sql = "SELECT * FROM [order] WHERE user_id = ?";
            conn = DBConnect.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, user_id);
            rs = ps.executeQuery();

            while (rs.next()) {
                orders.add(new OrderHistory(
                        rs.getInt("order_id"),
                        rs.getInt("user_id"),
                        rs.getDate("order_date"),
                        rs.getDouble("total_amount")
                ));
            }
        } catch (SQLException e) {

        }
        return orders;
    }

    public static void main(String[] args) {
        CustomerOrderDAO ordersDAO = new CustomerOrderDAO();
        int userId = 1;
        List<OrderHistory> orderlist = ordersDAO.getOrdersByUserId(userId);

        for (OrderHistory order : orderlist) {
            System.out.println(order);
        }
    }
}
