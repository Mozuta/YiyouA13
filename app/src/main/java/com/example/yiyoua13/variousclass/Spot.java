package com.example.yiyoua13.variousclass;

public class Spot {
    private String name;
    private String address;
    private String description;
    private String image;
    private String stars;
    private String price;
    private String distance;
    private String id;
    private String kind;
    public Spot(String name, String address, String description, String image, String stars, String price, String distance, String id, String kind) {
        this.name = name;
        this.address = address;
        this.description = description;
        this.image = image;
        this.stars = stars;
        this.price = price;
        this.distance = distance;
        this.id = id;
        this.kind = kind;
    }
    public Spot() {
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
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public String getStars() {
        return stars;
    }
    public void setStars(String stars) {
        this.stars = stars;
    }
    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {
        this.price = price;
    }
    public String getDistance() {
        return distance;
    }
    public void setDistance(String distance) {
        this.distance = distance;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getKind() {
        return kind;
    }
    public void setKind(String kind) {
        this.kind = kind;
    }

}
