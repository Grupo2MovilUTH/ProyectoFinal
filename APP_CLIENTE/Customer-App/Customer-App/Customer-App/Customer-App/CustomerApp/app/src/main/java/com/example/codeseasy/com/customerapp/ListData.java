package com.example.codeseasy.com.customerapp;

public class ListData {
    String id, name, des,img, price;

    public ListData(String id, String name, String des, String img, String price) {
        this.id = id;
        this.name = name;
        this.des = des;
        this.img = img;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImg() {
        return img;
    }

    public String getDes() {
        return des;
    }

    public String getPrice() {
        return price;
    }
}