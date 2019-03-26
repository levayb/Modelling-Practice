package com.codecool.wardrobe.api;

public class Cloth {
    private static int id;
    private String brand;
    private String color;
    private ClothType type;

    public Cloth(String brand, String color, ClothType type) {
        this.brand = brand;
        this.color = color;
        this.type = type;
    }

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        Cloth.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public ClothType getType() {
        return type;
    }

    public void setType(ClothType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return  "Cloth{" + id +
                "brand='" + brand + '\'' +
                "color='" + color + '\'' +
                "type=" + type +
                '}';
    }
}
