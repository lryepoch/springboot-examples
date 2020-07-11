package com.demo.pojo;

/**
 * @author lryepoch
 * @date 2020/7/8 11:30
 * @description TODO
 */
public class Product {

    private int id;
    private String name;
    private int price;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Product(int id, String name, int price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Product() {
    }
}
