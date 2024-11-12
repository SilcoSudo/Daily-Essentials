/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

/**
 *
 * @author Qi
 */
import DB.DBConnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LabelDAO {

    Connection conn = DBConnect.getConnection();

    // hiển thị danh sách label
    public ResultSet getAllLabels() {
        String query = "SELECT l.label_id, l.label_name, c.category_name FROM label l JOIN category c ON l.label_id = c.label_id";
        ResultSet rs = null;
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    // kiểm tra label có không
    public boolean isLabelExists(String labelName) throws SQLException {
        String query = "SELECT COUNT(*) FROM label WHERE label_name = ?";
        try ( PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, labelName);
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    return true;
                }
            }
        }
        return false;
    }

    // lấy danh mục để hiển thị ra phàn chọn danh mục 
    public ResultSet getCategories() {
        String query = "SELECT category_name FROM category";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBConnect.getConnection(); // Mở kết nối
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs; // Trả về ResultSet
    }

    // lấy nhãn để hiển thị ra phàn chọn nhãn
    public ResultSet getLabels() {
        String query = "SELECT DISTINCT label_name FROM label";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBConnect.getConnection(); // Mở kết nối
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs; // Trả về ResultSet
    }

    // tìm dựa trên cat và label
    public ResultSet getFilteredLabels(String categoryName, String labelName) {
        // Prepare the base query with JOIN
        String query = "SELECT l.label_id, l.label_name, c.category_name "
                + "FROM label l JOIN category c ON l.label_id = c.label_id WHERE 1=1";

        // Append conditions based on provided filters
        if (categoryName != null && !categoryName.isEmpty()) {
            query += " AND c.category_name LIKE ?"; // Use LIKE for partial matching on category name
        }
        if (labelName != null && !labelName.isEmpty()) {
            query += " AND l.label_name LIKE ?"; // Use LIKE for partial matching on label name
        }

        try {
            // Establish the database connection
            Connection conn = DBConnect.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);

            // Bind the parameters with "%" for partial match
            int index = 1;
            if (categoryName != null && !categoryName.isEmpty()) {
                ps.setString(index++, "%" + categoryName + "%"); // Wrap with % for partial match
            }
            if (labelName != null && !labelName.isEmpty()) {
                ps.setString(index++, "%" + labelName + "%"); // Wrap with % for partial match
            }

            // Execute and return the result set
            return ps.executeQuery(); // Returning the ResultSet without closing the connection here
        } catch (SQLException e) {
            e.printStackTrace();
            return null; // Return null in case of error
        }
    }

    // Phương thức lưu nhãn vào cơ sở dữ liệu
    public boolean updateLabel(int labelId, String category, String labelName) {
        String sql = "UPDATE label SET label_name = ? WHERE label_id = ?";
        String updateCategorySql
                = "UPDATE category SET category_name = ? WHERE category_id = "
                + "(SELECT TOP 1 category_id FROM category WHERE label_id = ?)";

        try ( Connection conn = DBConnect.getConnection()) {
            conn.setAutoCommit(false); // Begin transaction

            // Update label name in the "label" table
            try ( PreparedStatement psLabel = conn.prepareStatement(sql)) {
                psLabel.setString(1, labelName);
                psLabel.setInt(2, labelId);
                psLabel.executeUpdate();
            }

            // Update category name in the "category" table for the matching label
            try ( PreparedStatement psCategory = conn.prepareStatement(updateCategorySql)) {
                psCategory.setString(1, category);
                psCategory.setInt(2, labelId);
                psCategory.executeUpdate();
            }

            conn.commit(); // Commit transaction
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;  // Return false if an error occurs
        }
    }

    // đang test tính năng cái gì quên ròi :)))
    public ResultSet getLabelsAndCategories() {
        String query = "SELECT l.label_name, c.category_name "
                + "FROM label l "
                + "JOIN category c ON l.label_id = c.label_id";
        try ( Connection conn = DBConnect.getConnection();  PreparedStatement ps = conn.prepareStatement(query)) {
            return ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
// Thêm phương thức kiểm tra nếu danh mục đã tồn tại

    public boolean categoryExists(String categoryName) {
        String query = "SELECT COUNT(*) FROM category WHERE category_name = ?";
        try ( PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, categoryName);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

// Thêm danh mục mới vào bảng category
    public int addCategory(String categoryName) {
        String query = "INSERT INTO category (category_name) VALUES (?)";
        try ( PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, categoryName);
            ps.executeUpdate();
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1); // Lấy ID của danh mục mới thêm
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

// Cập nhật phương thức thêm nhãn (Label)
    public void addLabel(String labelName, String categoryType) {
        if (labelName == null || labelName.trim().isEmpty() || categoryType == null || categoryType.trim().isEmpty()) {
            System.out.println("Label name or category type is empty. Aborting insertion.");
            return;
        }

        int labelId = -1;
        int categoryId = -1;

        // Check if the label already exists and retrieve its label_id
        String checkLabelQuery = "SELECT label_id FROM label WHERE label_name = ?";
        try ( PreparedStatement psCheckLabel = conn.prepareStatement(checkLabelQuery)) {
            psCheckLabel.setString(1, labelName);
            ResultSet rsLabel = psCheckLabel.executeQuery();
            if (rsLabel.next()) {
                labelId = rsLabel.getInt("label_id");
            } else {
                // Insert new label and retrieve the new label_id
                String insertLabelQuery = "INSERT INTO label (label_name) VALUES (?)";
                try ( PreparedStatement psInsertLabel = conn.prepareStatement(insertLabelQuery, Statement.RETURN_GENERATED_KEYS)) {
                    psInsertLabel.setString(1, labelName);
                    psInsertLabel.executeUpdate();
                    ResultSet generatedKeys = psInsertLabel.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        labelId = generatedKeys.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // If labelId was not obtained, abort insertion
        if (labelId == -1) {
            System.out.println("Label ID could not be determined. Aborting insertion.");
            return;
        }

        // Check if the category already exists and retrieve its category_id
        String checkCategoryQuery = "SELECT category_id FROM category WHERE category_name = ?";
        try ( PreparedStatement psCheckCategory = conn.prepareStatement(checkCategoryQuery)) {
            psCheckCategory.setString(1, categoryType);
            ResultSet rsCategory = psCheckCategory.executeQuery();
            if (rsCategory.next()) {
                categoryId = rsCategory.getInt("category_id");
            } else {
                // Insert new category with the determined label_id
                String insertCategoryQuery = "INSERT INTO category (label_id, category_name) VALUES (?, ?)";
                try ( PreparedStatement psInsertCategory = conn.prepareStatement(insertCategoryQuery, Statement.RETURN_GENERATED_KEYS)) {
                    psInsertCategory.setInt(1, labelId);
                    psInsertCategory.setString(2, categoryType);
                    psInsertCategory.executeUpdate();
                    ResultSet generatedKeys = psInsertCategory.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        categoryId = generatedKeys.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("Label and category added successfully with Label ID: " + labelId + " and Category ID: " + categoryId);
    }
}
