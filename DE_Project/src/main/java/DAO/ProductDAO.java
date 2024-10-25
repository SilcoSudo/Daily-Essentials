package DAO;

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

    Connection conn;
    ResultSet rs;
    PreparedStatement ps;

    public List<ProductModel> getListProductMax15Item(int userId) {
        List<ProductModel> products = new ArrayList<>();
        String query = "SELECT TOP 15 product_id, product_name, product_price, product_sku, product_quantity, image_url, product_description, category_id\n"
                + "FROM product ORDER BY NEWID()";

        try {
            conn = DB.DBConnect.getConnection();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
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

}
