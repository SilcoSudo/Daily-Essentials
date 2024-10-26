/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Qi
 */

public class Warehouse {
    private String warehouseCode;
    private String warehouseName;
    private String warehouseAddress;
    private int warehouseCapacity;
    private String warehouseType;
    private String warehouseStatus;

    // Constructor
    public Warehouse(String warehouseCode, String warehouseName, String warehouseAddress, int warehouseCapacity, String warehouseType, String warehouseStatus) {
        this.warehouseCode = warehouseCode;
        this.warehouseName = warehouseName;
        this.warehouseAddress = warehouseAddress;
        this.warehouseCapacity = warehouseCapacity;
        this.warehouseType = warehouseType;
        this.warehouseStatus = warehouseStatus;
    }

    // Getters and Setters
    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getWarehouseAddress() {
        return warehouseAddress;
    }

    public void setWarehouseAddress(String warehouseAddress) {
        this.warehouseAddress = warehouseAddress;
    }

    public int getWarehouseCapacity() {
        return warehouseCapacity;
    }

    public void setWarehouseCapacity(int warehouseCapacity) {
        this.warehouseCapacity = warehouseCapacity;
    }

    public String getWarehouseType() {
        return warehouseType;
    }

    public void setWarehouseType(String warehouseType) {
        this.warehouseType = warehouseType;
    }

    public String getWarehouseStatus() {
        return warehouseStatus;
    }

    public void setWarehouseStatus(String warehouseStatus) {
        this.warehouseStatus = warehouseStatus;
    }
}
