/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.CategoryModel;
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
public class CategoryDAO {

    public List<ProductModel> searchProducts(String searchTerm) {
        List<ProductModel> productList = new ArrayList<>();

        // Tách chuỗi tìm kiếm thành các từ
        String[] searchTerms = searchTerm.split(" ");

        // Xây dựng câu truy vấn động
        StringBuilder sql = new StringBuilder("SELECT p.product_id, p.product_name, p.product_price, p.image_url, c.category_name, l.label_name ");
        sql.append("FROM product p ");
        sql.append("JOIN category c ON p.category_id = c.category_id ");
        sql.append("JOIN label l ON c.label_id = l.label_id ");
        sql.append("WHERE ");

        for (int i = 0; i < searchTerms.length; i++) {
            if (i > 0) {
                sql.append(" OR "); // Thêm OR giữa các từ
            }
            sql.append("(p.product_name COLLATE SQL_Latin1_General_CP1_CI_AI LIKE N'% ").append(searchTerms[i]).append(" %' ");
            sql.append("OR p.product_description COLLATE SQL_Latin1_General_CP1_CI_AI LIKE N'% ").append(searchTerms[i]).append(" %' ");
            sql.append("OR c.category_name COLLATE SQL_Latin1_General_CP1_CI_AI LIKE N'% ").append(searchTerms[i]).append(" %' ");
            sql.append("OR l.label_name COLLATE SQL_Latin1_General_CP1_CI_AI LIKE N'% ").append(searchTerms[i]).append(" %')");
        }

        try ( Connection connection = DB.DBConnect.getConnection();  PreparedStatement statement = connection.prepareStatement(sql.toString())) {

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                ProductModel product = new ProductModel();
                product.setProductId(rs.getInt("product_id"));
                product.setProductName(rs.getString("product_name"));
                product.setProductPrice(rs.getBigDecimal("product_price"));
                product.setImageUrl(rs.getString("image_url"));
                product.setCategoryName(rs.getString("category_name"));
                product.setLabelName(rs.getString("label_name"));

                productList.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }

    public List<CategoryModel> getCategoriesByLabelName(String labelName) {
        List<CategoryModel> categoryList = new ArrayList<>();
        String sql = "SELECT c.category_id, c.category_name "
                + "FROM category c "
                + "JOIN label l ON c.label_id = l.label_id "
                + "WHERE l.label_name = ?";

        try ( Connection connection = DB.DBConnect.getConnection();  PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, labelName);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                CategoryModel category = new CategoryModel();
                category.setCategoryId(rs.getInt("category_id"));
                category.setCategoryName(rs.getString("category_name"));

                categoryList.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categoryList;
    }
}
