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
import Model.InventoryReport;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class InventoryReportDAO {

    public InventoryReport getInventoryReport(String warehouseCode, String productSku) throws Exception {
        Connection conn = DBConnect.getConnection();
        String sql = "SELECT * FROM inventory_report WHERE warehouse_code = ? AND product_sku = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, warehouseCode);
        ps.setString(2, productSku);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            InventoryReport report = new InventoryReport();
            report.setWarehouseCode(warehouseCode);
            report.setProductSku(productSku);
            report.setBeginningQuantity(rs.getInt("beginning_quantity"));
            report.setIncomingQuantity(rs.getInt("incoming_quantity"));
            report.setOutgoingQuantity(rs.getInt("outgoing_quantity"));
            report.setEndingQuantity(rs.getInt("ending_quantity"));
            return report;
        }
        return null;
    }

    public void updateInventoryReport(InventoryReport report) throws Exception {
        Connection conn = DBConnect.getConnection();
        String sql = "UPDATE inventory_report SET incoming_quantity = ?, ending_quantity = ? WHERE warehouse_code = ? AND product_sku = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, report.getIncomingQuantity());
        ps.setInt(2, report.getEndingQuantity());
        ps.setString(3, report.getWarehouseCode());
        ps.setString(4, report.getProductSku());
        ps.executeUpdate();
    }

    public void addInventoryReport(InventoryReport report) throws Exception {
        Connection conn = DBConnect.getConnection();
        String sql = "INSERT INTO inventory_report (warehouse_code, product_sku, beginning_quantity, incoming_quantity, outgoing_quantity, ending_quantity) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, report.getWarehouseCode());
        ps.setString(2, report.getProductSku());
        ps.setInt(3, report.getBeginningQuantity());
        ps.setInt(4, report.getIncomingQuantity());
        ps.setInt(5, report.getOutgoingQuantity());
        ps.setInt(6, report.getEndingQuantity());
        ps.executeUpdate();
    }
}
