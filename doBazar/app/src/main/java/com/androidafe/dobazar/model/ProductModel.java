package com.androidafe.dobazar.model;

public class ProductModel {

    private int id;
    private int cat_id;
    private String pName;
    private String pDesc;
    private String pImage;
    private int pPrice;
    private boolean pStatus;

    public ProductModel(int id, int catId, String pName, String pDesc, String pImage, int pPrice, boolean pStatus) {
        this.id = id;
        this.cat_id = catId;
        this.pName = pName;
        this.pDesc = pDesc;
        this.pImage = pImage;
        this.pPrice = pPrice;
        this.pStatus = pStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCatId() {
        return cat_id;
    }

    public void setCatId(int catId) {
        this.cat_id = catId;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getpDesc() {
        return pDesc;
    }

    public void setpDesc(String pDesc) {
        this.pDesc = pDesc;
    }

    public String getpImage() {
        return "https://devtutor.me/admin/panel/product/uploads/"+pImage;
    }

    public void setpImage(String pImage) {
        this.pImage = pImage;
    }

    public int getpPrice() {
        return pPrice;
    }

    public void setpPrice(int pPrice) {
        this.pPrice = pPrice;
    }

    public boolean ispStatus() {
        return pStatus;
    }

    public void setpStatus(boolean pStatus) {
        this.pStatus = pStatus;
    }
}
