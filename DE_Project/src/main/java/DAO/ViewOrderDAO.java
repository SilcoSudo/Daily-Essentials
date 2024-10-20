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

    public List<OrderHistory> getAllOrder() {

        try {
            List<OrderHistory> orderlist = new ArrayList<>();
            String sql = "SELECT * FROM [order]";
            conn = DBConnect.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                orderlist.add(new OrderHistory(rs.getInt("order_id"),
                        rs.getInt("user_id"),
                        rs.getDate("order_date"),
                        rs.getDouble("total_amount")));
            }
            return orderlist;
        } catch (SQLException e) {
        }
        return null;
    }

    public static void main(String[] args) {
        ViewOrderDAO orders = new ViewOrderDAO();
        List<OrderHistory> orderlist = orders.getAllOrder();
        for (OrderHistory o : orderlist) {
            System.out.println(o);
        }
    }
}
