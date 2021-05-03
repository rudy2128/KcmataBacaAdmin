package com.kacamata.kacamataplusminus.entity;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

@IgnoreExtraProperties
public class Order implements Serializable {
    private String date;
    private String proId;
    private String title;
    private String note;
    private String quantity;
    private String price;
    private String subtotal;

    public Order(String date, String proId, String title, String note, String quantity, String price, String subtotal) {
        this.date = date;
        this.proId = proId;
        this.title = title;
        this.note = note;
        this.quantity = quantity;
        this.price = price;
        this.subtotal = subtotal;
    }

    public Order() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getProId() {
        return proId;
    }

    public void setProId(String proId) {
        this.proId = proId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }
}
