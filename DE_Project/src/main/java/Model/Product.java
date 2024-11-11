/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Qi
 */
import java.math.BigDecimal;
import java.sql.Timestamp;

public class Product {

    private int productId;
    private String productName;
    private String productSku;
    private BigDecimal productPrice;
    private int productQuantity;
    private String imgUrl;
    private String productDescription;
    private int labelId;
    private String labelName;  // Thêm tên nhãn nếu cần hiển thị cùng với sản phẩm
    private String warehouseCode;
    private String warehouseName;  // Thêm tên kho nếu cần hiển thị cùng với sản phẩm
    private int categoryId;
    private Timestamp createdAt;  // Thêm thuộc tính createdAt

    // Constructor mặc định
    public Product() {
    }

    // Constructor có tham số
    public Product(int productId, String productName, String productSku, BigDecimal productPrice, int productQuantity, String imgUrl,
            String productDescription, int labelId, String labelName, String warehouseCode, String warehouseName, int categoryId, Timestamp createdAt) {
        this.productId = productId;
        this.productName = productName;
        this.productSku = productSku;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
        this.imgUrl = imgUrl;
        this.productDescription = productDescription;
        this.labelId = labelId;
        this.labelName = labelName;
        this.warehouseCode = warehouseCode;
        this.warehouseName = warehouseName;
        this.categoryId = categoryId;
        this.createdAt = createdAt;
    }

    // Getters và Setters cho tất cả các thuộc tính
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductSku() {
        return productSku;
    }

    public void setProductSku(String productSku) {
        this.productSku = productSku;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public int getLabelId() {
        return labelId;
    }

    public void setLabelId(int labelId) {
        this.labelId = labelId;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

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

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
