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

    public ResultSet getAllLabels() {
        String query = "SELECT l.label_id, l.label_name, c.category_name FROM label l JOIN category c ON l.label_id = c.label_id";
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

    public void addLabel(String labelName, String categoryType) {
        if (labelName == null || labelName.trim().isEmpty() || categoryType == null || categoryType.trim().isEmpty()) {
            System.out.println("Label name or category type is empty. Aborting insertion.");
            return; // Ngừng thực hiện nếu đầu vào không hợp lệ
        }

        String insertLabelQuery = "INSERT INTO label (label_name) VALUES (?)";
        String insertCategoryQuery = "INSERT INTO category (label_id, category_name) VALUES (?, ?)";

        try ( Connection conn = DBConnect.getConnection()) {
            conn.setAutoCommit(false); // Bắt đầu giao dịch

            // Thêm nhãn vào bảng label
            try ( PreparedStatement psLabel = conn.prepareStatement(insertLabelQuery, Statement.RETURN_GENERATED_KEYS)) {
                psLabel.setString(1, labelName);
                psLabel.executeUpdate();

                // Lấy ID của nhãn vừa thêm
                ResultSet generatedKeys = psLabel.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int labelId = generatedKeys.getInt(1); // Lấy ID của nhãn

                    // Thêm danh mục vào bảng category
                    try ( PreparedStatement psCategory = conn.prepareStatement(insertCategoryQuery)) {
                        psCategory.setInt(1, labelId); // Đặt ID nhãn vào danh mục
                        psCategory.setString(2, categoryType); // Đặt tên danh mục
                        psCategory.executeUpdate();

                        conn.commit(); // Commit giao dịch nếu thành công
                    }
                }
            } catch (SQLException e) {
                conn.rollback(); // Rollback nếu có lỗi xảy ra
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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
        String query = "SELECT l.label_id, l.label_name, c.category_name FROM label l JOIN category c ON l.label_id = c.label_id WHERE 1=1";

        if (categoryName != null && !categoryName.isEmpty()) {
            query += " AND c.category_name = ?";
        }
        if (labelName != null && !labelName.isEmpty()) {
            query += " AND l.label_name LIKE ?";
        }

        try {
            Connection conn = DBConnect.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);

            int index = 1;
            if (categoryName != null && !categoryName.isEmpty()) {
                ps.setString(index++, categoryName);
            }
            if (labelName != null && !labelName.isEmpty()) {
                ps.setString(index++, "%" + labelName + "%");
            }

            return ps.executeQuery(); // Returning the ResultSet without closing the connection here
        } catch (SQLException e) {
            e.printStackTrace();
            return null; // Ensure you return null if there is an error
        }
    }

    // Phương thức lưu nhãn vào cơ sở dữ liệu
    public boolean updateLabel(int labelId, String category, String labelName) {
        String sql = "UPDATE label SET label_name = ? WHERE label_id = ?";
        String updateCategorySql = "UPDATE category SET category_name = ? WHERE label_id = ?";

        try ( Connection conn = DBConnect.getConnection()) {
            // Cập nhật bảng "label"
            try ( PreparedStatement psLabel = conn.prepareStatement(sql)) {
                psLabel.setString(1, labelName);
                psLabel.setInt(2, labelId);
                psLabel.executeUpdate();
            }

            // Cập nhật bảng "category"
            try ( PreparedStatement psCategory = conn.prepareStatement(updateCategorySql)) {
                psCategory.setString(1, category);
                psCategory.setInt(2, labelId);
                psCategory.executeUpdate();
            }

            return true;  // Nếu cả hai lệnh SQL đều thành công
        } catch (SQLException e) {
            e.printStackTrace();
            return false;  // Nếu xảy ra lỗi
        }
    }
    // Trong class LabelDAO.java

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

}
