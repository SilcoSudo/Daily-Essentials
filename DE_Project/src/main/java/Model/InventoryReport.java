/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Qi
 */
public class InventoryReport {

    private String warehouseCode;
    private String productSku;
    private int beginningQuantity;
    private int incomingQuantity;
    private int outgoingQuantity;
    private int endingQuantity;

    public InventoryReport() {
    }

    public InventoryReport(String warehouseCode, String productSku, int beginningQuantity, int incomingQuantity, int outgoingQuantity, int endingQuantity) {
        this.warehouseCode = warehouseCode;
        this.productSku = productSku;
        this.beginningQuantity = beginningQuantity;
        this.incomingQuantity = incomingQuantity;
        this.outgoingQuantity = outgoingQuantity;
        this.endingQuantity = endingQuantity;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getProductSku() {
        return productSku;
    }

    public void setProductSku(String productSku) {
        this.productSku = productSku;
    }

    public int getBeginningQuantity() {
        return beginningQuantity;
    }

    public void setBeginningQuantity(int beginningQuantity) {
        this.beginningQuantity = beginningQuantity;
    }

    public int getIncomingQuantity() {
        return incomingQuantity;
    }

    public void setIncomingQuantity(int incomingQuantity) {
        this.incomingQuantity = incomingQuantity;
    }

    public int getOutgoingQuantity() {
        return outgoingQuantity;
    }

    public void setOutgoingQuantity(int outgoingQuantity) {
        this.outgoingQuantity = outgoingQuantity;
    }

    public int getEndingQuantity() {
        return endingQuantity;
    }

    public void setEndingQuantity(int endingQuantity) {
        this.endingQuantity = endingQuantity;
    }

}
