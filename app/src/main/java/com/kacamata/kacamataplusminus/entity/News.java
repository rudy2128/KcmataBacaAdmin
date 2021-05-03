package com.kacamata.kacamataplusminus.entity;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class News {
    private String proId;
    private String title;
    private String description;
    private String imageUrl;

    public News(String proId, String title, String description, String imageUrl) {
        this.proId = proId;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public News() {
    }

    public News(String proId, String description) {

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
