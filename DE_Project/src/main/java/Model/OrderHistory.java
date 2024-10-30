/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.math.BigDecimal;
import java.sql.Date;

/**
 *
 * @author nhatl
 */
public class OrderHistory {

    private int order_id;
    private int user_id;
    private Date order_date;
    private BigDecimal total_amount;
    private int order_status;
    private String orderStatusString;
    private BigDecimal fee_shipp;
    
    

    public OrderHistory(int order_id, int user_id, Date order_date, BigDecimal total_amount, int order_status) {
        this.order_id = order_id;
        this.user_id = user_id;
        this.order_date = order_date;
        this.total_amount = total_amount;
        this.order_status = order_status;
    }
    
    public OrderHistory(int order_id, int user_id, Date order_date, BigDecimal total_amount, int order_status, BigDecimal fee_shipp) {
        this.order_id = order_id;
        this.user_id = user_id;
        this.order_date = order_date;
        this.total_amount = total_amount;
        this.order_status = order_status;
        this.fee_shipp = fee_shipp;
    }

    public OrderHistory() {
    }

    public BigDecimal getFee_shipp() {
        return fee_shipp;
    }

    public void setFee_shipp(BigDecimal fee_shipp) {
        this.fee_shipp = fee_shipp;
    }

    public int getOrder_status() {
        return order_status;
    }

    public void setOrder_status(int order_status) {
        this.order_status = order_status;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public Date getOrder_date() {
        return order_date;
    }

    public void setOrder_date(Date order_date) {
        this.order_date = order_date;
    }

    public String getOrderStatusString() {
        return orderStatusString;
    }

    public void setOrderStatusString(String orderStatusString) {
        this.orderStatusString = orderStatusString;
    }

    @Override
    public String toString() {
        return "OrderHistory{"
                + "order_id=" + order_id
                + ", user_id=" + user_id
                + ", order_date=" + order_date
                + ", total_amount=" + total_amount
                + ", order_status=" + order_status
                + ", orderStatusString='" + orderStatusString
                + ", fee_shipp='" + fee_shipp + '\''
                + '}';
    }

    public BigDecimal getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(BigDecimal total_amount) {
        this.total_amount = total_amount;
    }
}
