package DAO;

import DB.DBConnect;
import Model.Product;
import Model.ProductModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Yin Kenna
 */
public class ProductDAO {

    public int getQuantityProduct(int productId) {
        String query = "SELECT product_quantity FROM product WHERE product_id = ?";
        int quantity = 0;
        try ( Connection conn = DB.DBConnect.getConnection();  PreparedStatement ps = conn.prepareStatement(query)) {

            // Set the productId parameter
            ps.setInt(1, productId);

            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    quantity = rs.getInt("product_quantity");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving product quantity: " + e);
        }
        return quantity;
    }

    public List<ProductModel> getListProductMax15Item(int userId) {
        List<ProductModel> products = new ArrayList<>();
        String query = "SELECT TOP 15 product_id, product_name, product_price, product_sku, product_quantity, image_url, product_description, category_id\n"
                + "FROM product ORDER BY NEWID()";

        try {
            Connection conn = DB.DBConnect.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            CartDAO cartDAO;
            int quantityInCart;
            ProductModel product;
            int productQuantity;

            while (rs.next()) {
                productQuantity = rs.getInt("product_quantity");

                if (productQuantity <= 0) {
                    continue;
                }

                product = new ProductModel();
                product.setProductId(rs.getInt("product_id"));
                product.setProductName(rs.getString("product_name"));
                product.setProductPrice(rs.getBigDecimal("product_price"));
                product.setProductSku(rs.getString("product_sku"));
                product.setProductQuantity(productQuantity);
                product.setImageUrl(rs.getString("image_url"));
                product.setProductDescription(rs.getString("product_description"));
                product.setCategoryId(rs.getInt("category_id"));

                cartDAO = new CartDAO();
                quantityInCart = cartDAO.getProductQuantityInCart(userId, product.getProductId());
                product.setQuantityInCart(quantityInCart);

                products.add(product);
            }
        } catch (SQLException e) {
            System.out.println("List product: " + e);
        }

        return products;
    }

    public List<ProductModel> getRemainingProducts(int offset) {
        List<ProductModel> products = new ArrayList<>();
        String query = "SELECT product_id, product_name, product_price, product_sku, product_quantity, image_url, product_description, category_id\n"
                + "FROM product ORDER BY product_id OFFSET ? ROWS FETCH NEXT 15 ROWS ONLY";

        try ( Connection conn = DB.DBConnect.getConnection();  PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, offset);
            int quantityInCart = 0;
            ProductModel product;
            int productQuantity;
            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    productQuantity = rs.getInt("product_quantity");

                    if (productQuantity <= 0) {
                        continue;
                    }

                    product = new ProductModel();
                    product.setProductId(rs.getInt("product_id"));
                    product.setProductName(rs.getString("product_name"));
                    product.setProductPrice(rs.getBigDecimal("product_price"));
                    product.setProductSku(rs.getString("product_sku"));
                    product.setProductQuantity(quantityInCart);
                    product.setImageUrl(rs.getString("image_url"));
                    product.setProductDescription(rs.getString("product_description"));
                    product.setCategoryId(rs.getInt("category_id"));
                    products.add(product);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching products: " + e.getMessage());
        }
        return products;
    }

    public List<ProductModel> getProductDetails(int product_id) {
        List<ProductModel> result = new ArrayList<>();
        String query = "SELECT product_id, product_name, product_description, image_url, product_quantity, product_price\n"
                + "FROM product WHERE product_id = ?";
        try ( Connection conn = DB.DBConnect.getConnection();  PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, product_id);
            ProductModel product = new ProductModel();
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    product.setProductId(rs.getInt("product_id"));
                    product.setProductName(rs.getString("product_name"));
                    product.setProductDescription(rs.getString("product_description"));
                    product.setImageUrl(rs.getString("image_url"));
                    product.setProductQuantity(rs.getInt("product_quantity"));
                    product.setProductPrice(rs.getBigDecimal("product_price"));
                    result.add(product);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching products: " + e.getMessage());
        }
        return result;
    }

    public int getQuantityInWare(int product_id) {
        int result = 0;
        String query = "SELECT p.product_quantity FROM product p WHERE p.product_id = ?;";
        try ( Connection conn = DB.DBConnect.getConnection();  PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, product_id);
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    result = rs.getInt("product_quantity");
                }
            }
        } catch (SQLException e) {
            System.out.println("getQuantityInWare: " + e.getMessage());
        }
        return result;
    }

    public int getQuantityRemain(int userId, int product_id) {
        int result = 0;
        String query = "SELECT quantity \n"
                + "FROM cart WHERE user_id = ? AND product_id = ? AND status = 0";
        try ( Connection conn = DB.DBConnect.getConnection();  PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, userId);
            ps.setInt(2, product_id);
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    result = rs.getInt("quantity");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching products: " + e.getMessage());
        }
        return result;
    }

    public boolean addProduct(Product product) throws SQLException {
        String sql = "INSERT INTO product (product_name, product_sku, product_price, product_quantity, product_description, image_url, category_id, created_at) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try ( Connection conn = DBConnect.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, product.getProductName());
            ps.setString(2, product.getProductSku());
            ps.setBigDecimal(3, product.getProductPrice());
            ps.setInt(4, product.getProductQuantity());
            ps.setString(5, product.getProductDescription());
            ps.setString(6, product.getImgUrl());
            ps.setInt(7, product.getCategoryId());
            ps.setTimestamp(8, product.getCreatedAt()); // Store createdAt in the database

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        }
    }
}
