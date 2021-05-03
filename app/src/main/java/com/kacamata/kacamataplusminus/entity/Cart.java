package com.kacamata.kacamataplusminus.entity;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Cart {
    private String phone;
    private String name;
    private String address;
    private String process;

    public Cart(String phone, String name, String address, String process) {
        this.phone = phone;
        this.name = name;
        this.address = address;
        this.process = process;
    }

    public Cart() {
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }
}
