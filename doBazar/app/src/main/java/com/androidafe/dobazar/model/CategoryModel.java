package com.androidafe.dobazar.model;

public class CategoryModel {

    private int id;
    private String catName;
    private String catDesc;
    private String catImg;
    private boolean catStatus;

    public CategoryModel(int id, String catName, String catDesc, String catImg, boolean catStatus) {
        this.id = id;
        this.catName = catName;
        this.catDesc = catDesc;
        this.catImg = catImg;
        this.catStatus = catStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getCatDesc() {
        return catDesc;
    }

    public void setCatDesc(String catDesc) {
        this.catDesc = catDesc;
    }

    public String getCatImg() {
        return "https://devtutor.me/admin/panel/category/uploads/"+catImg;
    }

    public void setCatImg(String catImg) {
        this.catImg = catImg;
    }

    public boolean isCatStatus() {
        return catStatus;
    }

    public void setCatStatus(boolean catStatus) {
        this.catStatus = catStatus;
    }
}
