package com.example.inga.mcash;

/**
 * Created by Inga on 11.03.2015.
 */
public class Commodity {

    private String name, image;
    private int id;
    private int price;
    private int amount;

    public Commodity() {
        amount=1;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getImage(){
        return image;
    }

    public int getPrice(){
        return price;
    }

    public int getAmount(){
        return amount;
    }
}
