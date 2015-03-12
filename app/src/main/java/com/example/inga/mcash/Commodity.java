package com.example.inga.mcash;

/**
 * Created by Inga on 11.03.2015.
 */
public class Commodity {

    private String name, image;
    private long id;
    private float price;

    public Commodity() {

    }

    public void setId(long id) {

        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public long getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getImage(){
        return image;
    }

    public Float getPrice(){
        return price;
    }
}
