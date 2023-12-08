package com.androidafe.dobazar.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Wishes {

    @PrimaryKey(autoGenerate = true)
    private int pid;


    @ColumnInfo(name = "productName")
    private String productName;

    @ColumnInfo(name = "productPrice")
    private int productPrice;


    @ColumnInfo(name = "productImage")
    private String productImage;

    public Wishes(String productName, int productPrice, String productImage) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productImage = productImage;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }
}
