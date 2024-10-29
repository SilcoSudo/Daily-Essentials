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
            sql.append("(p.product_name COLLATE SQL_Latin1_General_CP1_CI_AI LIKE N'%").append(searchTerms[i]).append("%' ");
            sql.append("OR p.product_description COLLATE SQL_Latin1_General_CP1_CI_AI LIKE N'%").append(searchTerms[i]).append("%' ");
            sql.append("OR c.category_name COLLATE SQL_Latin1_General_CP1_CI_AI LIKE N'%").append(searchTerms[i]).append("%' ");
            sql.append("OR l.label_name COLLATE SQL_Latin1_General_CP1_CI_AI LIKE N'%").append(searchTerms[i]).append("%')");

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
            System.out.println("searchProducts: " + e);
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

    public List<ProductModel> getProductByCategoryID(int categoryID) {
        List<ProductModel> result = new ArrayList<>();
        String query = "SELECT p.product_id, p.product_name, p.product_price, p.image_url, c.category_name, l.label_name \n"
                + "FROM product p\n"
                + "JOIN category c ON p.category_id = c.category_id \n"
                + "JOIN label l ON c.label_id = l.label_id \n"
                + "WHERE c.category_id = ?";
        try ( Connection connection = DB.DBConnect.getConnection();  PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, categoryID);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                ProductModel product = new ProductModel();

                product.setProductId(rs.getInt("product_id"));
                product.setProductName(rs.getString("product_name"));
                product.setProductPrice(rs.getBigDecimal("product_price"));
                product.setImageUrl(rs.getString("image_url"));
                product.setCategoryName(rs.getString("category_name"));
                product.setLabelName(rs.getString("label_name"));

                result.add(product);
            }
        } catch (SQLException e) {
            System.out.println("getProductByCategoryID: " + e);
        }
        return result;
    }

    public List<CategoryModel> getFullLabel() {
        List<CategoryModel> result = new ArrayList<>();
        String query = "SELECT label_id, label_name\n"
                + "FROM label";
        try ( Connection connection = DB.DBConnect.getConnection();  PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                CategoryModel categoryModel = new CategoryModel();

                categoryModel.setLabelId(rs.getInt("label_id"));
                categoryModel.setCategoryName(rs.getString("label_name"));
                result.add(categoryModel);
            }

        } catch (SQLException e) {
            System.out.println("getFullLabel: " + e);
        }
        return result;
    }

    public List<ProductModel> getProductInCartWhenSearch(List<ProductModel> product_id, int user_id) {
        List<ProductModel> result = new ArrayList<>();
        StringBuilder query = new StringBuilder("SELECT p.product_id, c.quantity ");
        query.append("FROM product p ");
        query.append("JOIN cart c ON c.product_id = p.product_id ");
        query.append("JOIN user_profile up ON up.user_id = c.user_id ");
        if (user_id != 0) {
            query.append("WHERE c.user_id = ? AND ");
        } else {
            query.append("WHERE");
        }
        query.append("p.product_id IN (");
        for (int i = 0; i < product_id.size(); i++) {
            query.append("?");
            if (i < product_id.size() - 1) {
                query.append(", ");
            }
        }
        query.append(")");

        try ( Connection connection = DB.DBConnect.getConnection();  PreparedStatement statement = connection.prepareStatement(query.toString())) {

            statement.setInt(1, user_id);

            for (int i = 0; i < product_id.size(); i++) {
                statement.setInt(i + 2, product_id.get(i).getProductId());
            }

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                ProductModel e = new ProductModel();
                e.setProductId(rs.getInt("product_id"));
                e.setQuantityInCart(rs.getInt("quantity"));
                result.add(e);
            }
        } catch (SQLException e) {
            System.out.println("getProductsInCart: " + e);
        }
        return result;
    }
}
