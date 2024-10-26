/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DB.DBConnect;
import Model.Warehouse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Qi
 */
public class WarehouseDAO {

    public ResultSet getAllWarehouses() {
        ResultSet rs = null;
        try {
            Connection conn = DBConnect.getConnection();
            String query = "SELECT * FROM warehouse";
            PreparedStatement ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    // Thêm kho hàng mới
    public boolean addWarehouse(Warehouse warehouse) {
        try ( Connection conn = DBConnect.getConnection();  PreparedStatement pst = conn.prepareStatement("INSERT INTO warehouse(warehouse_code, warehouse_name, warehouse_address, warehouse_capacity, warehouse_type, warehouse_status) VALUES (?, ?, ?, ?, ?, ?)")) {

            pst.setString(1, warehouse.getWarehouseCode());
            pst.setString(2, warehouse.getWarehouseName());
            pst.setString(3, warehouse.getWarehouseAddress());
            pst.setInt(4, warehouse.getWarehouseCapacity());
            pst.setString(5, warehouse.getWarehouseType());
            pst.setString(6, warehouse.getWarehouseStatus());

            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Lấy thông tin kho hàng theo mã kho
    public Warehouse getWarehouseByCode(String warehouseCode) {
        try ( Connection conn = DBConnect.getConnection();  PreparedStatement pst = conn.prepareStatement("SELECT * FROM warehouse WHERE warehouse_code=?")) {

            pst.setString(1, warehouseCode);
            try ( ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return new Warehouse(
                            rs.getString("warehouse_code"),
                            rs.getString("warehouse_name"),
                            rs.getString("warehouse_address"),
                            rs.getInt("warehouse_capacity"),
                            rs.getString("warehouse_type"),
                            rs.getString("warehouse_status")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Cập nhật thông tin kho hàng
    public boolean updateWarehouse(Warehouse warehouse) {
        try ( Connection conn = DBConnect.getConnection();  PreparedStatement pst = conn.prepareStatement("UPDATE warehouse SET warehouse_name=?, warehouse_address=?, warehouse_capacity=?, warehouse_type=?, warehouse_status=? WHERE warehouse_code=?")) {

            pst.setString(1, warehouse.getWarehouseName());
            pst.setString(2, warehouse.getWarehouseAddress());
            pst.setInt(3, warehouse.getWarehouseCapacity());
            pst.setString(4, warehouse.getWarehouseType());
            pst.setString(5, warehouse.getWarehouseStatus());
            pst.setString(6, warehouse.getWarehouseCode());

            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Xóa kho hàng theo mã kho
    public boolean deleteWarehouse(String warehouseCode) {
        try ( Connection conn = DBConnect.getConnection();  PreparedStatement pst = conn.prepareStatement("DELETE FROM warehouse WHERE warehouse_code=?")) {

            pst.setString(1, warehouseCode);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Trong WarehouseDAO.java
    public ResultSet getWarehousesByCriteria(String warehouseName, String warehouseCode, String warehouseAddress, String warehouseStatus) {
        ResultSet rs = null;
        try {
            Connection conn = DBConnect.getConnection();
            String query = "SELECT * FROM warehouse WHERE 1=1"; // Điều kiện luôn đúng
            if (warehouseName != null && !warehouseName.isEmpty()) {
                query += " AND warehouse_name LIKE ?";
            }
            if (warehouseCode != null && !warehouseCode.isEmpty()) {
                query += " AND warehouse_code LIKE ?";
            }
            if (warehouseAddress != null && !warehouseAddress.isEmpty()) {
                query += " AND warehouse_address LIKE ?";
            }
            if (warehouseStatus != null && !warehouseStatus.isEmpty()) {
                query += " AND warehouse_status LIKE ?";
            }
            PreparedStatement ps = conn.prepareStatement(query);
            int index = 1;
            if (warehouseName != null && !warehouseName.isEmpty()) {
                ps.setString(index++, "%" + warehouseName + "%");
            }
            if (warehouseCode != null && !warehouseCode.isEmpty()) {
                ps.setString(index++, "%" + warehouseCode + "%");
            }
            if (warehouseAddress != null && !warehouseAddress.isEmpty()) {
                ps.setString(index++, "%" + warehouseAddress + "%");
            }
            if (warehouseStatus != null && !warehouseStatus.isEmpty()) {
                ps.setString(index++, "%" + warehouseStatus + "%");
            }
            rs = ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }
}
