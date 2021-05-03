package com.kacamata.kacamataplusminus.entity;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Product {
    private String proId;
    private String imageUrl;
    private String title;
    private String price;
    private String description;

    public Product(String proId, String imageUrl, String title, String price, String description) {
        this.proId = proId;
        this.imageUrl = imageUrl;
        this.title = title;
        this.price = price;
        this.description = description;
    }

    public Product() {
    }


    public String getProId() {
        return proId;
    }

    public void setProId(String proId) {
        this.proId = proId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
