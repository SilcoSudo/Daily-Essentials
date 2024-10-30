/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.LocationModel;
import Model.ProductModel;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Yin Kenna
 */
public class CartDAO {

    public void insertProductToCart(int userID, int productId, int quantity) {
        String query = "INSERT INTO cart (user_id, product_id, quantity, status) VALUES (?, ?, ?, ?)";

        try ( Connection conn = DB.DBConnect.getConnection();  PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, userID);
            ps.setInt(2, productId);
            ps.setInt(3, quantity);
            ps.setInt(4, 0);

            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("insertProductToCart :" + e);
        }
    }
    

    public boolean updateProductQuantity(int productId, int userId, boolean increase) {
        String updateQuery = "UPDATE cart SET quantity = quantity + ? WHERE product_id = ? AND user_id = ?";
        String deleteQuery = "DELETE FROM cart WHERE product_id = ? AND user_id = ? AND quantity = 0";
        String selectProductQuantityInWareQuery = "SELECT product_quantity FROM product WHERE product_id = ?";
        String selectProductQuantityInCartQuery = "SELECT quantity FROM cart WHERE product_id = ? AND user_id = ?";

        try ( Connection conn = DB.DBConnect.getConnection()) {
            int quantityChange = increase ? 1 : -1;

            // Kiểm tra số lượng trong kho và trong giỏ hàng của người dùng
            try ( PreparedStatement psSelectWare = conn.prepareStatement(selectProductQuantityInWareQuery);  PreparedStatement psSelectCart = conn.prepareStatement(selectProductQuantityInCartQuery)) {

                psSelectWare.setInt(1, productId);
                psSelectCart.setInt(1, productId);
                psSelectCart.setInt(2, userId);

                ResultSet rsWare = psSelectWare.executeQuery();
                ResultSet rsCart = psSelectCart.executeQuery();

                if (rsWare.next() && rsCart.next()) {
                    int productQuantityInWare = rsWare.getInt("product_quantity");
                    int productQuantityInCart = rsCart.getInt("quantity");

                    // Nếu đang tăng số lượng và tổng số lượng sẽ lớn hơn số lượng trong kho
                    if (increase && (productQuantityInCart + quantityChange) > productQuantityInWare) {
                        return false;
                    }
                }
            }

            // Thực hiện UPDATE
            try ( PreparedStatement psUpdate = conn.prepareStatement(updateQuery)) {
                psUpdate.setInt(1, quantityChange);
                psUpdate.setInt(2, productId);
                psUpdate.setInt(3, userId);
                psUpdate.executeUpdate();
            }

            // Thực hiện DELETE nếu quantity = 0
            if (!increase) {
                try ( PreparedStatement psDelete = conn.prepareStatement(deleteQuery)) {
                    psDelete.setInt(1, productId);
                    psDelete.setInt(2, userId);
                    psDelete.executeUpdate();
                }
            }
            return true;
        } catch (SQLException e) {
            System.out.println("updateProductQuantity: " + e);
            return false;
        }
    }

    public boolean updateQuantityCartInDetail(int userId, int productId, int quantity) {
        String query = "UPDATE cart SET quantity = ? WHERE status = 0 AND product_id = ? AND user_id = ?";
        boolean result = false;

        try ( Connection conn = DB.DBConnect.getConnection();  PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, quantity);
            ps.setInt(2, productId);
            ps.setInt(3, userId);

            result = ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("updateQuantityCartInDetail error: " + e.getMessage());
        }
        return result;
    }

    public int getProductQuantityInCart(int userId, int productId) {
        String query = "SELECT quantity FROM cart WHERE user_id = ? AND product_id = ? AND status = 0";
        try ( Connection conn = DB.DBConnect.getConnection();  PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, userId);
            ps.setInt(2, productId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("quantity");
            }
        } catch (SQLException e) {
            System.out.println("getProductQuantityInCart: " + e);
        }
        return 0;
    }

    public List<ProductModel> getAllProductCart(int userId) {
        List<ProductModel> productList = new ArrayList<>();
        String query = "SELECT p.image_url, p.product_name, p.product_price, c.quantity, c.product_id\n"
                + "FROM cart c\n"
                + "JOIN product p on c.product_id = p.product_id \n"
                + "WHERE c.status = 0  AND c.user_id = ?";

        try ( Connection conn = DB.DBConnect.getConnection();  PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, userId);  // Set the userId before executing the query
            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ProductModel product = new ProductModel();
                    product.setProductId(rs.getInt("product_id"));
                    product.setImageUrl(rs.getString("image_url"));
                    product.setProductName(rs.getString("product_name"));
                    product.setProductPrice(rs.getBigDecimal("product_price"));
                    product.setQuantityInCart(rs.getInt("quantity"));

                    productList.add(product);
                }
            }
        } catch (SQLException e) {
            System.out.println("getAllProductCart: " + e);
        }

        return productList;
    }

    public int getTotalCartItems(int userID) {
        int totalItems = 0;
        String query = "SELECT SUM(quantity) AS total FROM cart WHERE user_id = ? AND status = 0";

        try ( Connection conn = DB.DBConnect.getConnection();  PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, userID);

            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    totalItems = rs.getInt("total");
                }
            }
        } catch (SQLException e) {
            System.out.println("getTotalCartItems: " + e);
        }
        return totalItems;
    }

    public List<LocationModel> getLocation(int userID) {
        List<LocationModel> result = new ArrayList<>();
        String query = "SELECT la.province_name, la.district_name, la.ward_name\n"
                + "FROM location_area la WHERE la.user_id = ?";

        try ( Connection conn = DB.DBConnect.getConnection();  PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, userID);

            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String province = rs.getString("province_name");
                    String district = rs.getString("district_name");
                    String ward = rs.getString("ward_name");
                    LocationModel e = new LocationModel(province, district, ward);
                    result.add(e);
                }
            }
        } catch (SQLException e) {
            System.out.println("getTotalCartItems: " + e);
        }
        return result;
    }

    public boolean isHaveLocation(int userID) {
        boolean result = false;
        String query = "SELECT la.locationArea_id \n"
                + "FROM location_area la WHERE la.user_id = ?";
        try ( Connection conn = DB.DBConnect.getConnection();  PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, userID);

            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    result = true;
                }
            }
        } catch (SQLException e) {
            System.out.println("isHaveLocation: " + e);
        }
        return result;
    }

    public void insertLocation(int thanhPho, String thanhPho2, int quanHuyen, String quanHuyen2, int phuongXa, String phuongXa2, int userID) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DB.DBConnect.getConnection();

            String sql = "INSERT INTO location_area (user_id, province, province_name, district, district_name, ward, ward_name) VALUES (?, ?, ?, ?, ?, ?, ?)";

            ps = conn.prepareStatement(sql);
            ps.setInt(1, userID);
            ps.setInt(2, thanhPho);
            ps.setString(3, thanhPho2);
            ps.setInt(4, quanHuyen);
            ps.setString(5, quanHuyen2);
            ps.setInt(6, phuongXa);
            ps.setString(7, phuongXa2);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateLocation(int thanhPho, String thanhPho2, int quanHuyen, String quanHuyen2, int phuongXa, String phuongXa2, int userID) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DB.DBConnect.getConnection();

            String sql = "UPDATE location_area SET province = ?, province_name = ?, district = ?, district_name = ?, ward = ?, ward_name = ? WHERE user_id = ?";

            ps = conn.prepareStatement(sql);
            ps.setInt(1, thanhPho);
            ps.setString(2, thanhPho2);
            ps.setInt(3, quanHuyen);
            ps.setString(4, quanHuyen2);
            ps.setInt(5, phuongXa);
            ps.setString(6, phuongXa2);
            ps.setInt(7, userID);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<ProductModel> getProductInCart(int userID) {
        List<ProductModel> result = new ArrayList<>();
        String query = "SELECT p.product_id, p.product_name, p.image_url, c.quantity, p.product_price "
                + "FROM cart c "
                + "JOIN product p ON p.product_id = c.product_id "
                + "WHERE c.user_id = ? AND c.status = 0";

        try ( Connection conn = DB.DBConnect.getConnection();  PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, userID);

            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ProductModel e = new ProductModel();
                    e.setProductId(rs.getInt("product_id"));
                    e.setProductName(rs.getString("product_name"));
                    e.setImageUrl(rs.getString("image_url"));
                    e.setProductPrice(rs.getBigDecimal("product_price"));
                    e.setQuantityInCart(rs.getInt("quantity"));
                    result.add(e);
                }
            }
        } catch (SQLException e) {
            System.out.println("getProductInCart: " + e);
        }
        return result;
    }

    public LocationModel getUserAddress(int userId) {
        Connection conn;
        PreparedStatement ps;
        ResultSet rs;
        LocationModel userAddress = null;

        try {
            conn = DB.DBConnect.getConnection();
            String sql = "SELECT user_province, user_district, user_ward FROM user_profile WHERE user_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            rs = ps.executeQuery();

            if (rs.next()) {
                String province = rs.getString("user_province");
                String district = rs.getString("user_district");
                String ward = rs.getString("user_ward");
                userAddress = new LocationModel(province, district, ward);
            }

        } catch (SQLException e) {
            System.out.println("getUserAddress: " + e);
        }

        return userAddress;
    }

    public LocationModel getLocationAddress(int userId) {
        Connection conn;
        PreparedStatement ps;
        ResultSet rs;
        LocationModel locationAddress = null;

        try {
            conn = DB.DBConnect.getConnection();
            String sql = "SELECT province, district, ward FROM location_area WHERE user_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            rs = ps.executeQuery();

            if (rs.next()) {
                String province = rs.getString("province");
                String district = rs.getString("district");
                String ward = rs.getString("ward");
                locationAddress = new LocationModel(province, district, ward);
            }

        } catch (SQLException e) {
            System.out.println("getLocationAddress: " + e);
        }

        return locationAddress;
    }

    public void createNewOrder(int userId, BigDecimal totalAmount) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DB.DBConnect.getConnection();
            String sql = "INSERT INTO orders (user_id, order_date, total_amount, order_status) VALUES (?, ?, ?, 0)";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setTimestamp(2, new Timestamp(System.currentTimeMillis())); // Lấy thời gian hiện tại
            ps.setBigDecimal(3, totalAmount);

            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("createNewOrder: " + e);
        }
    }

    public boolean removeCart(int userId) {
        Connection conn;
        PreparedStatement ps;
        String query = "DELETE FROM cart WHERE status = 0 AND user_id = ?";
        try {
            conn = DB.DBConnect.getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, userId);

            if (ps.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("removeCart" + e);
        }
        return false;
    }

    public void insertInvoice(int orderId, BigDecimal feeShip, BigDecimal totalAmount, String invoiceStatus) {
        Connection conn = null;
        PreparedStatement psSelect = null;
        PreparedStatement psInsert = null;
        ResultSet rs = null;

        try {
            conn = DB.DBConnect.getConnection();

            // Lấy order_date mới nhất từ bảng orders
            String selectSql = "SELECT MAX(order_date) AS latest_order_date FROM orders WHERE order_id = ?";
            psSelect = conn.prepareStatement(selectSql);
            psSelect.setInt(1, orderId); // Sử dụng order_id hoặc user_id tùy theo yêu cầu của bạn
            rs = psSelect.executeQuery();

            Timestamp latestOrderDate = null;
            if (rs.next()) {
                latestOrderDate = rs.getTimestamp("latest_order_date");
            }

            String insertSql = "INSERT INTO invoices (order_id, invoice_date, fee_shipp, total_amount, invoice_status, create_at, update_at) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?)";
            psInsert = conn.prepareStatement(insertSql);
            psInsert.setInt(1, orderId);
            psInsert.setTimestamp(2, latestOrderDate); // Sử dụng order_date mới nhất
            psInsert.setBigDecimal(3, feeShip);
            psInsert.setBigDecimal(4, totalAmount);
            psInsert.setString(5, invoiceStatus);
            psInsert.setTimestamp(6, new Timestamp(System.currentTimeMillis())); // create_at
            psInsert.setTimestamp(7, new Timestamp(System.currentTimeMillis())); // update_at

            psInsert.executeUpdate();

        } catch (SQLException e) {
            System.out.println("insertInvoice: " + e);
        }
    }

}
