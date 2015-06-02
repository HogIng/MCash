package com.example.inga.mcash;

/**
 * Created by Inga on 11.03.2015.
 */
public class Commodity {

    public static final int IS_GROUP = 1;
    public static final int NOT_GROUP = 0;

    private String name, ean, image;
    private int id, price, amount, groupId, isGroup;

    public Commodity() {

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

    public void setGroupId(int groupId){
        this.groupId=groupId;
    }

    public void setIsGroup(int isGroup){
        this.isGroup= isGroup;
    }

    public void setEan(String ean){
        this.ean=ean;
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

    public int getGroupId() {
        return groupId;
    }

    public int getIsGroup() {
        return isGroup;
    }

    public String getEan(){
        return ean;
    }
}
