package com.phatpt.springExercise.Entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Entity
@Table(name = "tbl_product")
public class Product implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private long productId;

    @Column(name = "product_name")
    @NotBlank
    private String productName;

    @Column(name = "product_price")
    @NotNull
    @Positive
    private float productPrice;

    @Column(name = "image")
    @NotBlank
    private String image;

    @Column(name = "status")
    private String status;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "update_date")
    private Date updateDate;

    @Column(name = "update_Description")
    @NotBlank
    private String productDescription;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cate_id")
    private Category category;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "cart_quantity", nullable = true)
    private int cartQuantity;

    public Product() {
        super();
    }

    public Product(String productName, float productPrice, String image, Date createDate, Date updateDate,
            String productDescription, Category category, int quantity, String status) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.image = image;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.productDescription = productDescription;
        this.category = category;
        this.quantity = quantity;
        this.status = status;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public float getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(float productPrice) {
        this.productPrice = productPrice;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getCartQuantity() {
        return cartQuantity;
    }

    public void setCartQuantity(int cartQuantity) {
        this.cartQuantity = cartQuantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Product [category=" + category.getCateName() + ", createDate=" + createDate + ", image=" + image
                + ", productDescription=" + productDescription + ", productId=" + productId + ", productName="
                + productName + ", productPrice=" + productPrice + ", updateDate=" + updateDate + "]";
    }

    

}
