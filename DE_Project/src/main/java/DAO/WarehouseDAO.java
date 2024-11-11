/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DB.DBConnect;
import Model.InventoryModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Qi
 */
public class WarehouseDAO {



    public List<InventoryModel> getInventoryList() {
        List<InventoryModel> result = new ArrayList<>();
        String query = "SELECT * FROM inventory_report";
        try ( Connection conn = DBConnect.getConnection();  PreparedStatement ps = conn.prepareStatement(query);  ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                InventoryModel inventoryModel = new InventoryModel();
                inventoryModel.setInventoryId(rs.getInt("inventory_id"));
                inventoryModel.setInventoryCode(rs.getString("inventory_code"));
                inventoryModel.setProductId(rs.getInt("product_id"));
                Timestamp periodDateTimestamp = rs.getTimestamp("period_date");
                if (periodDateTimestamp != null) {
                    LocalDateTime periodDate = periodDateTimestamp.toLocalDateTime();
                    inventoryModel.setPeriodDate(periodDate);
                }
                inventoryModel.setBeginningQuantity(rs.getInt("beginning_quantity"));
                inventoryModel.setIncomingQuantity(rs.getInt("incoming_quantity"));
                inventoryModel.setOutgoingQuantity(rs.getInt("outgoing_quantity"));
                inventoryModel.setEndingQuantity(rs.getInt("ending_quantity"));
                inventoryModel.setCreateAt(rs.getTimestamp("create_at").toLocalDateTime());

                result.add(inventoryModel);
            }
        } catch (SQLException ex) {
            System.out.println("getInventoryList: " + ex.getMessage());
        }
        return result;
    }
}
