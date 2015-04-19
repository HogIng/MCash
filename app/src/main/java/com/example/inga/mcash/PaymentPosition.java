package com.example.inga.mcash;

/**
 * Created by Inga on 21.03.2015.
 */
public class PaymentPosition {

    private int paymentId, commodityId, number, price, percentage ;


    public void setPaymentId(int paymentId){
        this.paymentId=paymentId;
    }

    public void setNumber(int number){
        this.number=number;
    }

    public void setCommodityId(int commodityId){
        this.commodityId=commodityId;
    }

    public void setPrice(int price){
        this.price = price;
    }

    public void setPercentage(int percentage){
        this.percentage = percentage;
    }

    public int getPaymentId(){
        return paymentId;
    }

    public int getCommodityId(){
        return commodityId;
    }

    public int getNumber(){
        return number;
    }

    public int getPrice(){
        return price;
    }

    public int getPercentage(){
        return  percentage;
    }
}
