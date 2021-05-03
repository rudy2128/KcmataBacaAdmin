package com.kacamata.kacamataplusminus.entity;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Test {
    private String testId;
    private String imageUrl;
    private String description;

    public Test(String testId, String imageUrl, String description) {
        this.testId = testId;
        this.imageUrl = imageUrl;
        this.description = description;
    }

    public Test() {
    }

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
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
}
