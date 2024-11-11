/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Yin Kenna
 */
public class InventoryModel {

    private int inventoryId;
    private String inventoryCode;
    private int productId;
    private LocalDateTime periodDate;
    private int beginningQuantity;
    private int incomingQuantity;
    private int outgoingQuantity;
    private int endingQuantity;
    private LocalDateTime createAt;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public String getPeriodDateFormatted() {
        return periodDate != null ? periodDate.format(DATE_FORMATTER) : null;
    }

    public String getCreateAtFormatted() {
        return createAt != null ? createAt.format(DATE_FORMATTER) : null;
    }

    public InventoryModel() {
    }

    public int getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(int inventoryId) {
        this.inventoryId = inventoryId;
    }

    public String getInventoryCode() {
        return inventoryCode;
    }

    public void setInventoryCode(String inventoryCode) {
        this.inventoryCode = inventoryCode;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public LocalDateTime getPeriodDate() {
        return periodDate;
    }

    public void setPeriodDate(LocalDateTime periodDate) {
        this.periodDate = periodDate;
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

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

}
