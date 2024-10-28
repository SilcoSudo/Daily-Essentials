/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author nhatl
 */
public class ProductStatistics {

    private int product_id;
    private String product_name;
    private String image_url;

    private int category_id;
    private String category_name;

    private int order_status;
    private int orderstatus_count;
    private String orderStatusString;

    private int quantitySold;
    private double revenue;

    private double total_revenue;

    public ProductStatistics(double total_revenue) {
        this.total_revenue = total_revenue;
    }

    public double getTotal_revenue() {
        return total_revenue;
    }

    public void setTotal_revenue(double total_revenue) {
        this.total_revenue = total_revenue;
    }

    public ProductStatistics(int product_id, String product_name, String category_name, int quantitySold, double revenue) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.category_name = category_name;
        this.quantitySold = quantitySold;
        this.revenue = revenue;
    }

    public int getQuantitySold() {
        return quantitySold;
    }

    public void setQuantitySold(int quantitySold) {
        this.quantitySold = quantitySold;
    }

    public double getRevenue() {
        return revenue;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }

    public ProductStatistics(int order_status, int orderstatus_count) {
        this.order_status = order_status;
        this.orderstatus_count = orderstatus_count;
    }

    public int getOrder_status() {
        return order_status;
    }

    public void setOrder_status(int order_status) {
        this.order_status = order_status;
    }

    public int getOrderstatus_count() {
        return orderstatus_count;
    }

    public void setOrderstatus_count(int orderstatus_count) {
        this.orderstatus_count = orderstatus_count;
    }

    public String getOrderStatusString() {
        return orderStatusString;
    }

    public void setOrderStatusString(String orderStatusString) {
        this.orderStatusString = orderStatusString;
    }

    public ProductStatistics() {
    }

    public ProductStatistics(int category_id, String category_name) {
        this.category_id = category_id;
        this.category_name = category_name;
    }

    public ProductStatistics(int product_id, String product_name, String image_url, int category_id, String category_name) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.image_url = image_url;
        this.category_id = category_id;
        this.category_name = category_name;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    @Override
    public String toString() {
        return "ProductStatistics{" + "product_id=" + product_id + ", product_name=" + product_name + ", image_url=" + image_url + ", category_id=" + category_id + ", category_name=" + category_name + ", order_status=" + order_status + ", orderstatus_count=" + orderstatus_count + ", orderStatusString=" + orderStatusString + '}';
    }

}
