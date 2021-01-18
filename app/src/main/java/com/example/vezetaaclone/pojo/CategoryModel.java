package com.example.vezetaaclone.pojo;

public class CategoryModel {
     public String catImg;
    public String catName;

    public CategoryModel() {
    }

    public CategoryModel(String catImg, String catName) {
        this.catImg = catImg;
        this.catName = catName;
    }

    public void setCatImg(String catImg) {
        this.catImg = catImg;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getCatImg() {
        return catImg;
    }

    public String getCatName() {
        return catName;
    }
}
