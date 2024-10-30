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

    // thêm label mới với điều kiện nếu danh mục đã có thì chỉ thêm label còn nếu không thì thêm nhanx lẫn label
    public void addLabel(String labelName, String categoryType) {
        if (labelName == null || labelName.trim().isEmpty() || categoryType == null || categoryType.trim().isEmpty()) {
            System.out.println("Label name or category type is empty. Aborting insertion.");
            return; // Ngừng thực hiện nếu đầu vào không hợp lệ
        }

        String checkCategoryQuery = "SELECT category_id FROM category WHERE category_name = ?";
        String insertLabelQuery = "INSERT INTO label (label_name) VALUES (?)";
        String insertCategoryQuery = "INSERT INTO category (label_id, category_name) VALUES (?, ?)";
        String updateLabelCategoryLinkQuery = "UPDATE label SET category_id = ? WHERE label_id = ?";

        try ( Connection conn = DBConnect.getConnection()) {
            conn.setAutoCommit(false); // Bắt đầu giao dịch

            int categoryId = -1;

            // Kiểm tra xem danh mục đã tồn tại chưa
            try ( PreparedStatement psCheckCategory = conn.prepareStatement(checkCategoryQuery)) {
                psCheckCategory.setString(1, categoryType);
                ResultSet rsCategory = psCheckCategory.executeQuery();
                if (rsCategory.next()) {
                    categoryId = rsCategory.getInt("category_id"); // Lấy ID của danh mục đã tồn tại
                } else {
                    System.out.println("Category does not exist. Please add the category first.");
                    return; // Dừng nếu danh mục không tồn tại
                }
            }

            // Thêm nhãn mới vào bảng label
            int labelId = -1;
            try ( PreparedStatement psLabel = conn.prepareStatement(insertLabelQuery, Statement.RETURN_GENERATED_KEYS)) {
                psLabel.setString(1, labelName);
                psLabel.executeUpdate();

                ResultSet generatedKeys = psLabel.getGeneratedKeys();
                if (generatedKeys.next()) {
                    labelId = generatedKeys.getInt(1); // Lấy ID của nhãn mới thêm
                }
            }

            // Liên kết nhãn mới với danh mục đã tồn tại
            if (labelId != -1 && categoryId != -1) {
                try ( PreparedStatement psUpdateLink = conn.prepareStatement(updateLabelCategoryLinkQuery)) {
                    psUpdateLink.setInt(1, categoryId); // Đặt ID danh mục
                    psUpdateLink.setInt(2, labelId); // Đặt ID của nhãn
                    psUpdateLink.executeUpdate();
                    conn.commit(); // Commit giao dịch nếu thành công
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

}
