/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.ProductModel;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 *
 * @author Yin Kenna
 */
public class OrdersDAO {

    public boolean processPaymentTransaction(int userId, BigDecimal totalAmount, List<ProductModel> products, BigDecimal feeShipp) {
        Connection conn = null;
        PreparedStatement orderStmt = null;
        PreparedStatement orderDetailsStmt = null;
        PreparedStatement invoiceStmt = null;
        PreparedStatement productUpdateStmt = null;
        PreparedStatement cartUpdateStmt = null;

        try {
            conn = DB.DBConnect.getConnection();
            conn.setAutoCommit(false); // Bắt đầu giao dịch

            // 1. Tạo đơn hàng
            String orderSql = "INSERT INTO [order] ([user_id], total_amount, order_status) VALUES (?, ?, 0)";
            orderStmt = conn.prepareStatement(orderSql, Statement.RETURN_GENERATED_KEYS);
            orderStmt.setInt(1, userId);
            orderStmt.setBigDecimal(2, totalAmount);
            orderStmt.executeUpdate();

            ResultSet rsOrder = orderStmt.getGeneratedKeys();
            int orderId = 0;
            if (rsOrder.next()) {
                orderId = rsOrder.getInt(1);
            }

            // 2. Thêm chi tiết đơn hàng
            String orderDetailsSql = "INSERT INTO [order_details] (order_id, product_id, product_quantity, product_price) VALUES (?, ?, ?, ?)";
            orderDetailsStmt = conn.prepareStatement(orderDetailsSql);
            for (ProductModel product : products) {
                orderDetailsStmt.setInt(1, orderId);
                orderDetailsStmt.setInt(2, product.getProductId());
                orderDetailsStmt.setInt(3, product.getQuantityInCart());
                orderDetailsStmt.setBigDecimal(4, product.getProductPrice());
                orderDetailsStmt.addBatch();
            }
            orderDetailsStmt.executeBatch();

            // 3. Tạo hóa đơn
            String invoiceSql = "INSERT INTO [invoice] (order_id, fee_shipp, total_amount, invoice_status) VALUES (?, ?, ?, 0)";
            invoiceStmt = conn.prepareStatement(invoiceSql);
            invoiceStmt.setInt(1, orderId);
            invoiceStmt.setBigDecimal(2, feeShipp);
            invoiceStmt.setBigDecimal(3, totalAmount);
            invoiceStmt.executeUpdate();

            // 4. Cập nhật số lượng sản phẩm sau khi mua
            String productUpdateSql = "UPDATE product SET product_quantity = product_quantity - ? WHERE product_id = ?";
            productUpdateStmt = conn.prepareStatement(productUpdateSql);
            for (ProductModel product : products) {
                productUpdateStmt.setInt(1, product.getQuantityInCart());
                productUpdateStmt.setInt(2, product.getProductId());
                productUpdateStmt.addBatch();
            }
            productUpdateStmt.executeBatch();

            // 5. Cập nhật trạng thái sản phẩm trong giỏ hàng
            String cartUpdateSql = "UPDATE cart SET status = 1 WHERE product_id = ? AND user_id = ?";
            cartUpdateStmt = conn.prepareStatement(cartUpdateSql);
            for (ProductModel product : products) {
                cartUpdateStmt.setInt(1, product.getProductId());
                cartUpdateStmt.setInt(2, userId);
                cartUpdateStmt.addBatch();
            }
            cartUpdateStmt.executeBatch();

            conn.commit(); // Xác nhận giao dịch nếu tất cả thao tác thành công
            return true;

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback(); // Hủy giao dịch nếu có lỗi
                    System.out.println("Transaction rolled back due to error: " + e.getMessage());
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
            return false;

        } finally {
            // Đóng tài nguyên
            try {
                if (orderStmt != null) {
                    orderStmt.close();
                }
                if (orderDetailsStmt != null) {
                    orderDetailsStmt.close();
                }
                if (invoiceStmt != null) {
                    invoiceStmt.close();
                }
                if (productUpdateStmt != null) {
                    productUpdateStmt.close();
                }
                if (cartUpdateStmt != null) {
                    cartUpdateStmt.close();
                }
                if (conn != null) {
                    conn.setAutoCommit(true); // Khôi phục chế độ tự động commit
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
