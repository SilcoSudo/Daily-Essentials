package DAO;

import DB.DBConnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Model.Product;
import java.math.BigDecimal;

public class ProductWMDAO {

    // Hàm lấy toàn bộ danh sách sản phẩm từ DB (đã bỏ kho)
    public ResultSet getAllProducts() {
        String query = "SELECT p.product_name, p.product_id, p.product_sku, "
                + "p.product_price, p.product_description, l.label_name, "
                + "p.product_quantity, c.category_name, p.image_url "
                + "FROM product p "
                + "JOIN category c ON p.category_id = c.category_id "
                + "JOIN label l ON c.label_id = l.label_id";
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

    // Tìm kiếm sản phẩm (đã bỏ phần tìm kiếm theo kho)
    public ResultSet getFilteredProducts(String productName, String productID, String price, String label) {
        String query = "SELECT p.product_name, p.product_id, p.product_sku, p.product_price, "
                + "p.product_description, p.product_quantity, p.image_url, l.label_name, c.category_name "
                + "FROM product p "
                + "JOIN category c ON p.category_id = c.category_id "
                + "JOIN label l ON c.label_id = l.label_id WHERE 1=1";

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

            return ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Cập nhật thông tin sản phẩm (không thay đổi kho)
    public boolean updateProduct(int productId, String productName, String productDescription, BigDecimal productPrice,
            int productQuantity, String labelName, String categoryName, String imageUrl) {
        // Lấy ID nhãn và danh mục
        int labelId = getLabelId(labelName);
        int categoryId = getCategoryId(categoryName);

        if (labelId == -1 || categoryId == -1) {
            return false; // Invalid label or category name
        }

        String query = "UPDATE product SET product_name = ?, product_description = ?, product_price = ?, "
                + "product_quantity = ?, category_id = ?, image_url = ? WHERE product_id = ?";

        try ( Connection conn = DBConnect.getConnection();  PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, productName);
            ps.setString(2, productDescription);
            ps.setBigDecimal(3, productPrice);
            ps.setInt(4, productQuantity);
            ps.setInt(5, categoryId);
            ps.setString(6, imageUrl);
            ps.setInt(7, productId);

            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Lấy id nhãn
    private int getLabelId(String labelName) {
        String query = "SELECT label_id FROM label WHERE label_name = ?";
        try ( Connection conn = DBConnect.getConnection();  PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, labelName);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("label_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    // Lấy id danh mục
    private int getCategoryId(String categoryName) {
        String query = "SELECT category_id FROM category WHERE category_name = ?";
        try ( Connection conn = DBConnect.getConnection();  PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, categoryName);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("category_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    // Xóa sản phẩm
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

}
