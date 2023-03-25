package com.example.yiyoua13.variousclass;

public class Place {
    private String name;
    private String address;
    private String phone;
    private String type;
    private String price;
    private String time;
    private int imageId;

    public Place(String name, int imageId) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.type = type;
        this.price = price;
        this.time = time;
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getType() {
        return type;
    }

    public String getPrice() {
        return price;
    }

    public String getTime() {
        return time;
    }

    public int getImageId() {
        return imageId;
    }
}
