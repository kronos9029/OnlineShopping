package com.phatpt.springExercise.Entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_orderdetail")
public class OrderDetail implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String orderDetailId;
    private String orderId;
    private String productId;
    private int amount;
    private String feedback;
    private int star;
    
    public OrderDetail() {
        super();
    }

    public OrderDetail(String orderId, String productId, int amount, String feedback, int star) {
        this.orderId = orderId;
        this.productId = productId;
        this.amount = amount;
        this.feedback = feedback;
        this.star = star;
    }

    public String getOrderDetailID() {
        return orderDetailId;
    }

    public void setOrderDetailID(String orderDetailID) {
        this.orderDetailId = orderDetailID;
    }

    public String getOrderID() {
        return orderId;
    }

    public void setOrderID(String orderId) {
        this.orderId = orderId;
    }

    public String getProductID() {
        return productId;
    }

    public void setProductID(String productId) {
        this.productId = productId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    

    

}
