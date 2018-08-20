package com.tjx.pojo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tjx.util.JsonDateSerializer;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class Sale {

    private Integer id;
    private double price;
    private int quantity;
    private double totalPrice;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using=JsonDateSerializer.class)
    private Date saleDate;
    private Integer userId;
    private Integer productId;
    private Product product;
    private User user;
    public Sale() {
    }

    public Sale(Integer id, double price, int quantity, double totalPrice, Date saleDate, Integer userId, Integer productId) {
        this.id = id;
        this.price = price;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.saleDate = saleDate;
        this.userId = userId;
        this.productId = productId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return price*quantity;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(Date saleDate) {
        this.saleDate = saleDate;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    @Override
    public String toString() {
        return "Sale{" +
                "id=" + id +
                ", price=" + price +
                ", quantity=" + quantity +
                ", totalPrice=" + totalPrice +
                ", saleDate=" + saleDate +
                ", userId=" + userId +
                ", productId=" + productId +
                ", product=" + product +
                ", user=" + user +
                '}';
    }
}
