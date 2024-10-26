/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

/**
 *
 * @author Qi
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import DB.DBConnect;
import java.math.BigDecimal;

public class ProductWMDAO {

    // Hàm lấy toàn bộ danh sách sản phẩm từ DB
    public ResultSet getAllProducts() {
        String query = "SELECT p.product_name, p.product_id, p.product_sku, "
                + "p.product_price, p.product_description, l.label_name, "
                + "w.warehouse_name, p.product_quantity, c.category_name, p.image_url "
                + "FROM product p "
                + "JOIN category c ON p.category_id = c.category_id "
                + "JOIN label l ON c.label_id = l.label_id "
                + "JOIN inventory_report ir ON p.product_id = ir.product_id "
                + "JOIN warehouse w ON ir.warehouse_code = w.warehouse_code";
        ResultSet rs = null;
        try {
            Connection conn = DBConnect.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public ResultSet getFilteredProducts(String productName, String productID, String price, String label, String warehouse) {
        String query = "SELECT p.product_name, p.product_id, p.product_sku, p.product_price, "
                + "p.product_description, p.product_quantity, p.image_url, l.label_name, c.category_name, w.warehouse_name "
                + "FROM product p "
                + "JOIN category c ON p.category_id = c.category_id "
                + "JOIN label l ON c.label_id = l.label_id "
                + "JOIN inventory_report ir ON p.product_id = ir.product_id "
                + "JOIN warehouse w ON ir.warehouse_code = w.warehouse_code WHERE 1=1";

        if (productName != null && !productName.isEmpty()) {
            query += " AND p.product_name LIKE ?";
        }
        if (productID != null && !productID.isEmpty()) {
            query += " AND p.product_id = ?";
        }
        if (price != null && !price.isEmpty()) {
            query += " AND p.product_price <= ?";
        }
        if (label != null && !label.isEmpty()) {
            query += " AND l.label_name = ?";
        }
        if (warehouse != null && !warehouse.isEmpty()) {
            query += " AND w.warehouse_name = ?";
        }

        try {
            Connection conn = DBConnect.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);

            int index = 1;
            if (productName != null && !productName.isEmpty()) {
                ps.setString(index++, "%" + productName + "%");
            }
            if (productID != null && !productID.isEmpty()) {
                ps.setInt(index++, Integer.parseInt(productID));
            }
            if (price != null && !price.isEmpty()) {
                ps.setBigDecimal(index++, new BigDecimal(price));
            }
            if (label != null && !label.isEmpty()) {
                ps.setString(index++, label);
            }
            if (warehouse != null && !warehouse.isEmpty()) {
                ps.setString(index++, warehouse);
            }

            return ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Hàm cập nhật thông tin sản phẩm
    public boolean updateProduct(int productId, String productName, String productDescription, BigDecimal productPrice,
            int productQuantity, String labelName, String categoryName, String imageUrl) {
        // Get the category and label IDs
        int labelId = getLabelId(labelName);
        int categoryId = getCategoryId(categoryName);

        // Ghi log giá trị lấy được
        System.out.println("Label ID: " + labelId);
        System.out.println("Category ID: " + categoryId);

        if (labelId == -1 || categoryId == -1) {
            System.out.println("Invalid label or category name.");
            return false; // Trả về false nếu nhãn hoặc danh mục không hợp lệ
        }

        String query = "UPDATE product SET product_name = ?, product_description = ?, product_price = ?, "
                + "product_quantity = ?, category_id = ?, image_url = ? WHERE product_id = ?";

        try ( Connection conn = DBConnect.getConnection();  PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, productName);
            ps.setString(2, productDescription);
            ps.setBigDecimal(3, productPrice);
            ps.setInt(4, productQuantity);
            ps.setInt(5, categoryId); // Use the category ID
            ps.setString(6, imageUrl);
            ps.setInt(7, productId); // Product ID for the WHERE clause

            int rowsUpdated = ps.executeUpdate();
            System.out.println("Rows updated: " + rowsUpdated); // Ghi log số hàng đã cập nhật
            return rowsUpdated > 0; // Return true if update was successful
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Return false if there was an error
        }
    }

    private int getLabelId(String labelName) {
        String query = "SELECT label_id FROM label WHERE label_name = ?";
        try ( Connection conn = DBConnect.getConnection();  PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, labelName);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("label_id");
            } else {
                System.out.println("Label not found: " + labelName); // Thêm thông báo không tìm thấy
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Trả về -1 nếu không tìm thấy
    }

    private int getCategoryId(String categoryName) {
        String query = "SELECT category_id FROM category WHERE category_name = ?";
        try ( Connection conn = DBConnect.getConnection();  PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, categoryName);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("category_id");
            } else {
                System.out.println("Category not found: " + categoryName); // Thêm thông báo không tìm thấy
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Trả về -1 nếu không tìm thấy
    }

    // Hàm xóa sản phẩm
    public boolean deleteProduct(int productId) {
        String query = "DELETE FROM product WHERE product_id = ?";

        try ( Connection conn = DBConnect.getConnection();  PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, productId);
            int rowsDeleted = ps.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Lấy tên kho hàng
    public ResultSet getWarehouses() {
        String query = "SELECT warehouse_name FROM warehouse";
        ResultSet rs = null;
        try {
            Connection conn = DBConnect.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;

    }

    public ResultSet getProductTypes() {
        String query = "SELECT type_name FROM product_types"; // Adjust table name based on your database schema
        try {
            Connection conn = DBConnect.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            return ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ResultSet getProductCategories() {
        String query = "SELECT category_name FROM product_categories"; // Adjust table name based on your database schema
        try {
            Connection conn = DBConnect.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            return ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
