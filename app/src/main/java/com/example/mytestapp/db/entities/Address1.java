package com.example.mytestapp.db.entities;

//This class represents the Address object

import androidx.annotation.NonNull;

public class Address1 {

    public String address;
    public String city;

    public Address1(){

    }

    public Address1(String address, String city) {
        this.address = address;
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
